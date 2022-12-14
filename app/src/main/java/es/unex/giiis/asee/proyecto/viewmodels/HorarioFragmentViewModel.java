package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

public class HorarioFragmentViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<CalendarDayItem>> userEvents;

    public HorarioFragmentViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userEvents = mRepository.getAllUserEvents();
    }

    public LiveData<List<CalendarDayItem>> getUserEvents() {
        return userEvents;
    }
}
