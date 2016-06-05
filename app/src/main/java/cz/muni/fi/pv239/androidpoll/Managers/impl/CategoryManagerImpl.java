package cz.muni.fi.pv239.androidpoll.Managers.impl;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.Managers.interfaces.CategoryManager;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApi;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerApiContainer;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observable;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Adam on 05.06.2016.
 */
public class CategoryManagerImpl implements CategoryManager {

    private ServerApi api = null;

    public CategoryManagerImpl(){
        this.api = ServerApiContainer.getServerApi();
    }
    @Override
    public void getAllCategories(Observer<ServerResponse<List<Category>>> observer) {
        Observable<ServerResponse<List<Category>>> observable = api.getCategories();
        observable.subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);
    }
}
