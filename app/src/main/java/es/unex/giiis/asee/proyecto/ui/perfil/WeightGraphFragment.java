package es.unex.giiis.asee.proyecto.ui.perfil;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.BarGraphSeries;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.login_register.UserItem;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.roomdb.NutrifitDatabase;

public class WeightGraphFragment extends Fragment {

    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;
    private SharedPreferences sp;
    private List<WeightRecordItem> records;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight_graph, container, false);

        sp = getActivity().getSharedPreferences("UserPref", Context.MODE_PRIVATE);

        graphView = v.findViewById(R.id.graph);

        new AsyncLoad().execute();

        return v;
    }

    private void generateGraph() {
        series = new LineGraphSeries<>(getDataPoint());
        graphView.addSeries(series);

        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(20);
        graphView.getViewport().setMaxY(200);

    }

    private DataPoint[] getDataPoint() {
        int size = records.size();
        DataPoint[] values = new DataPoint[size];

        for (int i=0; i<size; i++) {
            Date xi = records.get(i).getDate();
            Double yi = records.get(i).getWeight();
            DataPoint v = new DataPoint(xi, yi);
            values[i] = v;
        }

        graphView.getViewport().setMinX(records.get(0).getDate().getTime());
        graphView.getViewport().setMaxX(records.get(records.size()-1).getDate().getTime());
        graphView.getViewport().setXAxisBoundsManual(true);

        return values;
    }

    class AsyncLoad extends AsyncTask<Void, Void, List<WeightRecordItem>> {

        @Override
        protected List<WeightRecordItem> doInBackground(Void... voids){
            NutrifitDatabase nutrifitDb = NutrifitDatabase.getDatabase(getContext());
            List<WeightRecordItem> items = nutrifitDb.weightRecordItemDao().getAllFromUser(sp.getLong("id", 0));

            return items;
        }

        @Override
        protected void onPostExecute(List<WeightRecordItem> items){
            super.onPostExecute(items);
            records = items;
            generateGraph();
        }
    }
}