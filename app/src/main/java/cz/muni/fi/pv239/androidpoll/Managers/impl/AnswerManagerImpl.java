package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.app.VoiceInteractor;
import android.content.Context;

import cz.muni.fi.pv239.androidpoll.Entities.Answer;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.AnswerManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrátor on 3.6.2016.
 */
public class AnswerManagerImpl implements AnswerManager{
    private Context context = null;
    private ServerApi api = null;

    public AnswerManagerImpl(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fcb.php5.sk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.api = retrofit.create(ServerApi.class);
        this.context = context;
    }

    @Override
    public void getNumberOfAnswers(Observer<ServerResponse<Long>> observer, Option option) {
        if(this.api == null){
            //log
            return;
        }
        if(this.context == null){
            //log
            return;
        }

        Observable<ServerResponse<Long>> observable = api.getNumOfAnswers(option.getId());
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void answerQuestion(Observer<ServerResponse<Answer>> observer, String login, Question question, Option option, String password) {
        if(this.api == null){
            //log
            return;
        }
        if(this.context == null){
            //log
            return;
        }

        Observable<ServerResponse<Answer>> observable = api.answer(login, question.getId(), option.getId(), password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
