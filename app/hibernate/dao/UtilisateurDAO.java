package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Utilisateur;

public class UtilisateurDAO {

	public static Utilisateur findById(Long id) {
		Utilisateur u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = null;
			q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"WHERE u.id = :id");
			q.setParameter("id", id);
			u = (Utilisateur) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			System.out.println("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return u;
	}
	
	public static Utilisateur findByIdWithEager(Long id) {
		Utilisateur u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = null;
			q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"LEFT OUTER JOIN FETCH u.connexion " +
					"LEFT OUTER JOIN FETCH u.genre " +
					"LEFT OUTER JOIN FETCH u.typeUtilisateur " +
					"WHERE u.id = :id");
			q.setParameter("id", id);
			u = (Utilisateur) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			System.out.println("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Utilisateur> getUtilisateurByNom(String nom) {
		List<Utilisateur> u = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = null;
			q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"WHERE UPPER(u.nom) like :nom");
			q.setParameter("nom", "%" + nom.toUpperCase() + "%");
			u = (List<Utilisateur>) q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			System.out.println("Hibernate failure : "+ ex.getMessage());
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
			Query q = null;
			q = BDDUtils.getCurrentSession().createQuery(
					"SELECT u FROM Utilisateur as u " +
					"LEFT OUTER JOIN FETCH u.connexion " +
					"WHERE u.adresseMail = :email");
			q.setParameter("email", email);
			u = (Utilisateur) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			System.out.println("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return u;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Utilisateur> getAllUsers() {
		List<Utilisateur> lu = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = null;
			q = BDDUtils.getCurrentSession().createQuery("SELECT u FROM Utilisateur u");
			lu = (List<Utilisateur>) q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			System.out.println("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return lu;
	}
}
