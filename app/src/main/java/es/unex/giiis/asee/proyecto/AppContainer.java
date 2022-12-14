package es.unex.giiis.asee.proyecto;

import android.content.Context;

import es.unex.giiis.asee.proyecto.repository.NutrifitRepository;
import es.unex.giiis.asee.proyecto.repository.network.ExcercisesNetworkDataSource;
import es.unex.giiis.asee.proyecto.repository.network.RecipesNetworkDataSource;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.DetallesExerciseFragmentViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.DetallesHorarioActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.DetallesPlantillaFragmentViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.DetallesRecetaActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.DetallesRecipeFragmentViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.AddRecipeToDietaActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.DietListViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.EditProfileActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.EditWeightActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.AddEventToHorarioActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.ExcerciseListViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteExerciseActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.FavoriteRecipeActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.HorarioFragmentViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.LoginActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.ModifyPlantillaActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.PerfilFragmentViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.RegisterActivityViewModelFactory;
import es.unex.giiis.asee.proyecto.viewmodels.WeightGraphFragmentViewModelFactory;

public class AppContainer {

    private NutrifitDatabase database;
    private RecipesNetworkDataSource recipesNetworkDataSource;
    private ExcercisesNetworkDataSource excercisesNetworkDataSource;

    public NutrifitRepository repository;

    public LoginActivityViewModelFactory loginFactory;
    public RegisterActivityViewModelFactory registerFactory;
    public PerfilFragmentViewModelFactory perfilFactory;
    public EditProfileActivityViewModelFactory editPerfilFactory;
    public EditWeightActivityViewModelFactory editWeightFactory;
    public WeightGraphFragmentViewModelFactory weightFactory;

    public DetallesRecetaActivityViewModelFactory detallesRecetaActivityFactory;
    public FavoriteRecipeActivityViewModelFactory favoriteRecipeActivityFactory;
    public DetallesRecipeFragmentViewModelFactory detallesRecipeFragmentFactory;
    public RecipeListViewModelFactory recipeFactory;

    public FavoriteExerciseActivityViewModelFactory favoriteExerciseActivityFactory;
    public ExcerciseListViewModelFactory excerciseFactory;
    public DetallesExerciseFragmentViewModelFactory detallesExerciseFactory;

    public DetallesPlantillaFragmentViewModelFactory detallesPlantillaFactory;
    public ModifyPlantillaActivityViewModelFactory modifyPlantillaFactory;
    public AddRecipeToDietaActivityViewModelFactory recipesDietFactory;
    public DietListViewModelFactory dietFactory;

    public HorarioFragmentViewModelFactory horarioFragmentFactory;
    public DetallesHorarioActivityViewModelFactory detallesHorarioFactory;
    public AddEventToHorarioActivityViewModelFactory eventsFactory;

    public AppContainer(Context context){
        database = NutrifitDatabase.getDatabase(context);
        recipesNetworkDataSource = RecipesNetworkDataSource.getInstance();
        excercisesNetworkDataSource = ExcercisesNetworkDataSource.getInstance();
        repository = NutrifitRepository.getInstance(database.userItemDao(), database.weightRecordItemDao(),
                database.plantillaItemDao(), database.recipePlantillaItemDao(), database.calendarDayItemDao(),
                database.favoriteExcerciseItemDao(), database.favoriteRecipeItemDao(),
                recipesNetworkDataSource, excercisesNetworkDataSource);

        loginFactory = new LoginActivityViewModelFactory(repository);
        registerFactory = new RegisterActivityViewModelFactory(repository);
        perfilFactory = new PerfilFragmentViewModelFactory(repository);
        editPerfilFactory = new EditProfileActivityViewModelFactory(repository);
        editWeightFactory = new EditWeightActivityViewModelFactory(repository);
        weightFactory = new WeightGraphFragmentViewModelFactory(repository);

        detallesRecetaActivityFactory = new DetallesRecetaActivityViewModelFactory(repository);
        favoriteRecipeActivityFactory = new FavoriteRecipeActivityViewModelFactory(repository);
        detallesRecipeFragmentFactory = new DetallesRecipeFragmentViewModelFactory(repository);
        recipeFactory = new RecipeListViewModelFactory(repository);

        favoriteExerciseActivityFactory = new FavoriteExerciseActivityViewModelFactory(repository);
        detallesExerciseFactory = new DetallesExerciseFragmentViewModelFactory(repository);
        excerciseFactory = new ExcerciseListViewModelFactory(repository);

        detallesPlantillaFactory = new DetallesPlantillaFragmentViewModelFactory(repository);
        modifyPlantillaFactory = new ModifyPlantillaActivityViewModelFactory(repository);
        recipesDietFactory = new AddRecipeToDietaActivityViewModelFactory(repository);
        dietFactory = new DietListViewModelFactory(repository);

        horarioFragmentFactory = new HorarioFragmentViewModelFactory(repository);
        detallesHorarioFactory = new DetallesHorarioActivityViewModelFactory(repository);
        eventsFactory = new AddEventToHorarioActivityViewModelFactory(repository);
    }
}
