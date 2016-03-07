package hibernate.dao;

import java.util.List;

import hibernate.model.Formulaire;

public class FormulaireDAO extends BasicDAO<Formulaire> {
	
	public static Formulaire findById(Long id) {
		return findById(Formulaire.class, id);
	}
	
	public static List<Formulaire> getAll(Long id) {
		return getAll(Formulaire.class);
	}
}
