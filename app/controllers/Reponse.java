package controllers;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.FormulaireDAO;
import hibernate.dao.NoteDAO;
import hibernate.dao.QuestionDAO;
import hibernate.dao.ServiceDAO;
import hibernate.dao.UtilisateurDAO;
import hibernate.model.Formulaire;
import hibernate.model.Note;
import hibernate.model.Question;
import hibernate.model.Service;
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
								
					Note n = new Note();
					
					Utilisateur u = null;
					Question q = null;
					
					
					
					if(jsonN.has("utilisateur") && jsonN.get("utilisateur").has("id")) {
						u = UtilisateurDAO.findById(jsonN.get("utilisateur").get("id").asLong());
					}
				
					if(jsonN.has("reponseFull") && jsonN.get("reponseFull").has("idQuestion")) {
						q = QuestionDAO.findById(jsonN.get("reponseFull").get("idQuestion").asLong());
					}
					//Tester s'il y a déjà une note pour cette question par cet utilisateur -> nope, voir sms sur l'historisation de réponses

					if(jsonN.get("reponseFull").has("id")) {
						n.setId(jsonN.get("reponseFull").get("id").asLong());
					}
					
					if(jsonN.get("reponseFull").has("valeur")) {
						n.setValeur(jsonN.get("reponseFull").get("valeur").asInt());
					}

					if(jsonN.get("reponseFull").has("remarque")) {
						n.setRemarque(jsonN.get("reponseFull").get("remarque").toString());
					}

					if(jsonN.get("reponseFull").has("priorisation")) {
						n.setPriorisation(jsonN.get("reponseFull").get("priorisation").asInt());
					}

					if(jsonN.get("reponseFull").has("axeAmelioration1")) {
						n.setAxeAmelioration1(jsonN.get("reponseFull").get("axeAmelioration1").toString());
					}

					if(jsonN.get("reponseFull").has("axeAmelioration2")) {
						n.setAxeAmelioration2(jsonN.get("reponseFull").get("axeAmelioration2").toString());
					}

					n.setUtilisateur(u);
					n.setQuestion(q);
					
					if(n.getUtilisateur() != null && n.getQuestion() != null){
						n.setDateSaisie(Instant.now());
						NoteDAO.insertOrUpdate(n);
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

			
			JSONObject ja = new JSONObject();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				List<Long> le = new ArrayList<>();
				le.add(idEtablissement);
				
				List<Long> ls = new ArrayList<>();
				
				List<Service> listService = ServiceDAO.getAllServiceByEtablissement(idEtablissement);
				listService.stream().mapToLong((e) -> e.getId()).forEach(ls::add);
				
				ja.put("listService", ConstructJSONObjects.getJSONArrayforListServices(listService));
				JSONObject jsonFormulaire = new JSONObject();
				JSONObject jsonQuestion = new JSONObject();
				for (Service service : listService) {
					List<Long> listServiceId = new ArrayList<>();
					listServiceId.add(service.getId());
					
					List<Formulaire> listFormulaire = FormulaireDAO.getAllServiceByEtablissement(le, listServiceId);
					jsonFormulaire.put(Long.toString(service.getId()), ConstructJSONObjects.getJSONArrayforListFormulairesFull(listFormulaire));
				
					for (Formulaire formulaire : listFormulaire) {
						jsonQuestion.put(Long.toString(formulaire.getId()), ConstructJSONObjects.getJSONArrayforListQuestions(QuestionDAO.getListQuestionByFormulaireId(formulaire.getId())));
					}
				}
				
				ja.put("listFormulaire", jsonFormulaire);
				ja.put("listQuestion", jsonQuestion);
				if(listService != null && !listService.isEmpty()) {
					ja.put("listNote", ConstructJSONObjects.getJSONArrayforNoteWithQuestion(NoteDAO.getListNoteByEtablissementsAndServices(le, ls)));
				}
				
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

	public static Promise<Result> getReponsesByUserId(Long idUser){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONObject ja = new JSONObject();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja.put("ListNote",ConstructJSONObjects.getJSONArrayforNote(NoteDAO.getListNoteByUserId(idUser)));
				
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

	public static Promise<Result> getReponsesPerPriorisationForUserId(Long idUser){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				List<Note> ln = NoteDAO.getListNoteByUserId(idUser);
				List<Note> finalLn = new ArrayList<Note>();
				 for(int i=0;i<ln.size();i++){
					 if(ln.get(i).getPriorisation() != null)finalLn.add(ln.get(i));
				 }
				
				 Comparator<Note> c = new Comparator<Note>() {
				        @Override
				        public int compare(Note n1, Note n2)
				        {

				            return  n1.getPriorisation().compareTo(n2.getPriorisation());
				        }
				    };
				 
				finalLn.sort(c);
				
				ja = ConstructJSONObjects.getJSONArrayforNotePriorise(finalLn);
				
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
