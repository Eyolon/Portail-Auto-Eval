package controllers;

import java.time.Instant;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.NoteDAO;
import hibernate.dao.QuestionDAO;
import hibernate.dao.UtilisateurDAO;
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
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
						/*Il aurait été plus rapide de mapper le json en objet mais j'y suis pas parvenus
						 * du coup : TO DO
						 * 
						 * */		
					//TO DO retirer le new Note() et voir s'il existe dans la BDD avant -> nope, voir sms sur l'historisation de réponses
					Note n = new Note();
					
					Utilisateur u = null;
					Question q = null;
					//RECUPERE JUSTE l'ID de LA QUESTION ET DE l'UTILISATEUR = plus léger et ça utilise LES OBJETS !!!
					//Y'a vraiment une majuscule en DEBUT d'attribut ? A refactorer en "utilisateur" à cours terme -> ok
					//Utiliser le token pour récupérer l'utilisateur ...
					if(jsonN.has("utilisateur") && jsonN.get("utilisateur").has("id")) {
						u = UtilisateurDAO.findById(jsonN.get("utilisateur").get("id").asLong());
					}
					if(jsonN.has("questionFull") && jsonN.get("questionFull").has("id")) {
						q = QuestionDAO.findById(jsonN.get("questionFull").get("id").asLong());
					}
					//Tester s'il y a déjà une note pour cette question par cet utilisateur -> nope, voir sms sur l'historisation de réponses
					
					if(jsonN.get("questionFull").get("notes").get(0).get("valeur") != null) {
						n.setValeur(jsonN.get("questionFull").get("notes").get(0).get("valeur").asInt());
					}
					if(jsonN.get("questionFull").get("notes").get(0).get("remarque") != null) {
						n.setRemarque(jsonN.get("questionFull").get("notes").get(0).get("remarque").toString());
					}
					n.setUtilisateur(u);
					n.setQuestion(q);
					
					if(n.getUtilisateur() != null && n.getQuestion() != null){
						n.setDateSaisie(Instant.now());
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
	}
	
	public static Promise<Result> getReponsesPerEtablissement(Long idEtablissement){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			
			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforNote(NoteDAO.getListNoteAndDetailByEtablissementId(idEtablissement));
				
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
