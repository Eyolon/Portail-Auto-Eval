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
					"SELECT f FROM Formulaire as f " +
					"WHERE f.nom = :nom");
			q.setParameter("nom", nom);
			f = (Formulaire) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur FormulaireDAO getFormulaireByNom : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Formulaire> getListFormulaireByUserId(long idUser) {
		List<Formulaire> lf = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				"SELECT fs.formulaireServiceID.formulaire FROM FormulaireService AS fs "+
				"WHERE fs.formulaireServiceID.utilisateur.id = :idUser");
			q.setParameter("idUser", idUser);
			lf = (List<Formulaire>)q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur FormulaireDAO getListFormulaireByUserId : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return lf;
	}
	
	
}
