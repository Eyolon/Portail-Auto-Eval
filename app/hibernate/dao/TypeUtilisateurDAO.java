package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.TypeUtilisateur;
import hibernate.utils.BDDUtils;
import play.Logger;

public class TypeUtilisateurDAO extends BasicDAO {
	
	public static TypeUtilisateur findById(Long id) {
		return findById(TypeUtilisateur.class, id);
	}
	
	public static List<TypeUtilisateur> getAll() {
		return getAll(TypeUtilisateur.class);
	}
	
	public static List<TypeUtilisateur> getAllOrderAscByColumn() {
		return getAllOrderAscByColumn(TypeUtilisateur.class, "libelle");
	}
	
	public static TypeUtilisateur getTypeUtilisateurByLibelle(String libelle) {
		TypeUtilisateur tu = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT tu FROM TypeUtilisateur tu " +
					"WHERE tu.libelle = :libelle");
			q.setParameter("libelle", libelle);
			tu = (TypeUtilisateur) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur TypeUtilisateur getTypeUtilisateurByLibelle : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return tu;
	}
}
