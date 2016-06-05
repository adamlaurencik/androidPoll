package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.*;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;

/**
 * Created by Administrátor on 3.6.2016.
 */
public interface OptionManager {

    public void addOption(Observer<ServerResponse<Option>> observer, String text, Question q, String login, String password);
    public void getQuestionOptions(Observer<ServerResponse<List<Option>>> observer, Long questionId);
    public void deleteOption(Observer<ServerResponse<Option>> observer, Question q, String login, String password);
}
