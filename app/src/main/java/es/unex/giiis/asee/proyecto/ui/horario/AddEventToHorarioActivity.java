package es.unex.giiis.asee.proyecto.ui.horario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.DialogFragment;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class AddEventToHorarioActivity extends AppCompatActivity {

    private static final int SEVEN_DAYS = 604800000;

    private static final String TAG = "Add/Modify_Recipe_To_Horario_Activity";

    private static String timeString;
    private static String dateString;
    private static TextView dateView;
    private static TextView timeView;

    private Toolbar mToolbar;
    private Date mDate;
    private RadioButton mDefaultStatusButton;
    private RadioGroup mStatusRadioGroup;

    private CalendarDayItem item;
    private String mode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_to_horario);

        item = new CalendarDayItem(getIntent());
        mode = getIntent().getStringExtra("Mode");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        if(mode.equals("Insert")) {
            getSupportActionBar().setTitle("Add recipe to calendar");
        } else {
            getSupportActionBar().setTitle("Modify recipe on calendar");
        }

        mDefaultStatusButton =  findViewById(R.id.statusNotDone);
        mStatusRadioGroup =  findViewById(R.id.statusGroup);
        dateView =  findViewById(R.id.date);
        timeView =  findViewById(R.id.time);

        if(mode.equals("Insert")) {
            setDefaultDateTime();
        } else {
            setValues();
        }

        // OnClickListener for the Date button, calls showDatePickerDialog() to show
        // the Date dialog
        final Button datePickerButton =  findViewById(R.id.date_picker_button);
        datePickerButton.setOnClickListener(v -> showDatePickerDialog());

        // OnClickListener for the Time button, calls showTimePickerDialog() to show
        // the Time Dialog
        final Button timePickerButton =  findViewById(R.id.time_picker_button);
        timePickerButton.setOnClickListener(v -> showTimePickerDialog());

        // OnClickListener for the Cancel Button,

        final Button cancelButton =  findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(v -> {
            log("Entered cancelButton.OnClickListener.onClick()");

            // - Implement onClick().
            Intent data = new Intent();
            setResult(RESULT_CANCELED, data);
            finish();
        });

        //OnClickListener for the Reset Button

        final Button resetButton =  findViewById(R.id.resetButton);
        resetButton.setOnClickListener(v -> {
            log("Entered resetButton.OnClickListener.onClick()");

            // - Reset data fields to default values
            mStatusRadioGroup.check(mDefaultStatusButton.getId());
            setDefaultDateTime();
        });

        // OnClickListener for the Submit Button
        // Implement onClick().

        final Button submitButton =  findViewById(R.id.submitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // -  Get Status
                CalendarDayItem.Status status = getStatus();
                item.setStatus(status);
                item.setDate(dateString);

                Date time = null;
                try {
                    time = CalendarDayItem.FORMAT.parse(timeString);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                if (mStatusRadioGroup.getCheckedRadioButtonId() == R.id.statusDone) {
                    item.setStatus(CalendarDayItem.Status.DONE);
                } else {
                    item.setStatus(CalendarDayItem.Status.NOTDONE);
                }

                item.setTime(time);

                if(mode.equals("Insert")) {
                    new AsyncInsert().execute(item);
                } else {
                    Intent data = new Intent();
                    CalendarDayItem.packageIntent(data, item.getId(), item.getTitle(), item.getWebid(),
                            item.getStatus(), item.getDate(), item.getTime(), item.getUserid(), item.getType());
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }

    private synchronized static void bindButtons() {

    }

    private void setValues() {
        if (item.getStatus() == CalendarDayItem.Status.DONE) {
            mStatusRadioGroup.check(mStatusRadioGroup.getChildAt(0).getId());
        } else {
            mStatusRadioGroup.check(mStatusRadioGroup.getChildAt(1).getId());
        }

        setStrings(item);

        dateView.setText(dateString);
        timeView.setText(timeString);
    }

    private synchronized static void setStrings(CalendarDayItem item) {
        timeString = CalendarDayItem.FORMAT.format(item.getTime());
        dateString = item.getDate();
    }

    private void setDefaultDateTime() {

        // Default is current time + 7 days
        mDate = new Date();
        mDate = new Date(mDate.getTime() + SEVEN_DAYS);

        Calendar c = Calendar.getInstance();
        c.setTime(mDate);

        setDateString(c.get(Calendar.YEAR), c.get(Calendar.MONTH),
                c.get(Calendar.DAY_OF_MONTH));

        dateView.setText(dateString);

        setTimeString(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
                c.get(Calendar.MILLISECOND));

        timeView.setText(timeString);
    }

    private static void setDateString(int year, int monthOfYear, int dayOfMonth) {

        // Increment monthOfYear for Calendar/Date -> Time Format setting
        monthOfYear++;
        String mon = "" + monthOfYear;
        String day = "" + dayOfMonth;

        if (monthOfYear < 10)
            mon = "0" + monthOfYear;
        if (dayOfMonth < 10)
            day = "0" + dayOfMonth;

        dateString = year + "-" + mon + "-" + day;
    }

    private static void setTimeString(int hourOfDay, int minute, int mili) {
        String hour = "" + hourOfDay;
        String min = "" + minute;

        if (hourOfDay < 10)
            hour = "0" + hourOfDay;
        if (minute < 10)
            min = "0" + minute;

        timeString = hour + ":" + min + ":00";
    }

    private CalendarDayItem.Status getStatus() {

        if (mStatusRadioGroup.getCheckedRadioButtonId() == R.id.statusDone) {
            return CalendarDayItem.Status.DONE;
        }
        return CalendarDayItem.Status.NOTDONE;
    }

    // DialogFragment used to pick a ToDoItem deadline date

    public static class DatePickerFragment extends DialogFragment implements
            DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current date as the default date in the picker

            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme, this, year, month, day);
        }

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            setDateString(year, monthOfYear, dayOfMonth);

            dateView.setText(dateString);
        }

    }

    // DialogFragment used to pick a ToDoItem deadline time

    public static class TimePickerFragment extends DialogFragment implements
            TimePickerDialog.OnTimeSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            // Use the current time as the default values for the picker
            final Calendar c = Calendar.getInstance();
            int hour = c.get(Calendar.HOUR_OF_DAY);
            int minute = c.get(Calendar.MINUTE);

            // Create a new instance of TimePickerDialog and return
            return new TimePickerDialog(getActivity(), R.style.MyDatePickerDialogTheme, this, hour, minute,
                    true);
        }

        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            setTimeString(hourOfDay, minute, 0);

            timeView.setText(timeString);
        }
    }

    private void showDatePickerDialog() {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    private void showTimePickerDialog() {
        DialogFragment newFragment = new TimePickerFragment();
        newFragment.show(getSupportFragmentManager(), "timePicker");
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    class AsyncInsert extends AsyncTask<CalendarDayItem, Void, CalendarDayItem> {

        @Override
        protected CalendarDayItem doInBackground(CalendarDayItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(AddEventToHorarioActivity.this);
            Long id = nutrifitDb.calendarDayItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(CalendarDayItem item){
            super.onPostExecute(item);

            Intent data = new Intent();
            setResult(RESULT_OK, data);
            finish();
        }
    }
}