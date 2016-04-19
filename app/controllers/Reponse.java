package controllers;

import java.time.Instant;
import java.util.Date;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.FormulaireDAO;
import hibernate.dao.NoteDAO;
import hibernate.model.Note;
import hibernate.model.Question;
import hibernate.model.Utilisateur;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Reponse extends Controller{

	
	public static Promise<Result> insertReponses() {
		Promise<Result> promiseOfResult = Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Utilisateur u = new Utilisateur();
				Note n = new Note();
				Question q = new Question();
			
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
						/*Il aurait été plus rapide de mapper le json en objet mais j'y suis pas parvenus
						 * du coup : TO DO
						 * 
						 * */		
						
						if(jsonN.get("questionFull").get("notes").get("valeur") != null) {
							n.setValeur(jsonN.get("questionFull").get("notes").get("valeur").asInt());
						}
						if(jsonN.get("questionFull").get("notes").get("remarque") != null) {
							n.setRemarque(jsonN.get("questionFull").get("notes").get("remarque").toString());
						}
						if(jsonN.get("Utilisateur").get("id") != null) {
							u.setId(jsonN.get("Utilisateur").get("id").asLong());
							n.setUtilisateur(u);
						}
						if(jsonN.get("questionFull").get("id") != null) {
							q.setId(jsonN.get("questionFull").get("id").asLong());
							n.setQuestion(q);
						}
						
						if(n.getUtilisateur() != null && n.getQuestion() != null){
							n.setDateSaisie(Instant.ofEpochMilli(new Date().getTime()));
							NoteDAO.insert(n);
							BDDUtils.commit(isActive, tx);
							return ok();
						}
						
					} catch(HibernateException ex) {
						Logger.error("Hibernate failure : "+ ex.getMessage());
						BDDUtils.rollback(isActive, tx);
						return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
					}
				
				}
			return notFound();
		});
		return promiseOfResult;
	}
	
	public static Promise<Result> getReponsesAlreadyCommited(Long idUser){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListFormulaires(FormulaireDAO.getAll());
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			System.out.println(idUser);
			
			return ok();
			
		});
		return promiseOfResult;
	}
	
}
