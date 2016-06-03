package cz.muni.fi.pv239.androidpoll.Managers.impl;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Entities.Option;
import cz.muni.fi.pv239.androidpoll.Entities.Question;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.QuestionManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Administrátor on 24.5.2016.
 */
public class QuestionManagerImpl implements QuestionManager{

    private Retrofit retrofit = null;
    private Context context = null;

    public QuestionManagerImpl(Context context){
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://fcb.php5.sk/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.context = context;
    }

    @Override
    public List<Category> getAllCategories() {
        if(this.retrofit == null){
            //log
            return null;
        }
        if(this.context == null){
            //log
            return null;
        }

        ServerApi api = this.retrofit.create(ServerApi.class);
        Call<ServerResponse<List<Category>>> call = api.getCategories();
        call.enqueue(new Callback<ServerResponse<List<Category>>>() {
            @Override
            public void onResponse(Call<ServerResponse<List<Category>>> call, Response<ServerResponse<List<Category>>> response) {
                if(response.body().isSuccessful()){
                    List<Category> categories = null;
                    if(response.body().isSuccessful()){
                        categories = response.body().getData();
                    }else{
                        //log
                    }
                }else{
                    //log
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<List<Category>>> call, Throwable t) {
                //log
            }
        });

        return null;
    }

    @Override
    public Question getRandomQuestion(Category category, String login, String password) {
        if(retrofit == null){
            //log
            return null;
        }
        if(context == null){
            //log
            return null;
        }

        ServerApi api = this.retrofit.create(ServerApi.class);
        Call<ServerResponse<Question>> call = api.getRandomQuestion(category.getId(), login, password);
        //Call<ServerResponse<Question>> call = api.getRandomQuestion(new Long(0), login, password);
        call.enqueue(new Callback<ServerResponse<Question>>() {

            @Override
            public void onResponse(Call<ServerResponse<Question>> call, Response<ServerResponse<Question>> response) {
                Question question = null;
                if(response.body().isSuccessful()){
                    question = response.body().getData();
                }else{
                    //log
                }
                if(question != null){
                    Toast.makeText(context, "pohodkis", Toast.LENGTH_LONG).show();
                }else{
                    Toast.makeText(context, "not pohodkis"+response.body().getError(), Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ServerResponse<Question>> call, Throwable t) {
                //log
            }
        });

        return null;
    }

    @Override
    public List<Option> getQuestionOptions(Question q) {
        return null;
    }

    @Override
    public void createQuestion(String login, String password, Category category, String text) {
        if(retrofit == null){
            //log
            return;
        }
        if(context == null){
            //log
            return;
        }
        ServerApi api = this.retrofit.create(ServerApi.class);
        Call<ServerResponse<Question>> call = api.createQuestion(login, new Long(1).longValue(), text, password);
        //Call<ServerResponse<Question>> call = api.createQuestion(login, category.getId(), text, password);
        call.enqueue(new Callback<ServerResponse<Question>>() {
            @Override
            public void onResponse(Call<ServerResponse<Question>> call, Response<ServerResponse<Question>> response) {
                if(response.body().isSuccessful()){
                    //jebem to
                }else {
                    //log
                }
            }

            @Override
            public void onFailure(Call<ServerResponse<Question>> call, Throwable t) {
                //log
            }
        });

    }

    @Override
    public void addOption(String login, String password, String text, Question q) {

    }

    @Override
    public void deleteOption(Question q, String login, String password) {

    }

    @Override
    public void skipQuestion(Question q, String login, String password) {

    }

    @Override
    public int getNumberOfAnswers(Question q) {
        return 0;
    }

}
