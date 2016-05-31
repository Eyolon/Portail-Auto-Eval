package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;
import org.json.JSONArray;

import com.fasterxml.jackson.databind.JsonNode;

import Tools.ConstructJSONObjects;
import hibernate.dao.FormulaireDAO;
import hibernate.dao.FormulaireServiceDAO;
import hibernate.dao.ServiceDAO;
import hibernate.dao.TypeUtilisateurDAO;
import hibernate.model.Formulaire;
import hibernate.model.FormulaireService;
import hibernate.model.FormulaireServiceID;
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
				
				ja = ConstructJSONObjects.getJSONArrayforListFormulairesFull(FormulaireDAO.getAll());
				
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
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					//Il s'avere qu'il se réfère a la primary key, qu'importe si l'objet est initialisé avec un new ou pas
					//Ici , la PK est l'id de la question donc si y a pas d'id on est tranquil, il créer une nouvelle clef et question
					if(jsonN.has("formulaire") && jsonN.get("formulaire").has("id")){
						Formulaire f = new Formulaire();
						f.setId(jsonN.get("formulaire").get("id").asLong());
						
						if(jsonN.get("formulaire").get("nom").asText() != ""){
							f.setNom(jsonN.get("formulaire").get("nom").asText());
						}
						FormulaireDAO.save(f);
					}
					
					else if(jsonN.has("formulaire")&& jsonN.get("formulaire").has("nom")){
						Formulaire f = new Formulaire();
						FormulaireService fs = new FormulaireService();
						FormulaireServiceID fsID = new FormulaireServiceID();
						
						f.setNom(jsonN.get("formulaire").get("nom").asText());
						FormulaireDAO.save(f);
						
						
						Service s = ServiceDAO.findById(jsonN.get("formulaire").get("service").get("id").asLong());
						TypeUtilisateur tu = TypeUtilisateurDAO.findById(jsonN.get("formulaire").get("type").get("id").asLong());
						fsID.setFormulaire(f);
						fsID.setService(s);
						fsID.setTypeUtilisateur(tu);
						
						fs.setFormulaireServiceID(fsID);
						
						FormulaireServiceDAO.insertOrUpdate(fs);
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
	
}