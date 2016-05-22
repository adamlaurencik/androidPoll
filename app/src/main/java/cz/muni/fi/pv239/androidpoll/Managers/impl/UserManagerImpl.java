package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.content.Context;
import android.widget.Toast;
import cz.muni.fi.pv239.androidpoll.Entities.Gender;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.UserManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.Response;
import retrofit2.Callback;
/**
 * Created by Administrátor on 21.5.2016.
 */
public class UserManagerImpl implements UserManager{

    private Retrofit retrofit = null;
    private Context context = null;

    public UserManagerImpl(Retrofit retrofit, Context context){
        this.retrofit = retrofit;
        this.context = context;
    }

    @Override
    public void registerNewUser(String username, String password, Integer age, Gender gender) {
        if(this.retrofit == null){
            throw new NullPointerException("retrofit was null");
        }
        if(this.context == null){
            throw new NullPointerException("context was null");
        }

        ServerApi api = retrofit.create(ServerApi.class);
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
        if(this.retrofit == null){
            throw new NullPointerException("retrofit was null");
        }
        if(this.context == null){
            throw new NullPointerException("context was null");
        }

        ServerApi api = retrofit.create(ServerApi.class);
        Call<ServerResponse<User>> call = api.loginUser(username, password);
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
}
