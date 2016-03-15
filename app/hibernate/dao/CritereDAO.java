package hibernate.dao;

import java.util.List;

import hibernate.model.Critere;

public class CritereDAO extends BasicDAO {
	
	public static Critere findById(Long id) {
		return findById(Critere.class, id);
	}
	
	public static List<Critere> getAll() {
		return getAll(Critere.class);
	}
}
