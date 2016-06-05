package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

import cz.muni.fi.pv239.androidpoll.Entities.Gender;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.UserManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApiContainer;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Observer;
import rx.Scheduler;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Administrátor on 21.5.2016.
 */
public class UserManagerImpl implements UserManager{

    private ServerApi api = null;

    public UserManagerImpl(){
        this.api = ServerApiContainer.getServerApi();
    }

    @Override
    public void registerNewUser(Observer<ServerResponse<User>> observer, String username, String password, Integer age, Gender gender) {
        if(this.api == null){
            throw new NullPointerException("retrofit was null");
        }
        Observable<ServerResponse<User>> observable = api.registerUser(username, password, null, null);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    @Override
    public void loginUser(Observer<ServerResponse<User>> observer, String username, String password) {
        if(this.api == null){
            throw new NullPointerException("retrofit was null");
        }
        Observable<ServerResponse<User>> observable = api.loginUser(username, password);
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }

}
