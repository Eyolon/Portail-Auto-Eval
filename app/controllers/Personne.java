package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.ConnexionDAO;
import hibernate.dao.EtablissementDAO;
import hibernate.dao.ServiceDAO;
import hibernate.dao.TypeUtilisateurDAO;
import hibernate.dao.EtablissementDAO;
import hibernate.dao.UtilisateurDAO;
import hibernate.model.Connexion;
import hibernate.model.Utilisateur;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Personne extends Controller {
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	
	public static Promise<Result> checkUser(String login) {
		return Promise.promise(() -> 
		{
			String newToken = null;
			String pwd = null;
			String newLogin = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.get("password") != null && jsonN.get("password").asText() != null) {
				pwd = jsonN.get("password").asText();
				
			}
			if(jsonN != null && jsonN.get("login") != null && jsonN.get("login").asText() != null) {
				newLogin = jsonN.get("login").asText();
				
			}
			Utilisateur u = null;
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				u = UtilisateurDAO.getUtilisateurByLogin(newLogin);
				
				if(u == null) {
					u = UtilisateurDAO.getUtilisateurByLogin(login);
					if(u == null || !BCrypt.checkpw(pwd, u.getConnexion().getPassword())) {
						u = null;
					} else {
				
						newToken = generateToken(u);
						
						/*quick fix BUG : Il choppe l'id mais pas le libelle ?*/
						u.setService(ServiceDAO.findById(u.getService().getId()));
						u.setTypeUtilisateur(TypeUtilisateurDAO.findById(u.getTypeUtilisateur().getId()));
					
					}
				} else if(BCrypt.checkpw(pwd, u.getConnexion().getPassword())) {
					
					newToken = generateToken(u);
					
					/*quick fix BUG : Il choppe l'id mais pas le libelle ?*/
					u.setService(ServiceDAO.findById(u.getService().getId()));
					u.setTypeUtilisateur(TypeUtilisateurDAO.findById(u.getTypeUtilisateur().getId()));
					
				} else {
					u = null;
				}
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			if(u == null){
				return notFound("Utilisateur introuvable.");
			} else {
				return ok(new JSONObject()
					.put("utilisateur", ConstructJSONObjects.getJSONforUser(u))
					.put("token", newToken).toString());
			}
		});
	}
	
	public static Promise<Result> insertUser() {
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			Utilisateur u = null;
			
			if(jsonN != null && jsonN.get("login") != null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					u = UtilisateurDAO.getUtilisateurByLogin(jsonN.get("login").asText());
					if(u == null) {
						u = new Utilisateur();
						Connexion connexion = new Connexion(BCrypt.hashpw(jsonN.get("pwd").asText(), BCrypt.gensalt()));
						ConnexionDAO.insert(connexion);
						u.setConnexion(connexion);
						
						if(jsonN.has("login") && !jsonN.get("login").asText().isEmpty()) {
							u.setLogin(jsonN.get("login").asText());
						}
						
						if(jsonN.has("service") && !jsonN.get("service").asText().isEmpty()) {
							u.setService(ServiceDAO.findById(jsonN.get("service").asLong()));
						}
						
						if(jsonN.has("etablissement") && !jsonN.get("etablissement").asText().isEmpty()) {
							u.setEtablissement(EtablissementDAO.findById(jsonN.get("etablissement").asLong()));
						}
						
						if(jsonN.has("typeUser") && !jsonN.get("typeUser").asText().isEmpty()) {
							u.setTypeUtilisateur(TypeUtilisateurDAO.findById(jsonN.get("typeUser").asLong()));
						} else {
							u.setTypeUtilisateur(TypeUtilisateurDAO.findById(2l));
						}
						
						UtilisateurDAO.insert(u);
					} else {
						throw new Exception("Login déjà utilisée.");
					}
					
					BDDUtils.commit(isActive, tx);
				} catch(HibernateException ex) {
					Logger.error("Hibernate failure : "+ ex.getMessage());
					BDDUtils.rollback(isActive, tx);
					return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
				} catch(Exception ex) {
					BDDUtils.rollback(isActive, tx);
					if(ex.getMessage().equalsIgnoreCase("Login déjà utilisée.")) {
						Logger.info("Login \"" + jsonN.get("adresseMail").asText() + " déjà utilisée.");
						return forbidden("Login \"" + jsonN.get("adresseMail").asText() + " déjà utilisée.");
					} else {
						Logger.error("Error : "+ ex.getMessage());
						return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
					}
				}
				return ok();
			}
			return notFound();
		});
	}
	
	public static Promise<Result> updateUser() {
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			JSONObject js = null;
			if(jsonN != null && jsonN.get("id") != null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					Utilisateur u = UtilisateurDAO.findById(jsonN.get("id").asLong());
					u.getConnexion().setPassword(BCrypt.hashpw(jsonN.get("pwd").asText(), BCrypt.gensalt()));
					u.setLogin(jsonN.get("login").asText());
					u.setEtablissement(EtablissementDAO.findById(jsonN.get("etablissement").asLong()));
					UtilisateurDAO.update(u);
					
					js = new JSONObject();
					js.put("user", ConstructJSONObjects.getJSONforUser(u))
						.put("token", generateToken(u));
					
					BDDUtils.commit(isActive, tx);
				}
				catch(Exception ex) {
					Logger.error("Hibernate failure : "+ ex.getMessage());
					BDDUtils.rollback(isActive, tx);
					return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
				}
			}
			if(js == null) {
				return notFound("Utilisateur introuvable.");
			} else {
				return ok(js.toString());
			}
		});
	}
	
	public static Promise<Result> getUserByIdFull(Long idUser) {
		return Promise.promise(() -> 
		{
			String token = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.get("token") != null && jsonN.get("token").asText() != null) {
				token = jsonN.get("token").asText();
			}
			JSONObject js = null;
			
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				Utilisateur u = UtilisateurDAO.findById(idUser);
				if(u != null && checkToken(token, u)) {
					js = ConstructJSONObjects.getJSONforUser(u);
				}
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			
			if(js == null) {
				return notFound("Utilisateur introuvable.");
			} else {
				return ok(js.toString());
			}
		});
	}
	
	public static Promise<Result> seLogger(String login) {
		
		return Promise.promise(() -> 
		{
			String pwd = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.has("password") && jsonN.get("password").asText() != null) {
				pwd = jsonN.get("password").asText();
			}
			JSONObject js = null;
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				Utilisateur u = UtilisateurDAO.getUtilisateurByLogin(login);
				if(u != null && BCrypt.checkpw(pwd, u.getConnexion().getPassword())) {
					js = new JSONObject()
							.put("utilisateur", ConstructJSONObjects.getJSONforUser(u))
							.put("token", generateToken(u));
				}
				
				BDDUtils.commit(isActive, tx);
			} catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return js == null ? notFound("Utilisateur introuvable.") : ok(js.toString());
		});
	}
	
	public static Promise<Result> getServices(){
		return Promise.promise(() -> 
		{
			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListServices(ServiceDAO.getAll());
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return ok(ja.toString());
			
		});
	}

	public static Promise<Result> getTypesUser(){
		return Promise.promise(() -> 
		{
			
			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListTypesUtilisateur(TypeUtilisateurDAO.getAll());
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return ok(ja.toString());
			
		});
	}
	
	public static Promise<Result> getEtablissements(){
		return Promise.promise(() -> 
		{
			
			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListEtablissements(EtablissementDAO.getAll());
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return ok(ja.toString());
			
		});
	}
	
	private static String generateToken(Utilisateur user) {
		String keySource = user.getId() + "/" + user.getLogin() + user.getConnexion().getPassword() + "psj@1802";
		byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
		return new String(tokenByte);
	}
	
	public static boolean checkToken(String token, Utilisateur user){
		return generateToken(user).equals(token);
	}
	
	public static boolean checkToken(String token){
		return generateToken(getUtilisateurFromToken(token)).equals(token); 
	}
	
	public static Utilisateur getUtilisateurFromToken(String token){
		byte[] tokenBytes = Base64.decodeBase64(token);
	
		String userToken = new String(tokenBytes);
		String[] tokenSplited = userToken.split("/");
		
		if (tokenSplited.length > 0){
			try {
				Long userId = Long.valueOf(tokenSplited[0]);
				UtilisateurDAO.findById(userId);
			} catch(NumberFormatException e) {
				Logger.error("Token invalide: ", e);
			}
			
		}
		return null;
	}

	public static Promise<Result> getUserFull(){
		return Promise.promise(() -> 
		{
			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListUsers(UtilisateurDAO.getAll());
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return ok(ja.toString());
			
		});
	}
	
	public static Promise<Result> getUserFullByEtablissement(Long idEtablissement){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListUsers(UtilisateurDAO.getAllByEtablissement(idEtablissement));
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			return ok(ja.toString());
			
		});
		return promiseOfResult;
	}
}