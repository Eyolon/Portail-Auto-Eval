package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Formulaire;
import hibernate.utils.BDDUtils;
import play.Logger;

public class FormulaireDAO extends BasicDAO {
	
	public static Formulaire findById(Long id) {
		return findById(Formulaire.class, id);
	}
	
	public static List<Formulaire> getAll() {
		return getAll(Formulaire.class);
	}
	
	public static Formulaire getFormulaireByNom(String nom) {
		Formulaire f = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT f FROM formulaire as f " +
					"WHERE f.nom = :nom");
			q.setParameter("nom", nom);
			f = (Formulaire) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return f;
	}
}
