package hibernate.dao;

import java.util.List;

import hibernate.model.Service;

public class ServiceDAO extends BasicDAO {
	
	public static Service findById(Long id) {
		return findById(Service.class, id);
	}
	
	public static List<Service> getAll() {
		return getAll(Service.class);
	}
}
