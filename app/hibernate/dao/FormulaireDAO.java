package hibernate.dao;

import java.util.List;

import hibernate.model.Formulaire;

public class FormulaireDAO extends BasicDAO {
	
	public static Formulaire findById(Long id) {
		return findById(Formulaire.class, id);
	}
	
	public static List<Formulaire> getAll() {
		return getAll(Formulaire.class);
	}
}
