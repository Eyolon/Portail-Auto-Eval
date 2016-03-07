package hibernate.dao;

import java.util.List;

import org.hibernate.Transaction;

import play.Logger;

public abstract class BasicDAO<T> {

	public static <T> T findById(Class<T> table, Long id) {
		T o = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			o = BDDUtils.getCurrentSession().get(table, id);
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return o;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> List<T> getAll(Class<T> table) {
		List<T> all = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			all = BDDUtils.getCurrentSession().createCriteria(table).list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return all;
	}
	
	public static <T> boolean insertOrUpdate(T object) {
		boolean b = false;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try{
			tx = BDDUtils.beginTransaction(isActive);
			BDDUtils.getCurrentSession().saveOrUpdate(object);
			BDDUtils.commit(isActive, tx);
			b = true;
		}
		catch(Exception ex)
		{
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return b;
	}
	
	public static <T> boolean update(T object) {
		boolean b = false;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try{
			tx = BDDUtils.beginTransaction(isActive);
			BDDUtils.getCurrentSession().update(object);
			BDDUtils.commit(isActive, tx);
			b = true;
		}
		catch(Exception ex)
		{
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return b;
	}
	
	public static <T> boolean insert(T object) {
		boolean b = false;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try{
			tx = BDDUtils.beginTransaction(isActive);
			BDDUtils.getCurrentSession().save(object);
			BDDUtils.commit(isActive, tx);
			b = true;
		}
		catch(Exception ex)
		{
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return b;
	}
	
	public static <T> boolean delete(T object) {
		boolean b = false;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try{
			tx = BDDUtils.beginTransaction(isActive);
			BDDUtils.getCurrentSession().delete(object);
			BDDUtils.commit(isActive, tx);
			b = true;
		}
		catch(Exception ex)
		{
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
		return b;
	}
}
