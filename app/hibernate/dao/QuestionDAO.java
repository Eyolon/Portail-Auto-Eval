package hibernate.dao;

import java.util.List;

import hibernate.model.Question;

public class QuestionDAO extends BasicDAO<Question> {
	
	public static Question findById(Long id) {
		return findById(Question.class, id);
	}
	
	public static List<Question> getAll(Long id) {
		return getAll(Question.class);
	}
}
