import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import hibernate.dao.BDDUtils;
import hibernate.dao.GenreDAO;
import hibernate.dao.TypeUtilisateurDAO;
import hibernate.model.Genre;
import hibernate.model.TypeUtilisateur;
import play.Application;
import play.GlobalSettings;
import play.Logger;

public class Global extends GlobalSettings {
	
	private void insertDefaultValuesInDB() {
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			
			//Default values for TypeUtilisateur
			if(TypeUtilisateurDAO.getTypeUtilisateurByLibelle("administrateur") == null) {
				BDDUtils.insert(new TypeUtilisateur("administrateur"));
			}
			if(TypeUtilisateurDAO.getTypeUtilisateurByLibelle("utilisateur") == null) {
				BDDUtils.insert(new TypeUtilisateur("utilisateur"));
			}
			
			//Default values for Genre
			if(GenreDAO.getGenreByLibelle("Homme") == null) {
				BDDUtils.insert(new Genre("Homme"));
			}
			if(GenreDAO.getGenreByLibelle("Femme") == null) {
				BDDUtils.insert(new Genre("Femme"));
			}
			
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Hibernate failure : "+ ex.getMessage());
			BDDUtils.rollback(isActive, tx);
		}
	}

	public void onStart(Application app) {
		Logger.info("Application has started.");
		try {
			BDDUtils.getCurrentSession();
			insertDefaultValuesInDB();
		} catch (HibernateException he) {
			Logger.error("Impossible de se connecter à la base de donnée.");
			Logger.error(BDDUtils.createDBProperties().toString());
		}
	}
	
	public void onStop(Application app) {
		Logger.info("Application shutdown ...");
		try {
			if(BDDUtils.getCurrentSession().isConnected()) {
				BDDUtils.disconnect();
			}
		} catch (HibernateException he) {
			Logger.error("Impossible de se connecter à la base de donnée.");
		}
	}
}
