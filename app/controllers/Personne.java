package controllers;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.GregorianCalendar;
import java.util.Locale;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.Transaction;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import Tools.Tools;
import hibernate.dao.BDDUtils;
import hibernate.dao.GenreDAO;
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
		Promise<String> promise = Promise.promise(() -> 
		{
			String newToken = null;
			String pwd = null;
			String newMail = null;
			JsonNode jsonN = request().body().asJson();
			if(jsonN != null && jsonN.get("password") != null && jsonN.get("password").asText() != null) {
				pwd = jsonN.get("password").asText();
			}
			if(jsonN != null && jsonN.get("email") != null && jsonN.get("email").asText() != null) {
				newMail = jsonN.get("email").asText();
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
				} else if(u.getConnexion().getPassword().equals(pwd)) {
					newToken = generateToken(u);
				} else {
					u = null;
				}
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				System.out.println("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
			}
			if(u == null){
				return null;
			} else {
				return new JSONObject()
					.put("utilisateur", ConstructJSONObjects.getJSONforUser(u))
					.put("token", newToken).toString();
			}
		});
		Promise<Result> promiseOfResult = promise.map(result -> {
			if(result != null) {
				return ok(result);
			} else {
				return notFound();
			}
		});
		return promiseOfResult;
	}
	
	public static Promise<Result> insertUser() {
		Promise<Boolean> promise = Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			boolean isInserted = false;
			if(jsonN != null && jsonN.get("id") == null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					Utilisateur u = UtilisateurDAO.getUtilisateurByEmail(jsonN.get("adresseMail").asText());
					if(u == null) {
						u = new Utilisateur();
						u.setConnexion(new Connexion(BCrypt.hashpw(jsonN.get("pwd").asText(), BCrypt.gensalt())));
						u.setAdresse(jsonN.get("adresseRue").asText());
						u.setAdresseCodePostal(jsonN.get("adresseCodePostal").asText());
						u.setAdresseVille(jsonN.get("adresseVille").asText());
						u.setAdresseMail(jsonN.get("adresseMail").asText());
						u.setTelephone(jsonN.get("telephone").asText());
						u.setGenre(GenreDAO.getGenreByLibelle(jsonN.get("genre").asText()));
						u.setNom(jsonN.get("lastname").asText());
						u.setPrenom(jsonN.get("firstname").asText());
						u.setDateNaissance(GregorianCalendar.from(Tools.parseDateToZonedDateTime(df.parse(jsonN.get("birthday").asText()))));
						u.setTypeUtilisateur(TypeUtilisateurDAO.findById(2l));
						BDDUtils.getCurrentSession().save(u);
						isInserted = true;
					}
					
					BDDUtils.commit(isActive, tx);
				}
				catch(Exception ex) {
					System.out.println("Hibernate failure : "+ ex.getMessage());
					BDDUtils.rollback(isActive, tx);
					isInserted = false;
				}
			}
			return isInserted;
		});
		Promise<Result> promiseOfResult = promise.map(result -> {
			if(result) {
				return ok();
			} else {
				return notFound();
			}
		});
		return promiseOfResult;
	}
	
	public static Promise<Result> updateUser() {
		Promise<String> promise = Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			JSONObject js = null;
			if(jsonN != null && jsonN.get("id") != null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					Utilisateur u = UtilisateurDAO.findByIdWithEager(jsonN.get("id").asLong());
					u.getConnexion().setPassword(BCrypt.hashpw(jsonN.get("pwd").asText(), BCrypt.gensalt()));
					u.setAdresse(jsonN.get("adresseRue").asText());
					u.setAdresseCodePostal(jsonN.get("adresseCodePostal").asText());
					u.setAdresseVille(jsonN.get("adresseVille").asText());
					u.setAdresseMail(jsonN.get("adresseMail").asText());
					u.setTelephone(jsonN.get("telephone").asText());
					u.setGenre(GenreDAO.getGenreByLibelle(jsonN.get("genre").asText()));
					u.setNom(jsonN.get("lastname").asText());
					u.setPrenom(jsonN.get("firstname").asText());
					u.setDateNaissance(GregorianCalendar.from(Tools.parseDateToZonedDateTime(df.parse(jsonN.get("birthday").asText()))));
					BDDUtils.getCurrentSession().update(u);
					
					js = new JSONObject();
					js.put("user", ConstructJSONObjects.getJSONforUser(u))
						.put("token", generateToken(u));
					
					BDDUtils.commit(isActive, tx);
				}
				catch(Exception ex) {
					System.out.println("Hibernate failure : "+ ex.getMessage());
					BDDUtils.rollback(isActive, tx);
				}
			}
			if(js == null) {
				return null;
			} else {
				return js.toString();
			}
		});
		Promise<Result> promiseOfResult = promise.map(result -> {
			if(result != null) {
				return ok(result);
			} else {
				return notFound();
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
				
				u = UtilisateurDAO.findByIdWithEager(idUser);

				if(u != null && checkToken(token, u)) {
					js = ConstructJSONObjects.getJSONforUserFull(u);
				}
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				System.out.println("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError();
			}
			
			if(js == null) {
				return notFound();
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
				System.out.println("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError();
			}
			if(js == null) {
				return notFound();
			} else {
				return ok(js.toString());
			}
			
		});
		return promiseOfResult;
	}
	
	private static String generateToken(Utilisateur user) {
		String keySource = user.getId() + "/" + user.getNom() + user.getPrenom() + user.getAdresseMail() + user.getConnexion().getPassword() +"pmce&1802";
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
			
			return UtilisateurDAO.findById(userId);
		} else {
			return null;
		}
	}
}