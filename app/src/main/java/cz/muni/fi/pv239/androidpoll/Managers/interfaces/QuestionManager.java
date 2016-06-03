package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Entities.Option;


/**
 * Created by Administrátor on 24.5.2016.
 */
public interface QuestionManager {

    public List<Category> getAllCategories();
    public Question getRandomQuestion(Category category, String login, String password);
    public List<Option> getQuestionOptions(Question q);
    public void createQuestion(String login, String password, Category category, String text);
    public void addOption(String login, String password, String text, Question q);
    public void deleteOption(Question q, String login, String password);
    public void skipQuestion(Question q, String login, String password);
    public int getNumberOfAnswers(Question q);
}
