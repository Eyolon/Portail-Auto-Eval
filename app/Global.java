import org.hibernate.HibernateException;
import org.hibernate.Transaction;

import hibernate.dao.TypeUtilisateurDAO;
import hibernate.model.TypeUtilisateur;
import hibernate.utils.BDDUtils;
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
			BDDUtils.disconnect();
		} catch (HibernateException he) {
			Logger.error("La liaison avec la base de donnée est déjà interrompue.");
		}
	}
}
