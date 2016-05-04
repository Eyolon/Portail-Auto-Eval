package hibernate.dao;

import java.util.List;

import hibernate.model.Etablissement;

public class EtablissementDAO extends BasicDAO {
	
	public static Etablissement findById(Long id) {
		return findById(Etablissement.class, id);
	}
	
	public static List<Etablissement> getAll() {
		return getAll(Etablissement.class);
	}
}
