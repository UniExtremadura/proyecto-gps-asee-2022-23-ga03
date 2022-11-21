package es.unex.giiis.asee.proyecto.ui.horario;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaActivity;
import es.unex.giiis.asee.proyecto.ui.recetas.OnSingleRecipeLoaderListener;
import es.unex.giiis.asee.proyecto.ui.recetas.SingleRecipeNetworkLoaderRunnable;

public class DetallesPlantillaFragment extends Fragment {

    private static final int UPDATE_RECETA_ITEM_REQUEST = 0;

    private static final String TAG = "Detalles_Dieta_fragment";

    private PlantillaItem data;
    private TextView dietNameView, priorityView, dayView;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DetallesPlantillaAdapter mAdapter;

    private long previousId = -1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_detalles_plantilla, container, false);

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

        mAdapter = new DetallesPlantillaAdapter(new DetallesPlantillaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(RecipePlantillaItem item) {
                AppExecutors.getInstance().networkIO().execute(new SingleRecipeNetworkLoaderRunnable(item.getWebid(), new OnSingleRecipeLoaderListener() {
                    @Override
                    public void onRecipeLoader(Recipe data) {
                        Intent intent = new Intent(getContext(), DetallesRecetaActivity.class);

                        Gson gson = new Gson();
                        String myJson = gson.toJson(data);

                        intent.putExtra("Recipe", myJson);
                        startActivity(intent);
                    }
                }));
            }
        });

        mRecyclerView.setAdapter(mAdapter);
        loadItems();


        return v;
    }

    private void loadItems() {
        //Se cargan los items almacenados en la base de datos
        new AsyncLoad().execute();
    }


    class AsyncLoad extends AsyncTask<Void, Void, List<RecipePlantillaItem>> {

        @Override
        protected List<RecipePlantillaItem> doInBackground(Void... voids) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            List<RecipePlantillaItem> items = nutrifitDb.recipePlantillaItemDao().getAllFromPlantilla(data.getId());

            return items;
        }

        @Override
        protected void onPostExecute(List<RecipePlantillaItem> items) {
            super.onPostExecute(items);
            log(items.toString());
            mAdapter.load(items);
        }
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