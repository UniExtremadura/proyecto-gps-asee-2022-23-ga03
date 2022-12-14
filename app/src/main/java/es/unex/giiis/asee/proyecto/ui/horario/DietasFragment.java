package es.unex.giiis.asee.proyecto.ui.horario;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;


public class DietasFragment extends Fragment implements PlantillaAdapter.OnDeleteClickListener, PlantillaAdapter.OnModifyClickListener {

    private static final int ADD_PLANTILLA_ITEM_REQUEST = 0;

    private static final String TAG = "Dietas_fragment";


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlantillaAdapter mAdapter;
    private SharedPreferences sp;
    private static final int MODIFY_PLANTILLA_ITEM_REQUEST = 1;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dietas, container, false);

        mRecyclerView = v.findViewById(R.id.recyclerView);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        mLayoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new PlantillaAdapter(this, new PlantillaAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(PlantillaItem item) {
                Intent intent = new Intent(getContext(), DetallesPlantillaSwipeActivity.class);
                List<PlantillaItem> data = mAdapter.getList();
                intent.putExtra("data", (Serializable) data);
                PlantillaItem.packageIntent(intent, item.getId(), item.getTitle(),
                        item.getPriority(), item.getDay(), item.getUserid());
                startActivity(intent);

            }
        }, this);

        FloatingActionButton mButton = v.findViewById(R.id.fab);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), AddPlantillaActivity.class);
                startActivityForResult(intent, ADD_PLANTILLA_ITEM_REQUEST);
            }
        });

        mRecyclerView.setAdapter(mAdapter);

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        // Load saved ToDoItems, if necessary

        if (mAdapter.getItemCount() == 0)
            loadItems();
    }

    private void loadItems() {

        //Se cargan los items almacenados en la base de datos
        new AsyncLoad().execute();
    }

    private void log(String msg) {
        Log.i(TAG, msg);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("Entered onActivityResult()");

        if (requestCode == ADD_PLANTILLA_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                PlantillaItem item = new PlantillaItem(data);

                //Se inserta el item recibido en la base de datos
                new AsyncInsert().execute(item);
            }
        }
        else if (requestCode == MODIFY_PLANTILLA_ITEM_REQUEST) {
            if (resultCode == RESULT_OK){
                PlantillaItem item = new PlantillaItem(data);

                //Se actualiza el item recibido en la base de datos
                new AsyncUpdate().execute(item);
            }
        }
    }

    @Override
    public void onModifyClick(PlantillaItem item) {
        Intent intent = new Intent(getContext(), ModifyPlantillaActivity.class);
        PlantillaItem.packageIntent(intent, item.getId(), item.getTitle(), item.getPriority(), item.getDay(), sp.getLong("id", 0));
        startActivityForResult(intent,MODIFY_PLANTILLA_ITEM_REQUEST);
    }

    @Override
    public void onDeleteClick(PlantillaItem item) {
        showDeleteDialog(item);
    }

    private void showDeleteDialog(PlantillaItem item) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.delete_plantilla);

        final Button cancelButton = dialog.findViewById(R.id.noButton);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        final Button submitButton = dialog.findViewById(R.id.yesButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AsyncDelete().execute(item);
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<PlantillaItem>> {

        @Override
        protected List<PlantillaItem> doInBackground(Void... voids) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            List<PlantillaItem> items = nutrifitDb.plantillaItemDao().getAll(sp.getLong("id", 0));

            return items;
        }

        @Override
        protected void onPostExecute(List<PlantillaItem> items) {
            super.onPostExecute(items);
            mAdapter.load(items);
        }
    }

    class AsyncInsert extends AsyncTask<PlantillaItem, Void, PlantillaItem> {

        @Override
        protected PlantillaItem doInBackground(PlantillaItem... items) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            Long id = nutrifitDb.plantillaItemDao().insert(items[0]);

            items[0].setId(id);

            return items[0];
        }

        @Override
        protected void onPostExecute(PlantillaItem item) {
            super.onPostExecute(item);
            mAdapter.add(item);
        }
    }


    class AsyncDelete extends AsyncTask<PlantillaItem, Void, PlantillaItem> {
        @Override
        protected PlantillaItem doInBackground(PlantillaItem... items) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            int respuesta = nutrifitDb.plantillaItemDao().delete(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(PlantillaItem item) {
            super.onPostExecute(item);
            mAdapter.delete(item);
        }
    }

    class AsyncUpdate extends AsyncTask<PlantillaItem, Void, PlantillaItem>{
        @Override
        protected PlantillaItem doInBackground(PlantillaItem... items){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            int respuesta = nutrifitDb.plantillaItemDao().update(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(PlantillaItem item){
            super.onPostExecute(item);
            mAdapter.update(item);
        }
    }
}