package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Utilisateur;
import play.Logger;

public class UtilisateurDAO extends BasicDAO<Utilisateur> {

	public static Utilisateur findById(Long id) {
		return findById(Utilisateur.class, id);
	}
	
	public static List<Utilisateur> getAll(Long id) {
		return getAll(Utilisateur.class);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Utilisateur> getUtilisateurByNom(String nom) {
		List<Utilisateur> u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"WHERE UPPER(u.nom) like :nom");
			q.setParameter("nom", "%" + nom.toUpperCase() + "%");
			u = (List<Utilisateur>) q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return u;
	}
	
	public static Utilisateur getUtilisateurByEmail(String email) {
		Utilisateur u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"LEFT OUTER JOIN FETCH u.connexion " +
					"WHERE u.email = :email");
			q.setParameter("email", email);
			u = (Utilisateur) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return u;
	}
}
