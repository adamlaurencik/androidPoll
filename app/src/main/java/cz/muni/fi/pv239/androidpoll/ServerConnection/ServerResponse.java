package cz.muni.fi.pv239.androidpoll.ServerConnection;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Adam on 16.05.2016.
 */
public class ServerResponse<E> {

    @SerializedName("success")
    @Expose
    private int success;
    @SerializedName("categories")
    @Expose
    private  <E>data = new <E>();

    public boolean isSuccessful() {
        return success!=0;
    }


}
