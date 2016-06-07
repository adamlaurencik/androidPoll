package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import android.app.VoiceInteractor;

import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;
import cz.muni.fi.pv239.androidpoll.Entities.*;

/**
 * Created by Administrátor on 3.6.2016.
 */
public interface AnswerManager {

    public void getNumberOfAnswers(Observer<ServerResponse<Long>> observer, Option option);
    public void answerQuestion(Observer<ServerResponse<Answer>> observer, String login,
                               Long questionId, Option option, String password);
}
