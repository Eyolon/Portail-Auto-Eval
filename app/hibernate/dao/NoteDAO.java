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
}
