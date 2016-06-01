package controllers;

import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import com.fasterxml.jackson.databind.JsonNode;

import hibernate.dao.ServiceDAO;
import hibernate.utils.BDDUtils;
import play.Logger;
import play.libs.F.Promise;
import play.mvc.Controller;
import play.mvc.Result;

public class Service extends Controller{
	
	public static Promise<Result> setService(String service) {
		return Promise.promise(() -> 
		{
			JsonNode jsonN = request().body().asJson();
			
			if(jsonN != null){
				Transaction tx = null;
				boolean isActive = BDDUtils.getTransactionStatus();
				try {
					tx = BDDUtils.beginTransaction(isActive);
								
					hibernate.model.Service s = new hibernate.model.Service();					
					
					if(jsonN.has("service")) {
						s.setLibelle(jsonN.get("service").asText());
					}
					
					ServiceDAO.insert(s);
				
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