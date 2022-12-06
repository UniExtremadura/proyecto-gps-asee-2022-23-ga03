package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

public class EventViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private LiveData<List<CalendarDayItem>> userEvents;
    private MutableLiveData<String> selectedDate;

    public EventViewModel(NutrifitRepository repository) {
        mRepository = repository;
        userEvents = mRepository.getAllUserEvents();
        selectedDate = new MutableLiveData<>();
    }

    public LiveData<List<CalendarDayItem>> getUserEvents() {
        return userEvents;
    }

    public LiveData<List<CalendarDayItem>> getSelectedDateEvents() {
        return Transformations.switchMap(getSelectedDate(), mRepository::getAllEventsFromDate);
    }

    public LiveData<String> getSelectedDate() {
        return selectedDate;
    }

    public void setSelectedDate(String date) {
        selectedDate.setValue(date);
    }

    public void insert(CalendarDayItem item) {
        mRepository.insertEvent(item);
    }

    public void update(CalendarDayItem item) {
        mRepository.updateEvent(item);
    }

    public void delete(CalendarDayItem item){
        mRepository.deleteEvent(item);
    }
}
