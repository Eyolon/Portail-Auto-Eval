package hibernate.dao;

import java.util.List;

import hibernate.model.Critere;

public class CritereDAO extends BasicDAO<Critere> {
	
	public static Critere findById(Long id) {
		return findById(Critere.class, id);
	}
	
	public static List<Critere> getAll(Long id) {
		return getAll(Critere.class);
	}
}
