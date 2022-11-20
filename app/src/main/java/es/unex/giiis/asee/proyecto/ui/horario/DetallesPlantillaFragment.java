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

public class DetallesPlantillaFragment extends Fragment {

    private static final int UPDATE_RECETA_ITEM_REQUEST = 0;

    private static final String TAG = "Detalles_Dieta_fragment";

    private PlantillaItem data;
    private TextView dietNameView, priorityView, dayView;

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

}