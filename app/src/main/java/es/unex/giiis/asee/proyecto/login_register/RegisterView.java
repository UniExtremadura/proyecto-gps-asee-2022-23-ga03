package es.unex.giiis.asee.proyecto.login_register;

import java.util.List;

public interface RegisterView {
    List<UserItem> getUsers();
    void registerSuccessful(UserItem user);
    void registerFailureUsername();
    void registerFailureEmail();
    void registerFailurePasswords();
    void fillFields();
    void bindViews();
    void failureEmailFormat();
    void registerFailureAge();
    void registerFailureWeight();
    void registerFailureHeight();
}
