package Tools;

import org.json.JSONObject;
import org.junit.Assert;
import org.junit.Test;

import hibernate.model.Connexion;
import hibernate.model.Etablissement;
import hibernate.model.Service;
import hibernate.model.TypeUtilisateur;
import hibernate.model.Utilisateur;

public class ConstructJSONObjectsTest {

	@Test
	public void testGetJSONforUser() throws Exception {
		Connexion c = new Connexion(1L, "password");
		TypeUtilisateur tu = new TypeUtilisateur(1L, "utilisateur");
		Etablissement e = new Etablissement(1L, "etablissement");
		Service s = new Service(1L, "service");
		Utilisateur u = new Utilisateur();
		u.setId(1L);
		u.setIsActif(true);
		u.setLogin("test");
		u.setConnexion(c);
		u.setTypeUtilisateur(tu);
		u.setEtablissement(e);
		u.setService(s);
		
		JSONObject jo = ConstructJSONObjects.getJSONforUser(u);

		JSONObject result = new JSONObject().put("id", u.getId())
		.put("login", u.getLogin())
		.put("service", new JSONObject().put("id", s.getId()).put("libelle", s.getLibelle()))
		.put("etablissement", new JSONObject().put("id", e.getId()).put("libelle", e.getLibelle()))
		.put("typeUtilisateur", new JSONObject().put("id", tu.getId()).put("libelle", tu.getLibelle()))
		.put("actif", u.getIsActif());
		Assert.assertTrue(jo.toString().equals(result.toString()));
	}
}
