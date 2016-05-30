package hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Service;
import hibernate.utils.BDDUtils;
import play.Logger;

public class ServiceDAO extends BasicDAO {
	
	public static Service findById(Long id) {
		return findById(Service.class, id);
	}
	
	public static List<Service> getAll() {
		return getAll(Service.class);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Service> getAllServiceByEtablissement(Long idEtablissement) {
		List<Service> lq = new ArrayList<>();
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT DISTINCT fs.formulaireServiceID.service FROM FormulaireService as fs " +
					"WHERE fs.formulaireServiceID.etablissement.id = :idEtablissement");
			q.setParameter("idEtablissement", idEtablissement);
			lq = (List<Service>)q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Erreur QuestionDAO getListQuestionByFormulaireId : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return lq;
	}
}
