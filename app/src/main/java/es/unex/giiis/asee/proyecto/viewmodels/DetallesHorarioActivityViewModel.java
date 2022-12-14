package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;

import java.util.List;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

public class DetallesHorarioActivityViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    private MutableLiveData<String> selectedDate;

    public DetallesHorarioActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
        selectedDate = new MutableLiveData<>();
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

    public void delete(CalendarDayItem item){
        mRepository.deleteEvent(item);
    }

    public void fetchOneRecipe(String webid) {
        mRepository.doFetchSingleRecipe(webid);
    }
}
