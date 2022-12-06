package es.unex.giiis.asee.proyecto.ui.horario;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaActivity;
import es.unex.giiis.asee.proyecto.repository.network.OnSingleRecipeLoaderListener;
import es.unex.giiis.asee.proyecto.repository.network.SingleRecipeNetworkLoaderRunnable;
import es.unex.giiis.asee.proyecto.viewmodels.DietRecipesViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.RecipeListViewModel;

public class DetallesPlantillaFragment extends Fragment implements DetallesPlantillaAdapter.OnDeleteClickListener{

    private static final int UPDATE_RECETA_ITEM_REQUEST = 0;

    private static final String TAG = "Detalles_Dieta_fragment";

    private PlantillaItem data;
    private TextView dietNameView, priorityView, dayView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DetallesPlantillaAdapter mAdapter;

    private DietRecipesViewModel mDietRecipesViewModel;
    private RecipeListViewModel mRecipeListViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_plantilla, container, false);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mDietRecipesViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), (ViewModelProvider.Factory) appContainer.recipesDietFactory).get(DietRecipesViewModel.class);
        mRecipeListViewModel = new ViewModelProvider((ViewModelStoreOwner) getActivity(), (ViewModelProvider.Factory) appContainer.recipeFactory).get(RecipeListViewModel.class);

        Bundle args = getArguments();
        data = (PlantillaItem) args.getSerializable("Details");

        dietNameView = v.findViewById(R.id.nombreDieta);
        priorityView = v.findViewById(R.id.priorityTextView);
        dayView = v.findViewById(R.id.dayTextView);

        dietNameView.setText(data.getTitle());
        priorityView.setText(String.valueOf(data.getPriority()));
        dayView.setText(String.valueOf(data.getDay()));

        mRecyclerView = v.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DetallesPlantillaAdapter(this, new DetallesPlantillaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecipePlantillaItem item) {
                mRecipeListViewModel.fetchOneRecipe(item.getWebid());
                Intent intent = new Intent(getContext(), DetallesRecetaActivity.class);
                intent.putExtra("webid", item.getWebid());
                startActivity(intent);
            }
        });

        mDietRecipesViewModel.getCurrentDietRecipes().observe(getViewLifecycleOwner(), new Observer<List<RecipePlantillaItem>>() {
            @Override
            public void onChanged(List<RecipePlantillaItem> recipePlantillaItems) {
                mAdapter.load(recipePlantillaItems);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        mDietRecipesViewModel.setCurrentPlantillaId(data.getId());
    }

    private void log(String msg) {
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Log.i(TAG, msg);
    }

    @Override
    public void onDeleteClick(RecipePlantillaItem item) {
        showDeleteDialog(item);
    }

    private void showDeleteDialog(RecipePlantillaItem item) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.delete_plantilla);

        final TextView text = dialog.findViewById(R.id.textView);
        text.setText("Are you sure you want to delete this recipe from the diet?");

        final Button cancelButton = dialog.findViewById(R.id.noButton);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        final Button submitButton = dialog.findViewById(R.id.yesButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDietRecipesViewModel.delete(item);
                dialog.dismiss();
            }
        });

        dialog.show();
    }
}