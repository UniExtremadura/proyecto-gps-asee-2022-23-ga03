package es.unex.giiis.asee.proyecto.login_register;

import java.util.List;

public interface LoginView {
    List <UserItem> getUsers ();
    void loginSuccessfull (UserItem user);
    void loginFailure ();
}
