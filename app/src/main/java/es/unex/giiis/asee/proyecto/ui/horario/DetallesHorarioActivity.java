package es.unex.giiis.asee.proyecto.ui.horario;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;
import es.unex.giiis.asee.proyecto.ui.recetas.DetallesRecetaActivity;
import es.unex.giiis.asee.proyecto.ui.recetas.OnSingleRecipeLoaderListener;
import es.unex.giiis.asee.proyecto.ui.recetas.SingleRecipeNetworkLoaderRunnable;
import es.unex.giiis.asee.proyecto.viewmodels.EventViewModel;

public class DetallesHorarioActivity extends AppCompatActivity implements DetallesHorarioAdapter.OnDeleteClickListener, DetallesHorarioAdapter.OnModifyClickListener {

    private static final String TAG = "Detalles_Horario_Activity";

    private String date;
    private TextView dateView;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DetallesHorarioAdapter mAdapter;

    private EventViewModel mEventViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_horario);

        AppContainer appContainer = ((MyApplication) getApplication()).appContainer;

        mEventViewModel = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.eventsFactory).get(EventViewModel.class);

        date = getIntent().getStringExtra("Date");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Calendar day details");

        dateView = findViewById(R.id.date);
        mRecyclerView = findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(DetallesHorarioActivity.this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new DetallesHorarioAdapter(this, this, new DetallesHorarioAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(CalendarDayItem item) {
                if (item.getType().equals("Recipe")) {
                    AppExecutors.getInstance().networkIO().execute(new SingleRecipeNetworkLoaderRunnable(item.getWebid(), new OnSingleRecipeLoaderListener() {
                        @Override
                        public void onRecipeLoader(Recipe data) {
                            Intent intent = new Intent(DetallesHorarioActivity.this, DetallesRecetaActivity.class);

                            Gson gson = new Gson();
                            String myJson = gson.toJson(data);

                            intent.putExtra("Recipe", myJson);
                            startActivity(intent);
                        }
                    }));
                }
            }
        });

        mEventViewModel.getSelectedDateEvents().observe(this, calendarDayItems -> mAdapter.load(calendarDayItems));

        mEventViewModel.getSelectedDate().observe(this, s -> dateView.setText(s));

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mEventViewModel.setSelectedDate(date);
    }

    @Override
    public void onDeleteClick(CalendarDayItem item) {
        showDeleteDialog(item);
    }

    private void showDeleteDialog(CalendarDayItem item) {
        final Dialog dialog = new Dialog(DetallesHorarioActivity.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(true);
        dialog.setContentView(R.layout.delete_plantilla);

        final TextView text = dialog.findViewById(R.id.textView);
        text.setText("Are you sure you want to delete this event from the calendar?");

        final Button cancelButton = dialog.findViewById(R.id.noButton);
        cancelButton.setOnClickListener(v -> dialog.dismiss());

        final Button submitButton = dialog.findViewById(R.id.yesButton);
        submitButton.setOnClickListener(v -> {
            mEventViewModel.delete(item);
            dialog.dismiss();
        });

        dialog.show();
    }

    @Override
    public void onModifyClick(CalendarDayItem item) {
        Intent intent = new Intent(DetallesHorarioActivity.this, AddEventToHorarioActivity.class);
        CalendarDayItem.packageIntent(intent, item.getId(), item.getTitle(), item.getWebid(),
                item.getStatus(), item.getDate(), item.getTime(), item.getUserid(), item.getType());
        intent.putExtra("Mode", "Update");
        startActivity(intent);
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