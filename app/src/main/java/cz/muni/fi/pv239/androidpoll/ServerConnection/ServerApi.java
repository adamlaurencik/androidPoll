package cz.muni.fi.pv239.androidpoll.ServerConnection;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import retrofit2.http.*;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Callback;

/**
 * Created by Adam on 16.05.2016.
 */
public interface ServerApi {

    @GET("/homepage/get-all-categories")
    Call<ServerResponse<List<Category>>> getCategories();

    @POST("/homepage/register")
    public Call<ServerResponse<User>> registerUser(
            @Field("login") String username,
            @Field("password") String password,
            @Field("age") String age,
            @Field("gender") String gender);

    @POST("/homepage/login")
    public Call<ServerResponse<User>> loginUser(
            @Field("login") String username,
            @Field("password") String password);
}
