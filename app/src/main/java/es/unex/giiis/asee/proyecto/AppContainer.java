package es.unex.giiis.asee.proyecto;

import android.content.Context;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.repository.network.ExcercisesNetworkDataSource;
import es.unex.giiis.asee.proyecto.repository.network.RecipesNetworkDataSource;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.DietRecipesViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.DietViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.EventViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.ExcerciseListViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.WeightViewModelFactory;

public class AppContainer {

    private NutrifitDatabase database;
    private RecipesNetworkDataSource recipesNetworkDataSource;
    private ExcercisesNetworkDataSource excercisesNetworkDataSource;

    public NutrifitRepository repository;
    public UserViewModelFactory userFactory;
    public WeightViewModelFactory weightFactory;
    public DietViewModelFactory dietFactory;
    public RecipeListViewModelFactory recipeFactory;
    public ExcerciseListViewModelFactory excerciseFactory;
    public DietRecipesViewModelFactory recipesDietFactory;
    public EventViewModelFactory eventsFactory;

    public AppContainer(Context context){
        database = NutrifitDatabase.getDatabase(context);
        recipesNetworkDataSource = RecipesNetworkDataSource.getInstance();
        excercisesNetworkDataSource = ExcercisesNetworkDataSource.getInstance();
        repository = NutrifitRepository.getInstance(database.userItemDao(), database.weightRecordItemDao(),
                database.plantillaItemDao(), database.recipePlantillaItemDao(),
                database.calendarDayItemDao(),
                recipesNetworkDataSource, excercisesNetworkDataSource);

        userFactory = new UserViewModelFactory(repository);
        weightFactory = new WeightViewModelFactory(repository);
        dietFactory = new DietViewModelFactory(repository);
        recipeFactory = new RecipeListViewModelFactory(repository);
        excerciseFactory = new ExcerciseListViewModelFactory(repository);
        recipesDietFactory = new DietRecipesViewModelFactory(repository);
        eventsFactory = new EventViewModelFactory(repository);
    }
}
