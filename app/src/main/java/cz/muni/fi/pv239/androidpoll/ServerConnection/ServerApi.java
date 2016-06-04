package cz.muni.fi.pv239.androidpoll.ServerConnection;

import java.util.List;


import cz.muni.fi.pv239.androidpoll.Entities.*;
import retrofit2.http.*;
import retrofit2.Call;
import rx.Observable;

/**
 * Created by Adam on 16.05.2016.
 */
public interface ServerApi {

    @GET("/homepage/get-all-categories")
    public Observable<ServerResponse<List<Category>>> getCategories();

    @POST("/homepage/register")
    public Observable<ServerResponse<User>> registerUser(
            @Query("login") String username,
            @Query("password") String password,
            @Query("age") String age,
            @Query("gender") String gender);

    @POST("/homepage/login")
    public Observable<ServerResponse<User>> loginUser(
            @Query("login") String username,
            @Query("password") String password);

    @POST("/homepage/get-random-question")

    public Observable<ServerResponse<Question>> getRandomQuestion(
            @Query("categoryId") Long id,
            @Query("login") String login,
            @Query("password") String password);


    @POST("/homepage/create-question")
    public Observable<ServerResponse<Question>> createQuestion(
            @Query("login") String login,
            @Query("categoryId") Long id,
            @Query("text") String text,
            @Query("password") String password);

    @POST("/homepage/delete-question")
    public Observable<ServerResponse<Question>> deleteQuestion(
            @Query("questionId") Long id,
            @Query("login") String login,
            @Query("password") String password);

    @POST("/homepage/skip-question")
    public Observable<ServerResponse<Question>> skipQuestion(
            @Query("questionId") Long id,
            @Query("login") String login,
            @Query("password") String password);

    @POST("/homepage/user-questions")
    public Observable<ServerResponse<List<Question>>> getUsersQuestions(
            @Query("login") String login,
            @Query("password") String password);

    @POST("/homepage/add-option")
    public Observable<ServerResponse<Option>> addOption(
            @Query("login") String login,
            @Query("questionId") Long id,
            @Query("text") String text,
            @Query("password") String password);

    @POST("/homepage/get-question-options")
    public Observable<ServerResponse<List<Option>>> getQuestionOptions(
            @Query("questionId") Long id);

    @POST("/homepage/get-num-of-answers")
    public Observable<ServerResponse<Long>> getNumOfAnswers(
            @Query("optionId") Long id);

    @POST("/homepage/answer")
    public Observable<ServerResponse<Answer>> answer(
            @Query("login") String login,
            @Query("questionId") Long questionId,
            @Query("optionId") Long optionId,
            @Query("password") String password);
}
