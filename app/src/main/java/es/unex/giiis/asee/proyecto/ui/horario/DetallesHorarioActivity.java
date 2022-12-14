package es.unex.giiis.asee.proyecto.ui.horario;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
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

public class DetallesHorarioActivity extends AppCompatActivity implements DetallesHorarioAdapter.OnDeleteClickListener, DetallesHorarioAdapter.OnModifyClickListener {

    private static final String TAG = "Detalles_Horario_Activity";
    private static final int MODIFY_EVENT_ITEM_REQUEST = 1;

    private String date;
    private TextView dateView;
    private Toolbar mToolbar;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private DetallesHorarioAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_horario);

        date = getIntent().getStringExtra("Date");

        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportActionBar().setTitle("Calendar day details");

        dateView = findViewById(R.id.date);
        dateView.setText(date);

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

        mRecyclerView.setAdapter(mAdapter);
        new AsyncLoad().execute();
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
            new AsyncDelete().execute(item);
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
        startActivityForResult(intent, MODIFY_EVENT_ITEM_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MODIFY_EVENT_ITEM_REQUEST) {
            if (resultCode == RESULT_OK) {
                CalendarDayItem item = new CalendarDayItem(data);
                new AsyncUpdate().execute(item);
            }
        }
    }

    private void log(String msg) {
        Log.i(TAG, msg);
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<CalendarDayItem>> {

        @Override
        protected List<CalendarDayItem> doInBackground(Void... voids) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(DetallesHorarioActivity.this);
            List<CalendarDayItem> items = nutrifitDb.calendarDayItemDao().getAllFromDate(date);

            return items;
        }

        @Override
        protected void onPostExecute(List<CalendarDayItem> items) {
            super.onPostExecute(items);
            mAdapter.load(items);
        }
    }

    class AsyncUpdate extends AsyncTask<CalendarDayItem, Void, CalendarDayItem> {
        @Override
        protected CalendarDayItem doInBackground(CalendarDayItem... items) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(DetallesHorarioActivity.this);
            int respuesta = nutrifitDb.calendarDayItemDao().update(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(CalendarDayItem item) {
            super.onPostExecute(item);
            new AsyncLoad().execute();
        }
    }

    class AsyncDelete extends AsyncTask<CalendarDayItem, Void, CalendarDayItem> {
        @Override
        protected CalendarDayItem doInBackground(CalendarDayItem... items) {
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(DetallesHorarioActivity.this);
            int respuesta = nutrifitDb.calendarDayItemDao().delete(items[0]);

            return items[0];
        }

        @Override
        protected void onPostExecute(CalendarDayItem item) {
            super.onPostExecute(item);
            mAdapter.delete(item);
        }
    }
}