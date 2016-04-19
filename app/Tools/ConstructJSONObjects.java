package Tools;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import hibernate.model.Categorie;
import hibernate.model.Critere;
import hibernate.model.Formulaire;
import hibernate.model.Note;
import hibernate.model.Question;
import hibernate.model.Service;
import hibernate.model.Utilisateur;

public class ConstructJSONObjects {
	
	public static JSONObject getJSONforUser(Utilisateur u) {
		return new JSONObject()
		.put("id", u.getId())
		.put("login", u.getLogin())
		.put("service", getJSONforService(u.getService()));
	}
	
	public static JSONArray getJSONArrayforNote(List<Note> ln){
		if(ln != null) {
			JSONArray ja = new JSONArray();
			for (Note note : ln) {
				ja.put(getJSONforNote(note));
			}
			return ja;
		}
		return null;
	}
	
	private static JSONObject getJSONforNote(Note note) {
		return new JSONObject()
				.put("id", note.getId())
				.put("valeur", note.getValeur())
				.put("remarque", note.getRemarque())
				.put("utilisateur" , note.getUtilisateur())
				.put("question", note.getQuestion());
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
	
	public static JSONObject getJSONforCategorie(Categorie c) {
		return new JSONObject()
				.put("id", c.getId())
				.put("libelle", c.getLibelle());
	}
	
	public static JSONObject getJSONforCritere(Critere c) {
		return new JSONObject()
				.put("id", c.getId())
				.put("libelle", c.getLibelle())
				.put("", getJSONforCategorie(c.getCategorie()));
	}
	
	public static JSONObject getJSONforQuestion(Question q) {
		return new JSONObject()
				.put("id", q.getId())
				.put("valeur", q.getValeur())
				.put("critere", getJSONforCritere(q.getCritere()))
				.put("formulaire", getJSONforFormulaire(q.getFormulaire()));
				//.put("notes", getJSONArrayforNote(q.getNotes()));
	}
	
	public static JSONArray getJSONArrayforListQuestions(List<Question> lq) {
		if(lq != null) {
			JSONArray ja = new JSONArray();
			for (Question question : lq) {
				ja.put(getJSONforQuestion(question));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforFormulaire(Formulaire f) {
		return new JSONObject()
				.put("id", f.getId())
				.put("nom", f.getNom());
	}
	
	public static JSONArray getJSONArrayforListFormulaires(List<Formulaire> lf) {
		if(lf != null) {
			JSONArray ja = new JSONArray();
			for (Formulaire formulaire : lf) {
				ja.put(getJSONforFormulaire(formulaire));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONArray getJSONArrayforListFormulairesFull(List<Formulaire> lf) {
		if(lf != null) {
			JSONArray ja = new JSONArray();
			for (Formulaire formulaire : lf) {
				ja.put(getJSONforFormulaireFull(formulaire));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforFormulaireFull(Formulaire f) {
		return new JSONObject()
				.put("id", f.getId())
				.put("nom", f.getNom())
				.put("listQuestions", getJSONArrayforListQuestions(f.getQuestions()));
	}
	
	public static JSONObject getJSONforService(Service s){
		return new JSONObject()
				.put("id",s.getId())
				.put("libelle",s.getLibelle());
	}
}
