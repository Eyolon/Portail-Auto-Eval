package hibernate.dao;

import java.util.List;

import hibernate.model.Connexion;

public class ConnexionDAO extends BasicDAO {
	
	public static Connexion findById(Long id) {
		return findById(Connexion.class, id);
	}
	
	public static List<Connexion> getAll() {
		return getAll(Connexion.class);
	}
}
