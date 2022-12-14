package es.unex.giiis.asee.proyecto.ui.horario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.applandeo.materialcalendarview.CalendarView;
import com.applandeo.materialcalendarview.EventDay;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.viewmodels.HorarioFragmentViewModel;

public class HorarioFragment extends Fragment {

    private static final String TAG = "Horario_fragment";
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

    private CalendarView mCalendarView;
    private List<EventDay> mEventDays = new ArrayList<>();

    private HorarioFragmentViewModel mHorarioFragmentViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_horario, container, false);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mHorarioFragmentViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), (ViewModelProvider.Factory) appContainer.horarioFragmentFactory).get(HorarioFragmentViewModel.class);

        mCalendarView = v.findViewById(R.id.calendarView);

        mCalendarView.setOnDayClickListener(this::previewNote);

        mEventDays = new ArrayList<>();
        mCalendarView.setEvents(mEventDays);

        mHorarioFragmentViewModel.getUserEvents().observe(getViewLifecycleOwner(), new Observer<List<CalendarDayItem>>() {
            @Override
            public void onChanged(List<CalendarDayItem> calendarDayItems) {
                loadEventItems(calendarDayItems);
            }
        });

        return v;
    }

    private void loadEventItems(List<CalendarDayItem> data) {
        mCalendarView.clearSelectedDays();
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
}