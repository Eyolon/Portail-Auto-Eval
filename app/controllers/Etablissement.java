package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.fasterxml.jackson.databind.JsonNode;

import hibernate.dao.EtablissementDAO;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Etablissement extends Controller{
	
	public static Promise<Result> setEtablissement(String etablissement) {
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
								
					hibernate.model.Etablissement e = new hibernate.model.Etablissement();					
					
					if(jsonN.has("etablissement")) {
						e.setLibelle(jsonN.get("etablissement").asText());
					}
					
					EtablissementDAO.insert(e);
				
					BDDUtils.commit(isActive, tx);
						
					return ok();	
					
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