package es.unex.giiis.asee.proyecto.ui.perfil;

import es.unex.giiis.asee.proyecto.login_register.UserItem;

public class EditProfileValidator {
    private final EditProfileView mEditProfileView;
    private final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    EditProfileValidator(EditProfileView editProfileView) {
        mEditProfileView = editProfileView;
    }

    public void validateEdit(String username, String completename, String email, long age,
                             Double height, String oldPassword, String newPassword, String passwordRepeat) {
        if (username.length() == 0 || oldPassword.length() == 0 || completename.length() == 0) {
            mEditProfileView.fillFields();
        } else if (!email.trim().matches(emailPattern)) {
            mEditProfileView.failureEmailFormat();
        } else if (!newPassword.equals(passwordRepeat)) {
            mEditProfileView.editFailurePasswords();
        } else if (!oldPassword.equals(mEditProfileView.getCurrentUser().getPassword())) {
            mEditProfileView.editFailurePasswordOld();
        } else if (age <= 0 || age > 90) {
            mEditProfileView.editFailureAge();
        } else if (height <= 0.0) {
            mEditProfileView.registerFailureHeight();
        } else if (existsUser(username)) {
            mEditProfileView.editFailureUsername();
        } else if (existEmail(email)) {
            mEditProfileView.editFailureEmail();
        } else {
            mEditProfileView.editSuccessful();
        }
    }

    private boolean existsUser(String username) {
        boolean flag = false;
        for(UserItem user : mEditProfileView.getUsers()) {
            if(username.equals(user.getUsername()) && mEditProfileView.getCurrentUser().getId() != user.getId()) {
                flag =true;
                break;
            }
        }
        return flag;
    }

    private boolean existEmail(String email) {
        boolean flag = false;
        for(UserItem user : mEditProfileView.getUsers()) {
            if(email.equals(user.getEmail()) && mEditProfileView.getCurrentUser().getId() != user.getId()) {
                flag =true;
                break;
            }
        }
        return flag;
    }
}
