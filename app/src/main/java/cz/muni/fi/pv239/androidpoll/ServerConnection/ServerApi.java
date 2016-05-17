package cz.muni.fi.pv239.androidpoll.ServerConnection;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import retrofit2.http.GET;
import retrofit2.Call;

/**
 * Created by Adam on 16.05.2016.
 */
public interface ServerApi {

    @GET("/homepage/get-all-categories")
    Call<ServerResponse<List<Category>>> getCategories();

}
