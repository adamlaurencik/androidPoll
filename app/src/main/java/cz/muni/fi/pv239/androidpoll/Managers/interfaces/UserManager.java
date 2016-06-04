package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import cz.muni.fi.pv239.androidpoll.Entities.Gender;
import cz.muni.fi.pv239.androidpoll.Entities.User;
import cz.muni.fi.pv239.androidpoll.ServerConnection.ServerResponse;
import rx.Observer;

/**
 * Created by Administrátor on 21.5.2016.
 */
public interface UserManager {

    public void registerNewUser(Observer<ServerResponse<User>> observer, String nick, String password, Integer age, Gender gender);

    public void loginUser(Observer<ServerResponse<User>> observer, String nick, String password);
}
