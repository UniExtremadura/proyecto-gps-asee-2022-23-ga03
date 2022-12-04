package es.unex.giiis.asee.proyecto.ui.perfil;

import java.util.List;

import es.unex.giiis.asee.proyecto.login_register.UserItem;

public interface EditProfileView {
    List<UserItem> getUsers();
    UserItem getCurrentUser();
    void editSuccessful();
    void editFailureUsername();
    void editFailureEmail();
    void editFailurePasswords();
    void fillFields();
    void bindViews();
    void failureEmailFormat();
    void editFailureAge();
    void editFailurePasswordOld();
    void registerFailureHeight();
}
