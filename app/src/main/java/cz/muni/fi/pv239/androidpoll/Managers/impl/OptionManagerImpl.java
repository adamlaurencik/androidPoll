package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.content.Context;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.OptionManager;
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
 * Created by Administrátor on 3.6.2016.
 */
public class OptionManagerImpl implements OptionManager {
    private Context context = null;
    private ServerApi api = null;

    public OptionManagerImpl(){
        this.api = ServerApiContainer.getServerApi();
    }

    @Override
    public void addOption(Observer<ServerResponse<Option>> observer, String text, Question q, String login, String password) {
        if(this.api == null){
            //log
            return;
        }
        if(text == null ||text.equals("") || text.isEmpty()){
            //log
            return;
        }
        Observable<ServerResponse<Option>> observable = api.addOption(login, q.getId(), text, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void getQuestionOptions(Observer<ServerResponse<List<Option>>> observer, Long questionId) {
        if(this.api == null){
            //log
            return;
        }
        Observable<ServerResponse<List<Option>>> observable = api.getQuestionOptions(questionId);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

    @Override
    public void deleteOption(Observer<ServerResponse<Option>> observer, Question q, String login, String password) {

    }
}
