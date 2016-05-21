package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.CritereDAO;
import hibernate.dao.FormulaireDAO;
import hibernate.dao.QuestionDAO;
import hibernate.model.Critere;
import hibernate.model.Formulaire;
import hibernate.model.Question;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

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
	
	public static Promise<Result> getListCritere(){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListCritere(CritereDAO.getAll());
				
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

	public static Promise<Result> saveQuestion(){
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					//Il s'avere qu'il se réfère a la primary key, qu'importe si l'objet est initialisé avec un new ou pas
					//Ici , la PK est l'id de la question donc si y a pas d'id on est tranquil, il créer une nouvelle clef et question
					if(jsonN.has("question") && jsonN.get("question").has("id")){
						Question q = new Question();
						q.setId(jsonN.get("question").get("id").asLong());
						
						if(jsonN.get("question").get("valeur").asText() != ""){
							q.setValeur(jsonN.get("question").get("valeur").asText());
						}
						
						Formulaire f = FormulaireDAO.findById(jsonN.get("question").get("formulaire").get("id").asLong());
						Critere c = CritereDAO.findById(jsonN.get("question").get("critere").get("id").asLong());
						q.setCritere(c);
						q.setFormulaire(f);
						QuestionDAO.save(q);
					}
					
					else if(jsonN.has("question")&& jsonN.get("question").has("valeur")){
						Question q = new Question();
						q.setValeur(jsonN.get("question").get("valeur").asText());
						Formulaire f = FormulaireDAO.findById(jsonN.get("question").get("formulaire").get("id").asLong());
						Critere c = CritereDAO.findById(jsonN.get("question").get("critere").get("id").asLong());
						q.setCritere(c);
						q.setFormulaire(f);
						QuestionDAO.save(q);
					}

					else{
						return notFound();
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
	
	public static Promise<Result> updateQuestion() {
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			JSONObject js = null;
			if(jsonN != null && jsonN.get("question") != null) {
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
					
					Question q = QuestionDAO.findById(jsonN.get("question").get("id").asLong());
					
					q.setValeur(jsonN.get("question").get("valeur").asText());
					
					QuestionDAO.update(q);
					
					js = new JSONObject();
					js.put("question", ConstructJSONObjects.getJSONforQuestion(q));
					
					BDDUtils.commit(isActive, tx);
				}
				catch(Exception ex) {
					Logger.error("Hibernate failure : "+ ex.getMessage());
					BDDUtils.rollback(isActive, tx);
					return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
				}
			}
			if(js == null) {
				return notFound("Question introuvable.");
			} else {
				return ok(js.toString());
			}
		});
	}

}