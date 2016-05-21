package hibernate.dao;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Question;
import hibernate.utils.BDDUtils;
import play.Logger;

public class QuestionDAO extends BasicDAO {
	
	public static Question findById(Long id) {
		return findById(Question.class, id);
	}
	
	public static List<Question> getAll() {
		return getAll(Question.class);
	}
	
	public static boolean save(Question object){
		return insertOrUpdate(object);
	}
	
	@SuppressWarnings("unchecked")
	public static List<Question> getListQuestionByFormulaireId(long idForm) {
		List<Question> lq = new ArrayList<>();
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT q FROM question as q " +
					"WHERE q.formulaire.id = :idFormulaire");
			q.setParameter("idFormulaire", idForm);
			lq = (List<Question>)q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Erreur QuestionDAO getListQuestionByFormulaireId : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return lq;
	}
}
