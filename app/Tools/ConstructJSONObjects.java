package Tools;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import hibernate.model.Utilisateur;

public class ConstructJSONObjects {
	
	public static JSONObject getJSONforUser(Utilisateur u) {
		return new JSONObject()
		.put("id", u.getId())
		.put("lastname", u.getNom())
		.put("firstname", u.getPrenom())
		.put("birthday", Tools.formatDateToDisplay(u.getDateNaissance()));
	}
	
	public static JSONObject getJSONforUserFull(Utilisateur u) {
		return new JSONObject()
		.put("id", u.getId())
		.put("lastname", u.getNom())
		.put("firstname", u.getPrenom())
		//.put("pwd", u.getConnexion().getPassword())
		.put("adresseRue", u.getAdresse())
		.put("adresseVille", u.getVille().getNom())
		.put("adresseCodePostal", u.getAdresseCodePostal())
		.put("adresseMail", u.getAdresseMail())
		.put("telephone", u.getTelephone())
		.put("genre", u.getGenre().getGenre())
		.put("birthday", Tools.formatDateToDisplay(u.getDateNaissance()));
	}
	
	public static JSONArray getJSONArrayforListUsers(List<Utilisateur> lu) {
		if(lu != null) {
			JSONArray ja = new JSONArray();
			for (Utilisateur user : lu) {
				ja.put(getJSONforUser(user));
			}
			return ja;
		}
		return null;
	}
}
