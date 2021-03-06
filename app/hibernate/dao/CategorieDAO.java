package hibernate.dao;

import java.util.List;

import hibernate.model.Categorie;

public class CategorieDAO extends BasicDAO {
	
	public static Categorie findById(Long id) {
		return findById(Categorie.class, id);
	}
	
	public static List<Categorie> getAll() {
		return getAll(Categorie.class);
	}
}
