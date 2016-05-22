package cz.muni.fi.pv239.androidpoll.Managers.interfaces;

import cz.muni.fi.pv239.androidpoll.Entities.Gender;

/**
 * Created by Administrátor on 21.5.2016.
 */
public interface UserManager {

    public void registerNewUser(String nick, String password, Integer age, Gender gender);

    public void loginUser(String nick, String password);
}
