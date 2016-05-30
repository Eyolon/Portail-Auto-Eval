package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Note;
import hibernate.utils.BDDUtils;
import play.Logger;

public class NoteDAO extends BasicDAO {
	
	public static Note findById(Long id) {
		return findById(Note.class, id);
	}
	
	public static List<Note> getAll() {
		return getAll(Note.class);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Note> getListNoteByUserId(Long idUser) {
		List<Note> ln = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				"SELECT n FROM Note AS n "+
				"WHERE n.utilisateur.id = :idUser");
			q.setParameter("idUser", idUser);
			ln = (List<Note>) q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur NoteDAO getListNoteByUserId: ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return ln;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Note> getListNoteByEtablissementAndService(Long idEtablissement, Long idService) {
		List<Note> ln = null;
		Transaction tx = null;
		
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				"SELECT n FROM Note AS n "+
				"WHERE n.utilisateur.etablissement.id = :idEtablissement " +
				"AND n.utilisateur.service.id = :idService");
			q.setParameter("idEtablissement", idEtablissement);
			q.setParameter("idService", idService);
			ln = (List<Note>) q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur NoteDAO getListNoteByEtablissementAndService: ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return ln;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Note> getListNoteByEtablissementsAndServices(List<Long> idEtablissements, List<Long> idServices) {
		List<Note> ln = null;
		Transaction tx = null;
		
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				"SELECT n FROM Note AS n "+
				"WHERE n.utilisateur.etablissement.id IN :idEtablissements " +
				"AND n.utilisateur.service.id IN :idServices");
			q.setParameterList("idEtablissements", idEtablissements);
			q.setParameterList("idServices", idServices);
			ln = (List<Note>) q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur NoteDAO getListNoteByEtablissementsAndServices: ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return ln;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Note> getListNoteAndDetailByEtablissementId(Long idEtablissement) {
		List<Note> ln = null;
		Transaction tx = null;
		
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				"SELECT n FROM Note AS n "+
				"WHERE n.utilisateur.etablissement.id = :idEtablissement");
			q.setParameter("idEtablissement", idEtablissement);
			ln = (List<Note>) q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur NoteDAO getListNoteAndDetailByEtablissementId: ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return ln;
	}
}
