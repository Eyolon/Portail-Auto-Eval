package hibernate.dao;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.Transaction;

import hibernate.model.Formulaire;
import hibernate.utils.BDDUtils;
import play.Logger;

public class FormulaireDAO extends BasicDAO {
	
	public static Formulaire findById(Long id) {
		return findById(Formulaire.class, id);
	}
	
	public static List<Formulaire> getAll() {
		return getAll(Formulaire.class);
	}
	
	public static Formulaire getFormulaireByNom(String nom) {
		Formulaire f = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT f FROM Formulaire as f " +
					"WHERE f.nom = :nom");
			q.setParameter("nom", nom);
			f = (Formulaire) q.uniqueResult();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur FormulaireDAO getFormulaireByNom : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return f;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Formulaire> getListFormulaireByUserId(long idType) {
		List<Formulaire> lf = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				"SELECT fs.formulaireServiceID.formulaire FROM FormulaireService AS fs "+
				"WHERE fs.formulaireServiceID.service.id = :idType");
				/*"SELECT fs.formulaireServiceID.formulaire FROM FormulaireService AS fs "+
				* "WHERE fs.formulaireServiceID.utilisateur.id = :idUser");
				*  ne marche pas, genere une org.hibernate.QueryException: 
				* could not resolve property: formulaireServiceID of: hibernate.model.FormulaireService 
				* [SELECT fs.formulaireServiceID.formulaire FROM hibernate.model.formulaireService AS fs WHERE fs.formulaireServiceID.utilisateur.id = :idUser]
				* A mon sens normal, dans ton modèle formulaireServiceID n'a pas de properties utilisateur mais son id de type.
				* donc pour résoudre le soucis, je vais re consulter la base en fonction de l'id de service.
				* j'avais changer... par ce que je j'arrivais pas a chopper les formulaires et les reponses en même temps.
				*/
					
					/* "SELECT f "+		
					 "FROM Formulaire AS f, "+		
					 "FormulaireService AS fs, "+		
					 "Utilisateur AS u "+		
					 "WHERE f.id = fs.formulaireServiceID.formulaire.id "+	
					 "AND fs.formulaireServiceID.service.id = u.service.id "+		
					 "AND u.id = :id");*/
			q.setParameter("idType", idType);
			lf = (List<Formulaire>)q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur FormulaireDAO getListFormulaireByUserId : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return lf;
	}
	
	
}
