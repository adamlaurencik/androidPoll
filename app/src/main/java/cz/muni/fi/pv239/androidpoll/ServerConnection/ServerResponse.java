package cz.muni.fi.pv239.androidpoll.ServerConnection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.User;
import okhttp3.ResponseBody;
import retrofit2.Response;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Adam on 16.05.2016.
 */
public class ServerResponse<E>{

    public static final int SUCCESS = 0;
    public static final int INCORRECT_PASS_MESSAGE = 1;
    public static final int INCORRECT_USERNAME_MESSAGE = 2;
    public static final int INVALID_VALUES_PASSED_MESSAGE = 3;
    public static final int UNSUCESSFULL_OPERATION_MESSAGE = 4;
    public static final int USER_ALREADY_EXIST = 5;
    public static final int NO_CATEGORIES_FOUND = 6;
    public static final int NO_QUESTION_FOUND = 7;
    public static final int AUTHORIZATION_ERROR = 8;

    @SerializedName("error")
    @Expose
    private int error;

    @SerializedName("data")
    @Expose
    private E data;

    public boolean isSuccessful() {
        return error == 0;
    }

    public int getError(){
        return error;
    }

}
