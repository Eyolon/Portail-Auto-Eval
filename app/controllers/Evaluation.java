package controllers;

import java.util.List;

import org.hibernate.Transaction;
import org.json.JSONArray;
import org.json.JSONObject;

import Tools.ConstructJSONObjects;
import hibernate.dao.FormulaireDAO;
import hibernate.model.Formulaire;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Evaluation extends Controller{
	
	public static Promise<Result> getListFormulaire(){
		
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
			return ok(ja.toString());
			
		});
		return promiseOfResult;
	}
	
	public static Promise<Result> getFormulaire(String nomForm){
		Promise<Result> promiseOfResult = Promise.promise(()->{

			JSONObject js = null;
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				Formulaire f = FormulaireDAO.getFormulaireByNom(nomForm);
				if(f != null) {
					js = ConstructJSONObjects.getJSONforFormulaireFull(f);
				}
				
				BDDUtils.commit(isActive, tx);
			}
			catch(Exception ex) {
				Logger.error("Hibernate failure : "+ ex.getMessage());
				BDDUtils.rollback(isActive, tx);
				return internalServerError("Une erreur est survenue pendant la transaction avec la base de données.");
			}
			if(js == null) {
				return notFound("Formulaire " + nomForm + " introuvable.");
			} else {
				return ok(js.toString());
			}
			
		});
		return promiseOfResult;
	}
	
	public static Promise<Result> getFormulaireFullForUser(Long idUser){

		Promise<Result> promiseOfResult = Promise.promise(()->{
			JSONArray ja = new JSONArray();
			Transaction tx = null;
			boolean isActive = BDDUtils.getTransactionStatus();
			try {
				tx = BDDUtils.beginTransaction(isActive);
				
				List<Formulaire> lf = FormulaireDAO.getListFormulaireByUserId(idUser);
				
				if(lf != null) {
					ja = ConstructJSONObjects.getJSONArrayforListFormulairesFull(lf);
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

}
