package es.unex.giiis.asee.proyecto;

import static android.content.Context.MODE_PRIVATE;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.closeSoftKeyboard;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.action.ViewActions.scrollTo;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;

import android.content.SharedPreferences;

import androidx.test.core.app.ApplicationProvider;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.filters.LargeTest;
import androidx.test.rule.ActivityTestRule;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import es.unex.giiis.asee.proyecto.login_register.LoginActivity;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
@RunWith (AndroidJUnit4.class)
@LargeTest
public class RegisterUserUITest {
    private SharedPreferences sp;
    private String userName;
    @Rule
    public ActivityTestRule<LoginActivity> mActivityRule =
            new ActivityTestRule<>(LoginActivity.class);

    @Before
    public void init() {
        sp = ApplicationProvider.getApplicationContext().getSharedPreferences("UserPref", MODE_PRIVATE);
    }

    @After
    public void reset() {
        SharedPreferences.Editor editor = sp.edit();
        editor.remove("id");
        editor.remove("username");
        editor.remove("password");
        editor.apply();

        AppExecutors.getInstance().diskIO().execute(() -> NutrifitDatabase.getDatabase(ApplicationProvider.getApplicationContext()).userItemDao().delete(userName));
    }
    @Test
    public void shouldRegister() {
        userName = "UsuarioPrueba";
        String completeName = "Usuario de prueba";
        String email = "UsuarioPrueba@gmail.com";
        String password = "123";
        String age = "21";
        String weight = "74.0";
        String height = "180.0";

        onView(withId(R.id.sign_up_button_login)).perform(click());
        onView(withId(R.id.username_register_editTex)).perform(replaceText(userName),closeSoftKeyboard());
        onView(withId(R.id.complete_name_editText_register)).perform(replaceText(completeName),closeSoftKeyboard());
        onView(withId(R.id.age_editText_register)).perform(replaceText(age),closeSoftKeyboard());
        onView(withId(R.id.email_editText_register)).perform(replaceText(email),closeSoftKeyboard());
        onView(withId(R.id.weight_editTex)).perform(replaceText(weight),closeSoftKeyboard());
        onView(withId(R.id.height_editTex)).perform(replaceText(height),closeSoftKeyboard());
        onView(withId(R.id.password_editText_register)).perform(replaceText(password),closeSoftKeyboard());
        onView(withId(R.id.repeat_password_editText_register)).perform(replaceText(password),closeSoftKeyboard());
        onView(withId(R.id.sign_up)).perform(scrollTo(), click());
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        onView(withId(R.id.buttonLogin)).perform(click());

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        onView(withId(R.id.navigation_perfil)).perform(click());

        onView(withId(R.id.name_view)).check(matches(withText(userName)));
        onView(withId(R.id.complete_name_view)).check(matches(withText(completeName)));
        onView(withId(R.id.emailView)).check(matches(withText(email)));
        onView(withId(R.id.age_view)).check(matches(withText(age)));
        onView(withId(R.id.weight_view)).check(matches(withText(weight)));
        onView(withId(R.id.height_view)).check(matches(withText(height)));
    }

}
