package es.unex.giiis.asee.proyecto.ui.horario;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class HorarioFragment extends Fragment {

    private static final String TAG = "Horario_fragment";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();
    private SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_horario, container, false);

        mCalendarView = v.findViewById(R.id.calendarView);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        mCalendarView.setOnDayClickListener(this::previewNote);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mEventDays = new ArrayList<>();
        mCalendarView.setEvents(mEventDays);
        new AsyncLoad().execute();
    }

    private void loadEventItems(List<CalendarDayItem> data) {
        for(CalendarDayItem item : data) {
            Calendar cal = Calendar.getInstance();
            try {
                cal.setTime(sdf.parse(item.getDate()));
            } catch (ParseException e) {
                e.printStackTrace();
            }

            EventDay date = new EventDay(cal,R.drawable.today_blue);
            mEventDays.add(date);
        }

        mCalendarView.setEvents(mEventDays);
    }

    private void previewNote(EventDay eventDay) {
        Intent intent = new Intent(getContext(), DetallesHorarioActivity.class);
        intent.putExtra("Date", sdf.format(eventDay.getCalendar().getTime()));
        startActivity(intent);
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<CalendarDayItem>> {

        @Override
        protected List<CalendarDayItem> doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            List<CalendarDayItem> items = nutrifitDb.calendarDayItemDao().getAllFromUser(sp.getLong("id", 0));

            return items;
        }

        @Override
        protected void onPostExecute(List<CalendarDayItem> items){
            super.onPostExecute(items);
            loadEventItems(items);
        }
    }
}