package es.unex.giiis.asee.proyecto.ui.horario;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.Serializable;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;
import es.unex.giiis.asee.proyecto.viewmodels.DietViewModel;
import es.unex.giiis.asee.proyecto.viewmodels.UserViewModel;


public class DietasFragment extends Fragment implements PlantillaAdapter.OnDeleteClickListener, PlantillaAdapter.OnModifyClickListener {

    private static final int ADD_PLANTILLA_ITEM_REQUEST = 0;

    private static final String TAG = "Dietas_fragment";


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private PlantillaAdapter mAdapter;

    private DietViewModel mDietViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_dietas, container, false);

        mRecyclerView = v.findViewById(R.id.recyclerView);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mDietViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.dietFactory).get(DietViewModel.class);

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

        mDietViewModel.getUserDiets().observe(getViewLifecycleOwner(), new Observer<List<PlantillaItem>>() {
            @Override
            public void onChanged(List<PlantillaItem> plantillaItems) {
                mAdapter.load(plantillaItems);
            }
        });

        return v;
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        log("Entered onActivityResult()");

        if (requestCode == ADD_PLANTILLA_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                PlantillaItem item = new PlantillaItem(data);

                //Se inserta el item recibido en la base de datos
                mDietViewModel.insert(item);
            }
        }
    }

    @Override
    public void onModifyClick(PlantillaItem item) {
        Intent intent = new Intent(getContext(), ModifyPlantillaActivity.class);
        PlantillaItem.packageIntent(intent, item.getId(), item.getTitle(), item.getPriority(),
                item.getDay(), item.getUserid());
        startActivity(intent);
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
        submitButton.setOnClickListener(v -> {
            mDietViewModel.delete(item);
            dialog.dismiss();
        });
        dialog.show();
    }
}