package hibernate.dao;

import java.util.ArrayList;
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
	
	public static boolean save(Formulaire object){
		return insertOrUpdate(object);
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
	public static List<Formulaire> getListFormulaireByUserId(long idUser) {
		List<Formulaire> lf = null;
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
				/*"SELECT fs.formulaireServiceID.formulaire FROM FormulaireService AS fs "+
				* "WHERE fs.formulaireServiceID.utilisateur.id = :idUser");
				*  ne marche pas, genere une org.hibernate.QueryException: 
				* could not resolve property: formulaireServiceID of: hibernate.model.FormulaireService 
				* [SELECT fs.formulaireServiceID.formulaire FROM hibernate.model.formulaireService AS fs WHERE fs.formulaireServiceID.utilisateur.id = :idUser]
				* A mon sens normal, dans ton modèle formulaireServiceID n'a pas de properties utilisateur mais son id de type.
				* donc pour résoudre le soucis, je vais re consulter la base en fonction de l'id de service.
				* j'avais changer... par ce que je j'arrivais pas a chopper les formulaires et les reponses en même temps.
				* 
				* NOTABENE : 21/04/2016
				* a défault d'une solution qui marche pour faire en fonction de l'UTILISATEUR je met ca pour que sa marche
				*/
					
				 "SELECT fs.formulaireServiceID.formulaire "+		
				 "FROM FormulaireService AS fs, "+			
				 "Utilisateur AS u "+		
				 "WHERE u.id = :idUser "+		
				 "AND fs.formulaireServiceID.service.id = u.service.id ");
			q.setParameter("idUser", idUser);
			lf = (List<Formulaire>)q.list();
			BDDUtils.commit(isActive, tx);
		} catch(Exception ex) {
			Logger.error("Erreur FormulaireDAO getListFormulaireByUserId : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return lf;
	}
	
	@SuppressWarnings("unchecked")
	public static List<Formulaire> getAllServiceByEtablissement(List<Long> idEtablissement, List<Long> idService) {
		List<Formulaire> lf = new ArrayList<>();
		Transaction tx = null;
		boolean isActive = BDDUtils.getTransactionStatus();
		try {
			tx = BDDUtils.beginTransaction(isActive);
			Query q = BDDUtils.getCurrentSession().createQuery(
					"SELECT DISTINCT fs.formulaireServiceID.formulaire FROM FormulaireService as fs " +
					"WHERE fs.formulaireServiceID.etablissement.id IN :idEtablissement " +
					"AND fs.formulaireServiceID.service.id IN :idService");
			q.setParameterList("idEtablissement", idEtablissement);
			q.setParameterList("idService", idService);
			lf = (List<Formulaire>)q.list();
			BDDUtils.commit(isActive, tx);
		}
		catch(Exception ex) {
			Logger.error("Erreur FormulaireDAO getAllServiceByEtablissement : ", ex);
			BDDUtils.rollback(isActive, tx);
		}
		return lf;
	}
}
