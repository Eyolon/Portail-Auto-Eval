package hibernate.dao;

import java.util.List;

import hibernate.model.Note;

public class NoteDAO extends BasicDAO {
	
	public static Note findById(Long id) {
		return findById(Note.class, id);
	}
	
	public static List<Note> getAll() {
		return getAll(Note.class);
	}
}