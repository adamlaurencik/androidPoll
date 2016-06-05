package cz.muni.fi.pv239.androidpoll.ServerConnection;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Adam on 05.06.2016.
 */
public class ServerApiContainer {
    private static ServerApi serverApi = null;

    public static ServerApi getServerApi(){
        if(serverApi==null){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://fcb.php5.sk/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            serverApi=retrofit.create(ServerApi.class);
        }
        return serverApi;
    }
}
