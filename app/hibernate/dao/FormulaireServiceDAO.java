package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.FormulaireService;
import hibernate.utils.BDDUtils;
import play.Logger;

public class FormulaireServiceDAO extends BasicDAO {
	
	public static FormulaireService findById(Long formulaireId, Long serviceId, Long typeUtilisateurId, Long etablissementId) {
		FormulaireService fs = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT fs FROM FormulaireService as fs " +
					"WHERE f.formulaireServiceID.formulaire.id = :formulaireId " +
					"AND f.formulaireServiceID.service.id = :serviceId " +
					"AND f.formulaireServiceID.typeUtilisateur.id = :typeUtilisateurId " +
					"AND f.formulaireServiceID.etablissement.id = :etablissementId");
			q.setParameter("formulaireId", formulaireId);
			q.setParameter("serviceId", serviceId);
			q.setParameter("typeUtilisateurId", typeUtilisateurId);
			q.setParameter("etablissementId", etablissementId);
			fs = (FormulaireService) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur FormulaireServiceDAO findById : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return fs;
	}
	
	public static List<FormulaireService> getAll() {
		return getAll(FormulaireService.class);
	}
}
