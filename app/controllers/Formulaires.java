package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.CritereDAO;
import hibernate.dao.EtablissementDAO;
import hibernate.dao.FormulaireDAO;
import hibernate.dao.FormulaireServiceDAO;
import hibernate.dao.QuestionDAO;
import hibernate.dao.ServiceDAO;
import hibernate.dao.TypeUtilisateurDAO;
import hibernate.model.Etablissement;
import hibernate.model.Formulaire;
import hibernate.model.FormulaireService;
import hibernate.model.FormulaireServiceID;
import hibernate.model.Question;
import hibernate.model.Service;
import hibernate.model.TypeUtilisateur;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Formulaires extends Controller {
	
public static Promise<Result> getListFormulaireFull(){
		
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				ja = ConstructJSONObjects.getJSONArrayforListFormulaireService(FormulaireServiceDAO.getAll());
				
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

	public static Promise<Result> saveFormulaire(){
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			boolean isActive = BDDUtils.getTransactionStatus();
			
			if(jsonN != null){
				Transaction tx = null;
				
				
				try {
					
					tx = BDDUtils.beginTransaction(isActive);
					//Il s'avere qu'il se réfère a la primary key, qu'importe si l'objet est initialisé avec un new ou pas
					//Ici , la PK est l'id de la question donc si y a pas d'id on est tranquil, il créer une nouvelle clef et question
					if(jsonN.has("formulaire") && jsonN.get("formulaire").has("idFormulaire")){
						Formulaire f = FormulaireDAO.findById(jsonN.get("formulaire").get("idFormulaire").asLong());
						FormulaireService fs = null;					
						FormulaireServiceID fsID = null;
					
						if(jsonN.has("idFormulaire") && jsonN.has("idEtablissement") && jsonN.has("idService") && jsonN.has("idTypeUtilisateur")) {
							fs = FormulaireServiceDAO.findById(jsonN.get("idFormulaire").asLong(), jsonN.get("idEtablissement").asLong(), jsonN.get("idService").asLong(), jsonN.get("idTypeUtilisateur").asLong());
							fsID = fs.getFormulaireServiceID();	
						}
					
						Service s = ServiceDAO.findById(jsonN.get("formulaire").get("service").get("id").asLong());
						TypeUtilisateur tu = TypeUtilisateurDAO.findById(jsonN.get("formulaire").get("typeUtilisateur").get("id").asLong());
						Etablissement e = EtablissementDAO.findById(jsonN.get("formulaire").get("etablissement").get("id").asLong());
						
						f.setId(jsonN.get("formulaire").get("idFormulaire").asLong());
						
						if(jsonN.get("formulaire").get("formulaire").get("nom").asText() != ""){
							f.setNom(jsonN.get("formulaire").get("formulaire").get("nom").asText());
						}
						
						for (JsonNode node : jsonN.get("formulaire").get("listQuestion")) {
							Question q = null;
							if(node.has("id")) {
								q = QuestionDAO.findById(node.get("id").asLong());
							} else {
								q = new Question();
							}
							
							q.setFormulaire(f);
							q.setCritere(CritereDAO.findById(node.get("critere").get("id").asLong()));
							q.setValeur(node.get("valeur").asText());
							if(node.has("id")) {
								BDDUtils.update(q);
							} else {
								BDDUtils.insert(q);
							}
						}
						
						if(fs == null && fsID == null) {
							fs = new FormulaireService();
							fsID = new FormulaireServiceID();
							fs.setFormulaireServiceID(fsID);
							fsID.setService(s);
							fsID.setTypeUtilisateur(tu);
							fsID.setEtablissement(e);
							fsID.setFormulaire(f);
						}
						
						FormulaireDAO.insertOrUpdate(f);
						FormulaireServiceDAO.insertOrUpdate(fs);
						
						
					}
					
//					else if(jsonN.has("formulaire")&& jsonN.get("formulaire").has("nom")){
//						Formulaire f = new Formulaire();
//						FormulaireService fs = new FormulaireService();
//						FormulaireServiceID fsID = new FormulaireServiceID();
//						
//						f.setNom(jsonN.get("formulaire").get("nom").asText());
//						FormulaireDAO.save(f);
//						
//						
//						Service s = ServiceDAO.findById(jsonN.get("formulaire").get("service").get("id").asLong());
//						TypeUtilisateur tu = TypeUtilisateurDAO.findById(jsonN.get("formulaire").get("type").get("id").asLong());
//						Etablissement e = EtablissementDAO.findById(jsonN.get("formulaire").get("etablissement").get("id").asLong());
//						fsID.setFormulaire(f);
//						fsID.setService(s);
//						fsID.setTypeUtilisateur(tu);
//						fsID.setEtablissement(e);
//						
//						fs.setFormulaireServiceID(fsID);
//						
//						FormulaireServiceDAO.insertOrUpdate(fs);
//					}
					
						BDDUtils.commit(isActive, tx);
					
					} catch(HibernateException ex) {
						Logger.error("Hibernate failure : "+ ex.getMessage());
						BDDUtils.rollback(isActive, tx);
						return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
					}
					return ok();
				}
			return notFound();
		});
	}
	
}