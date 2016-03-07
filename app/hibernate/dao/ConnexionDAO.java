package hibernate.dao;

import java.util.List;

import hibernate.model.Connexion;

public class ConnexionDAO extends BasicDAO<Connexion> {
	
	public static Connexion findById(Long id) {
		return findById(Connexion.class, id);
	}
	
	public static List<Connexion> getAll(Long id) {
		return getAll(Connexion.class);
	}
}
