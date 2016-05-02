package Tools;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hibernate.model.Categorie;
import hibernate.model.Critere;
import hibernate.model.Formulaire;
import hibernate.model.Note;
import hibernate.model.Question;
import hibernate.model.Service;
import hibernate.model.TypeUtilisateur;
import hibernate.model.Utilisateur;

public class ConstructJSONObjects {
	
	public static JSONObject getJSONforUser(Utilisateur u) throws JSONException {
		return new JSONObject()
		.put("id", u.getId())
		.put("login", u.getLogin())
		.put("service", getJSONforService(u.getService()))
		.put("typeUtilisateur", getJSONforTypeUtilisateur(u.getTypeUtilisateur()));
	}
	
	public static JSONArray getJSONArrayforNote(List<Note> ln) throws JSONException{
		if(ln != null) {
			JSONArray ja = new JSONArray();
			for (Note note : ln) {
				ja.put(getJSONforNote(note));
			}
			return ja;
		}
		return null;
	}
	
	private static JSONObject getJSONforNote(Note note) throws JSONException {
		return new JSONObject()
				.put("id", note.getId())
				.put("valeur", note.getValeur())
				.put("remarque", note.getRemarque())
				.put("utilisateur" ,getJSONforUser(note.getUtilisateur()));
				//.put("question", getJSONforQuestion(note.getQuestion())); Créer une boucle infinie : la question appel la note et la note appel la question...
		/*
		 * Il semble que si je décomente, sa plante. mais de toute facon c'est tout se dont j'ai besoin
		 */
	}

	public static JSONArray getJSONArrayforListUsers(List<Utilisateur> lu) throws JSONException {
		if(lu != null) {
			JSONArray ja = new JSONArray();
			for (Utilisateur user : lu) {
				ja.put(getJSONforUser(user));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforCategorie(Categorie c) throws JSONException {
		return new JSONObject()
				.put("id", c.getId())
				.put("libelle", c.getLibelle());
	}
	
	public static JSONObject getJSONforCritere(Critere c) throws JSONException {
		return new JSONObject()
				.put("id", c.getId())
				.put("libelle", c.getLibelle())
				.put("categorie", getJSONforCategorie(c.getCategorie()));
	}
	
	public static JSONObject getJSONforQuestion(Question q) throws JSONException {
		return new JSONObject()
				.put("id", q.getId())
				.put("valeur", q.getValeur())
				.put("critere", getJSONforCritere(q.getCritere()))
				.put("formulaire", getJSONforFormulaire(q.getFormulaire()))
				.put("notes", getJSONArrayforNote(q.getNotes()));
	}
	
	public static JSONArray getJSONArrayforListQuestions(List<Question> lq) throws JSONException {
		if(lq != null) {
			JSONArray ja = new JSONArray();
			for (Question question : lq) {
				ja.put(getJSONforQuestion(question));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforFormulaire(Formulaire f) throws JSONException {
		return new JSONObject()
				.put("id", f.getId())
				.put("nom", f.getNom());
	}
	
	public static JSONArray getJSONArrayforListFormulaires(List<Formulaire> lf) throws JSONException {
		if(lf != null) {
			JSONArray ja = new JSONArray();
			for (Formulaire formulaire : lf) {
				ja.put(getJSONforFormulaire(formulaire));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONArray getJSONArrayforListFormulairesFull(List<Formulaire> lf) throws JSONException {
		if(lf != null) {
			JSONArray ja = new JSONArray();
			for (Formulaire formulaire : lf) {
				ja.put(getJSONforFormulaireFull(formulaire));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforFormulaireFull(Formulaire f) throws JSONException {
		return new JSONObject()
				.put("id", f.getId())
				.put("nom", f.getNom())
				.put("listQuestions", getJSONArrayforListQuestions(f.getQuestions()));
	}
	
	public static JSONObject getJSONforService(Service s) throws JSONException{
		return new JSONObject()
				.put("id",s.getId())
				.put("libelle",s.getLibelle());
	}
	
	public static JSONArray getJSONArrayforListServices(List<Service> ls) throws JSONException{
		if(ls != null) {
			JSONArray ja = new JSONArray();
			for (Service service : ls) {
				ja.put(getJSONforService(service));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforTypeUtilisateur(TypeUtilisateur tu) throws JSONException{
		return new JSONObject()
				.put("id",tu.getId())
				.put("libelle",tu.getLibelle());
	}
	
	public static JSONArray getJSONArrayforListTypesUtilisateur(List<TypeUtilisateur> ltu) throws JSONException{
		if(ltu != null) {
			JSONArray ja = new JSONArray();
			for (TypeUtilisateur typeUtilisateur : ltu) {
				ja.put(getJSONforTypeUtilisateur(typeUtilisateur));
			}
			return ja;
		}
		return null;
	}
}
