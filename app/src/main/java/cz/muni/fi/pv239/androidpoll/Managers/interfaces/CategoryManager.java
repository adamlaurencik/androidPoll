package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import java.util.List;

import cz.muni.fi.pv239.androidpoll.Entities.Category;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;

/**
 * Created by Adam on 05.06.2016.
 */
public interface CategoryManager {

    public void getAllCategories(Observer<ServerResponse<List<Category>>> observer);
}
