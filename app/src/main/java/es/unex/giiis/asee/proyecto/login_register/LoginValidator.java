package es.unex.giiis.asee.proyecto.login_register;

import android.os.Handler;
import android.util.Log;

public class LoginValidator {

    private LoginView mLoginView;
    private UserItem user;

    LoginValidator(LoginView loginView) {
        this.mLoginView = loginView;
    }

    public void validateLogin(String username, String password) {
        new Handler().postDelayed(() -> {
            if(validateUser(username,password)) {
                mLoginView.loginSuccessfull(user);
            } else {
                mLoginView.loginFailure();
            }
        }, 1500);

    }

    private boolean validateUser(String username, String password){
        boolean flag = false;
        for(UserItem item : mLoginView.getUsers()) {
            Log.d("User",item.toLog());
            if(username.equals(item.getUsername())){
                if(password.equals(item.getPassword())){
                    flag = true;
                    user = item;
                }
                break;
            }
        }

        return flag;
    }
}
