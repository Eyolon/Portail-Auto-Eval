package Tools;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import hibernate.model.Categorie;
import hibernate.model.Critere;
import hibernate.model.Etablissement;
import hibernate.model.Formulaire;
import hibernate.model.FormulaireService;
import hibernate.model.Note;
import hibernate.model.Question;
import hibernate.model.Service;
import hibernate.model.TypeUtilisateur;
import hibernate.model.Utilisateur;

public class ConstructJSONObjects {
	
	public static JSONObject getJSONforUser(Utilisateur u) throws JSONException {
		return u == null ? null : new JSONObject()
		.put("id", u.getId())
		.put("login", u.getLogin())
		.put("service", getJSONforService(u.getService()))
		.put("etablissement", getJSONforEtablissement(u.getEtablissement()))
		.put("typeUtilisateur", getJSONforTypeUtilisateur(u.getTypeUtilisateur()))
		.put("actif", u.getIsActif());
	}
	
	public static JSONObject getJSONforFormulaireService(FormulaireService f) throws JSONException{
		return f == null ? null : new JSONObject()
			.put("idFormulaire", f.getFormulaireServiceID().getFormulaire().getId())
			.put("idEtablissement", f.getFormulaireServiceID().getEtablissement().getId())
			.put("idService", f.getFormulaireServiceID().getService().getId())
			.put("idTypeUtilisateur", f.getFormulaireServiceID().getTypeUtilisateur().getId())
			.put("listQuestion", getJSONArrayforListQuestions(f.getFormulaireServiceID().getFormulaire().getQuestions()))
			.put("formulaire", getJSONforFormulaire(f.getFormulaireServiceID().getFormulaire()))
			.put("service", getJSONforService(f.getFormulaireServiceID().getService()))
			.put("etablissement", getJSONforEtablissement(f.getFormulaireServiceID().getEtablissement()))
			.put("typeUtilisateur", getJSONforTypeUtilisateur(f.getFormulaireServiceID().getTypeUtilisateur()));
	}
	
	public static JSONArray getJSONArrayforListFormulaireService(List<FormulaireService> ls) throws JSONException{
		if(ls != null) {
			JSONArray ja = new JSONArray();
			for (FormulaireService fs : ls) {
				ja.put(getJSONforFormulaireService(fs));
			}
			return ja;
		}
		return null;
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
	
	public static JSONArray getJSONArrayforNotePriorise(List<Note> ln) throws JSONException{
		if(ln != null) {
			JSONArray ja = new JSONArray();
			for (Note note : ln) {
				ja.put(getJSONforNotePriorise(note));
			}
			return ja;
		}
		return null;
	}
	
	private static JSONObject getJSONforNotePriorise(Note note) throws JSONException {
		return note == null ? null : new JSONObject()
				.put("id", note.getId())
				.put("valeur", note.getValeur())
				.put("remarque", note.getRemarque())
				.put("utilisateur" ,getJSONforUser(note.getUtilisateur()))
				.put("question", getJSONforQuestionWithoutNote(note.getQuestion()))
				.put("gravite", note.getGravite())
				.put("axeAmelioration1", note.getAxeAmelioration1())
				.put("axeAmelioration2", note.getAxeAmelioration2())
				.put("amelioration", note.getAmelioration());
		
	}
	
	public static JSONObject getJSONforNote(Note note) throws JSONException {
		return note == null ? null : new JSONObject()
				.put("id", note.getId())
				.put("valeur", note.getValeur())
				.put("remarque", note.getRemarque())
				.put("utilisateur" ,getJSONforUser(note.getUtilisateur())
				//.put("question", getJSONforQuestion(note.getQuestion())); Créer une boucle infinie : la question appel la note et la note appel la question...
				.put("gravite", note.getGravite())
				.put("axeAmelioration1", note.getAxeAmelioration1())
				.put("axeAmelioration2", note.getAxeAmelioration2())
				.put("amelioration", note.getAmelioration()));
		/*
		 * Il semble que si je décomente, sa plante. mais de toute facon c'est tout se dont j'ai besoin
		 */
	}
	
	public static JSONArray getJSONArrayforNoteWithQuestion(List<Note> ln) throws JSONException{
		if(ln != null) {
			JSONArray ja = new JSONArray();
			for (Note note : ln) {
				ja.put(getJSONforNoteWithQuestion(note));
			}
			return ja;
		}
		return null;
	}
	
	private static JSONObject getJSONforNoteWithQuestion(Note note) throws JSONException {
		return note == null ? null : new JSONObject()
				.put("id", note.getId())
				.put("valeur", note.getValeur())
				.put("remarque", note.getRemarque())
				.put("utilisateur" ,getJSONforUser(note.getUtilisateur()))
				.put("question", getJSONforQuestionWithoutNote(note.getQuestion())); // on a vaincu la boucle infinis !
	}
	
	public static JSONObject getJSONforQuestionWithoutNote(Question q) throws JSONException {
		return q == null ? null : new JSONObject()
				.put("id", q.getId())
				.put("valeur", q.getValeur())
				.put("critere", getJSONforCritere(q.getCritere()))
				.put("formulaire", getJSONforFormulaire(q.getFormulaire()));
				//.put("notes", getJSONArrayforNote(q.getNotes()));
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
		return c == null ? null : new JSONObject()
				.put("id", c.getId())
				.put("libelle", c.getLibelle());
	}
	
	public static JSONObject getJSONforCritere(Critere c) throws JSONException {
		return c == null ? null : new JSONObject()
				.put("id", c.getId())
				.put("libelle", c.getLibelle())
				.put("categorie", getJSONforCategorie(c.getCategorie()));
	}
	
	public static JSONArray getJSONArrayforListCritere(List<Critere> lc) throws JSONException {
		if(lc != null) {
			JSONArray ja = new JSONArray();
			for (Critere critere : lc) {
				ja.put(getJSONforCritere(critere));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforQuestion(Question q) throws JSONException {
		return q == null ? null : new JSONObject()
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
	
	public static JSONObject getJSONforFormulaire(Formulaire formulaire) throws JSONException {
		return formulaire == null ? null : new JSONObject()
				.put("id", formulaire.getId())
				.put("nom", formulaire.getNom());
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
		return f == null ? null : new JSONObject()
				.put("id", f.getId())
				.put("nom", f.getNom())
				.put("listQuestions", getJSONArrayforListQuestions(f.getQuestions()));
	}
	
	public static JSONObject getJSONforService(Service s) throws JSONException{
		return s == null ? null : new JSONObject()
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
	
	public static JSONObject getJSONforEtablissement(Etablissement e) throws JSONException{
		return e == null ? null : new JSONObject()
				.put("id",e.getId())
				.put("libelle",e.getLibelle());
	}
	
	public static JSONArray getJSONArrayforListEtablissements(List<Etablissement> le) throws JSONException{
		if(le != null) {
			JSONArray ja = new JSONArray();
			for (Etablissement etablissement : le) {
				ja.put(getJSONforEtablissement(etablissement));
			}
			return ja;
		}
		return null;
	}
	
	public static JSONObject getJSONforTypeUtilisateur(TypeUtilisateur tu) throws JSONException{
		return tu == null ? null : new JSONObject()
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
