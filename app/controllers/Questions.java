package controllers;

import Tools.ConstructJSONObjects;
import hibernate.dao.QuestionDAO;

import org.apache.commons.codec.binary.Base64;
import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;
import org.mindrot.jbcrypt.BCrypt;

import com.fasterxml.jackson.databind.JsonNode;

import hibernate.dao.UtilisateurDAO;
import hibernate.model.Connexion;
import hibernate.model.Utilisateur;
import hibernate.model.Question;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

import play.mvc.Controller;

public class Questions extends Controller {
	
	public static Promise<Result> getListQuestion(){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListQuestions(QuestionDAO.getAll());
				
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

	public static Promise<Result> insertQuestion(Long idQuestion){
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					Question q = new Question();
					
					if(jsonN.has("question") && jsonN.get("question").get("id").asLong()!= 0) {
						q.setId(jsonN.get("question").get("id").asLong());
						q.setValeur(jsonN.get("question").get("valeur").asText());
					}
						
					} catch(HibernateException ex) {
						Logger.error("Hibernate failure : "+ ex.getMessage());
						BDDUtils.rollback(isActive, tx);
						return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
					}
				}
			return notFound();
		});
	}

}