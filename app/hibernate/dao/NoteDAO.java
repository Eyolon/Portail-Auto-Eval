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
	
	public static List<Note> getListNoteByUserId(Long idUser) {
		List <Note> lf = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
			"SELECT n "+
			"FROM Note AS n, "+
			"Utilisateur AS u "+
			"WHERE u.id = :id");
			
			q.setParameter("id", idUser);
			lf = q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return lf;
	}
}
