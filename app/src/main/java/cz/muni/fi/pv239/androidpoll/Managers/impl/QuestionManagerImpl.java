package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.content.Context;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApiContainer;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrátor on 24.5.2016.
 */
public class QuestionManagerImpl implements QuestionManager{

    private ServerApi api = null;

    public QuestionManagerImpl(){
        this.api = ServerApiContainer.getServerApi();
    }

    @Override
    public void getAllCategories(Observer<ServerResponse<List<Category>>> observer) {
        if(this.api == null){
            //log
            return;
        }
        Observable<ServerResponse<List<Category>>> observable = api.getCategories();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getRandomQuestion(Observer<ServerResponse<Question>> observer,Category category, String login, String password) {
        if(api == null){
            //log
            return;
        }
        Observable<ServerResponse<Question>> observable = api.getRandomQuestion(category.getId(), login, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void createQuestion(Observer<ServerResponse<Question>> observer, String login, String password, Category category, String text) {
        if(api == null){
            //log
            return;
        }
        Observable<ServerResponse<Question>> observable = api.createQuestion(login, category.getId(), text, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void deleteQuestion(Observer<ServerResponse<Question>> observer, Question question, String login, String password){
        if(api == null){
            //log
            return;
        }
        Observable<ServerResponse<Question>> observable = api.deleteQuestion(question.getId(), login, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void skipQuestion(Observer<ServerResponse<Question>> observer, Question question, String login, String password){
        if(api == null){
            //log
            return;
        }
        Observable<ServerResponse<Question>> observable = api.skipQuestion(question.getId(), login, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getUsersQuestion(Observer<ServerResponse<List<Question>>> observer, String login, String password) {
        if(api == null){
            //log
            return;
        }
        Observable<ServerResponse<List<Question>>> observable = api.getUsersQuestions(login, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void createQuestionWithOptions(Observer<ServerResponse<Question>> observer, String login, String password, Long categoryId, String text, List<String> options){
        Observable<ServerResponse<Question>> observable = api.createQuestionWithOptions(login, categoryId, text, password, options);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
