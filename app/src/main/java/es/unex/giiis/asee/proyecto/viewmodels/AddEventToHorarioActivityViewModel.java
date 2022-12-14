package es.unex.giiis.asee.proyecto.viewmodels;

import androidx.lifecycle.ViewModel;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.ui.horario.CalendarDayItem;

public class AddEventToHorarioActivityViewModel extends ViewModel {
    private final NutrifitRepository mRepository;

    public AddEventToHorarioActivityViewModel(NutrifitRepository repository) {
        mRepository = repository;
    }

    public void insert(CalendarDayItem item) {
        mRepository.insertEvent(item);
    }

    public void update(CalendarDayItem item) {
        mRepository.updateEvent(item);
    }
}
