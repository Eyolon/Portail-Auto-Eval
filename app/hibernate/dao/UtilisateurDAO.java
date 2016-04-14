package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Utilisateur;
import hibernate.utils.BDDUtils;
import play.Logger;

public class UtilisateurDAO extends BasicDAO {

	public static Utilisateur findById(Long id) {
		return findById(Utilisateur.class, id);
	}
	
	public static List<Utilisateur> getAll() {
		return getAll(Utilisateur.class);
	}
	
	public static Utilisateur getUtilisateurByLogin(String login) {
		Utilisateur u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"LEFT OUTER JOIN FETCH u.connexion " +
					"WHERE u.login = :login");
			q.setParameter("login", login);
			u = (Utilisateur) q.uniqueResult();
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
	
	public static Utilisateur getUtilisateurById(Long id) {
		Utilisateur u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"LEFT OUTER JOIN FETCH u.connexion " +
					"WHERE u.id = :email");
			q.setParameter("id", id);
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
