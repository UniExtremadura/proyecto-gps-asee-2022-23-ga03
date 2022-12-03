package es.unex.giiis.asee.proyecto;

import android.content.Context;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.WeightViewModelFactory;

public class AppContainer {

    private NutrifitDatabase database;
    public NutrifitRepository repository;
    public UserViewModelFactory userFactory;
    public WeightViewModelFactory weightFactory;

    public AppContainer(Context context){
        database = NutrifitDatabase.getDatabase(context);
        repository = NutrifitRepository.getInstance(database.userItemDao(), database.weightRecordItemDao());
        userFactory = new UserViewModelFactory(repository);
        weightFactory = new WeightViewModelFactory(repository);
    }
}
