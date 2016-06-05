package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;


/**
 * Created by Administrátor on 24.5.2016.
 */
public interface QuestionManager {

    public void getAllCategories(Observer<ServerResponse<List<Category>>> observer);
    public void getRandomQuestion(Observer<ServerResponse<Question>> observer, Category category, String login, String password);

    public void createQuestion(Observer<ServerResponse<Question>> observer, String login, String password, Category category, String text);
    public void skipQuestion(Observer<ServerResponse<Question>> observer, Question q, String login, String password);
    public void deleteQuestion(Observer<ServerResponse<Question>> observer, Question question, String login, String password);
    public void getUsersQuestion(Observer<ServerResponse<List<Question>>> observer, String login, String password);
}
