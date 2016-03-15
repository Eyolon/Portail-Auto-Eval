package hibernate.dao;

import java.util.List;

import hibernate.model.Question;

public class QuestionDAO extends BasicDAO {
	
	public static Question findById(Long id) {
		return findById(Question.class, id);
	}
	
	public static List<Question> getAll() {
		return getAll(Question.class);
	}
}
