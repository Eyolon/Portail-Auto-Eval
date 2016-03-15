package Tools;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import hibernate.model.Utilisateur;

public class ConstructJSONObjects {
	
	public static JSONObject getJSONforUser(Utilisateur u) {
		return new JSONObject()
		.put("id", u.getId())
		.put("login", u.getLogin());
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
