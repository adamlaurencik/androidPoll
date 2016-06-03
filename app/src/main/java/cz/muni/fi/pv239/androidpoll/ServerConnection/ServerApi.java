package cz.muni.fi.pv239.androidpoll.ServerConnection;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.*;
import retrofit2.http.*;
import retrofit2.Call;

/**
 * Created by Adam on 16.05.2016.
 */
public interface ServerApi {

    @GET("/homepage/get-all-categories")
    public Call<ServerResponse<List<Category>>> getCategories();

    @POST("/homepage/register")
    public Call<ServerResponse<User>> registerUser(
            @Query("login") String username,
            @Query("password") String password,
            @Query("age") String age,
            @Query("gender") String gender);

    @POST("/homepage/login")
    public Call<ServerResponse<User>> loginUser(
            @Query("login") String username,
            @Query("password") String password);

    @POST("/homepage/get-random-question")

    public Call<ServerResponse<Question>> getRandomQuestion(
            @Query("categoryId") Long id,
            @Query("login") String login,
            @Query("password") String password);


    @POST("/homepage/create-question")
    public Call<ServerResponse<Question>> createQuestion(
            @Query("login") String login,
            @Query("categoryId") Long id,
            @Query("text") String text,
            @Query("password") String password);
}
