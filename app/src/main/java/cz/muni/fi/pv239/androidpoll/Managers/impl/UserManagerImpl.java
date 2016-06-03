package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import java.util.concurrent.CountDownLatch;

import cz.muni.fi.pv239.androidpoll.Entities.Gender;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.UserManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Callback;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrátor on 21.5.2016.
 */
public class UserManagerImpl implements UserManager{

    private ServerApi api = null;
    private Context context = null;

    public UserManagerImpl(Context context){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://fcb.php5.sk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.api = retrofit.create(ServerApi.class);
        this.context = context;
    }

    @Override
    public void registerNewUser(String username, String password, Integer age, Gender gender) {
        if(this.api == null){
            throw new NullPointerException("retrofit was null");
        }
        if(this.context == null){
            throw new NullPointerException("context was null");
        }

        Call<ServerResponse<User>> call = api.registerUser(username, password, null, null);
        call.enqueue(new Callback<ServerResponse<User>>() {
            @Override
            public void onResponse(Call<ServerResponse<User>> call, Response<ServerResponse<User>> response) {
                if (response.body().isSuccessful()) {
                    Toast.makeText(context, "User was successfully registered", Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<User>> call, Throwable t) {
                //logging
            }
        });

    }

    @Override
    public void loginUser(String username, String password) {
        if(this.api == null){
            throw new NullPointerException("retrofit was null");
        }
        if(this.context == null){
            throw new NullPointerException("context was null");
        }

        try {
            Call<ServerResponse<User>> call = api.loginUser(username, password);
            call.enqueue(new Callback<ServerResponse<User>>() {
                @Override
                public void onResponse(Call<ServerResponse<User>> call, Response<ServerResponse<User>> response) {
                    if (response.body().isSuccessful()) {
                        Toast.makeText(context, "User was successfully logged in! :)", Toast.LENGTH_LONG).show();

                    } else {
                        Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                    }
                    //Intent intent = new Intent(context, TestActivity.class);
                    //context.startActivity(intent);
                }

                @Override
                public void onFailure(Call<ServerResponse<User>> call, Throwable t) {
                    Toast.makeText(context, "Error", Toast.LENGTH_LONG).show();
                }
            });
        }catch (Exception ex){
            Toast.makeText(context,"",Toast.LENGTH_LONG).show();
        }
    }
}
