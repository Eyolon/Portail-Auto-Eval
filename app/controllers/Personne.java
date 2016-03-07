package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.BDDUtils;
import hibernate.dao.ConnexionDAO;
import hibernate.dao.TypeUtilisateurDAO;
import hibernate.dao.UtilisateurDAO;
import hibernate.model.Connexion;
import hibernate.model.Utilisateur;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Personne extends Controller {
	public static DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE);
	
	public static Promise<Result> checkUser(String email) {
		Promise<Result> promiseOfResult = Promise.promise(() -> 
		{
			String newToken = null;
			String pwd = null;
			String newMail = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.get("password") != null && jsonN.get("password").asText() != null) {
				pwd = jsonN.get("password").asText();
			}
			if(jsonN != null && jsonN.get("adresseMail") != null && jsonN.get("adresseMail").asText() != null) {
				newMail = jsonN.get("adresseMail").asText();
			}
			Utilisateur u = null;
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				u = UtilisateurDAO.getUtilisateurByEmail(newMail);
				if(u == null) {
					u = UtilisateurDAO.getUtilisateurByEmail(email);
					if(u == null || !BCrypt.checkpw(pwd, u.getConnexion().getPassword())) {
						u = null;
					} else {
						newToken = generateToken(u);
					}
				} else if(BCrypt.checkpw(pwd, u.getConnexion().getPassword())) {
					newToken = generateToken(u);
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
		return promiseOfResult;
	}
	
	public static Promise<Result> insertUser() {
		Promise<Result> promiseOfResult = Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			Utilisateur u = null;
			if(jsonN != null && jsonN.get("id") == null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					u = UtilisateurDAO.getUtilisateurByEmail(jsonN.get("adresseMail").asText());
					if(u == null) {
						u = new Utilisateur();
						Connexion connexion = new Connexion(BCrypt.hashpw(jsonN.get("pwd").asText(), BCrypt.gensalt()));
						ConnexionDAO.insert(connexion);
						u.setConnexion(connexion);
						
						u.setEmail(jsonN.get("adresseMail").asText());
						if(jsonN.get("login") != null && !jsonN.get("login").asText().isEmpty()) {
							u.setLogin(jsonN.get("login").asText());
						}
						u.setTypeUtilisateur(TypeUtilisateurDAO.findById(2l));
						UtilisateurDAO.insert(u);
					} else {
						throw new Exception("Adresse mail déjà utilisée.");
					}
					
					BDDUtils.commit(isActive, tx);
				} catch(HibernateException ex) {
					Logger.error("Hibernate failure : "+ ex.getMessage());
					BDDUtils.rollback(isActive, tx);
					return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
				} catch(Exception ex) {
					BDDUtils.rollback(isActive, tx);
					if(ex.getMessage().equalsIgnoreCase("Adresse mail déjà utilisée.")) {
						Logger.info("Adresse mail \"" + jsonN.get("adresseMail").asText() + " déjà utilisée.");
						return forbidden("Adresse mail \"" + jsonN.get("adresseMail").asText() + " déjà utilisée.");
					} else {
						Logger.error("Error : "+ ex.getMessage());
						return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
					}
				}
				return ok();
			}
			return notFound();
		});
		return promiseOfResult;
	}
	
	public static Promise<Result> updateUser() {
		Promise<Result> promiseOfResult = Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			JSONObject js = null;
			if(jsonN != null && jsonN.get("id") != null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					Utilisateur u = UtilisateurDAO.findById(Utilisateur.class, jsonN.get("id").asLong());
					u.getConnexion().setPassword(BCrypt.hashpw(jsonN.get("pwd").asText(), BCrypt.gensalt()));
					u.setEmail(jsonN.get("adresseMail").asText());
					u.setLogin(jsonN.get("login").asText());
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
		return promiseOfResult;
	}
	
	public static Promise<Result> getUserByIdFull(Long idUser) {
		Promise<Result> promiseOfResult = Promise.promise(() -> 
		{
			String token = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.get("token") != null && jsonN.get("token").asText() != null) {
				token = jsonN.get("token").asText();
			}
			JSONObject js = null;
			Utilisateur u = null;
			
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				u = UtilisateurDAO.findById(Utilisateur.class, idUser);

				if(u != null && checkToken(token, u)) {
					js = ConstructJSONObjects.getJSONforUserFull(u);
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
		return promiseOfResult;
	}
	
	public static Promise<Result> seLogger(String email) {
		Promise<Result> promiseOfResult = Promise.promise(() -> 
		{
			String newToken = null;
			String pwd = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.get("password") != null && jsonN.get("password").asText() != null) {
				pwd = jsonN.get("password").asText();
			}
			JSONObject js = null;
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				Utilisateur u = UtilisateurDAO.getUtilisateurByEmail(email);
				if(u != null) {
					if(BCrypt.checkpw(pwd, u.getConnexion().getPassword())) {
						newToken = generateToken(u);
					} else {
						u = null;
					}
					
					js = new JSONObject()
							.put("utilisateur", ConstructJSONObjects.getJSONforUser(u))
							.put("token", newToken);
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
		return promiseOfResult;
	}
	
	private static String generateToken(Utilisateur user) {
		String keySource = user.getId() + "/" + user.getLogin() + user.getEmail() + user.getConnexion().getPassword() +"psj@1802";
		byte [] tokenByte = Base64.encodeBase64(keySource.getBytes());
		String token = new String(tokenByte);
		return token;
	}
	
	public static boolean checkToken(String token, Utilisateur user){
		String utilisateurToken;
		utilisateurToken = generateToken(user);
		if (utilisateurToken.equals(token)){
			return true;
		} else {
			return false;
		}
	}
	
	public static boolean checkToken(String token){
		Utilisateur user = getUtilisateurFromToken(token);
		String utilisateurToken;
		utilisateurToken = generateToken(user);
		if (utilisateurToken.equals(token)){
			return true;
		} else {
			return false;
		}
	}
	
	public static Utilisateur getUtilisateurFromToken(String token){
		byte[] tokenBytes = Base64.decodeBase64(token);
	
		String userToken = new String(tokenBytes);
		String[] tokenSplited = userToken.split("/");
		
		Long userId = null;
		if (tokenSplited.length > 0){
			try {
				userId = Long.valueOf(tokenSplited[0]);
			} catch(NumberFormatException e) {
				Logger.error("Token invalide: ", e);
			}
			
			return UtilisateurDAO.findById(Utilisateur.class, userId);
		} else {
			return null;
		}
	}
}