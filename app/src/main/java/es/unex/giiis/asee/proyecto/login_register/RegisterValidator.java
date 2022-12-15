package es.unex.giiis.asee.proyecto.login_register;

import android.os.Handler;

/**
 * Valida el registro de usuario
 */
public class RegisterValidator {
    private final RegisterView mRegisterView;
    private static final String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    RegisterValidator(RegisterView registerView) {
        mRegisterView = registerView;
    }

    public void validateRegister(String username, String completename, String email, int age, Double weight, Double height, String password, String passwordRepeat) {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (username.length() == 0 || password.length() == 0 || passwordRepeat.length() == 0 || completename.length() == 0) {
                    mRegisterView.fillFields();
                } else if (!email.trim().matches(emailPattern)) {
                    mRegisterView.failureEmailFormat();
                } else if (!password.equals(passwordRepeat)){
                    mRegisterView.registerFailurePasswords();
                } else if (age <= 0 || age > 90){
                    mRegisterView.registerFailureAge();
                } else if (weight <= 20.0){
                    mRegisterView.registerFailureWeight();
                } else if (height <= 0.0){
                    mRegisterView.registerFailureHeight();
                } else if (existsUser(username)){
                    mRegisterView.registerFailureUsername();
                } else if (existEmail(email)){
                    mRegisterView.registerFailureEmail();
                } else {
                    mRegisterView.registerSuccessful(new UserItem(username, completename, email, age, weight, height, password));
                }
            }
        }, 2000);
    }

    private boolean existsUser(String username) {
        boolean flag = false;
        for(UserItem user : mRegisterView.getUsers()) {
            if(username.equals(user.getUsername())) {
                flag =true;
                break;
            }
        }
        return flag;
    }

    private boolean existEmail(String email) {
        boolean flag = false;
        for(UserItem user : mRegisterView.getUsers()) {
            if(email.equals(user.getEmail())) {
                flag =true;
                break;
            }
        }
        return flag;
    }
}
