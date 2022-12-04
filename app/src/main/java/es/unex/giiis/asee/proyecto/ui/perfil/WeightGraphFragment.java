package es.unex.giiis.asee.proyecto.ui.perfil;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Date;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppContainer;
import es.unex.giiis.asee.proyecto.MyApplication;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.login_register.WeightRecordItem;
import es.unex.giiis.asee.proyecto.viewmodels.WeightViewModel;

public class WeightGraphFragment extends Fragment {

    private GraphView graphView;
    private LineGraphSeries<DataPoint> series;

    private WeightViewModel mWeightRecordItem;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_weight_graph, container, false);

        graphView = v.findViewById(R.id.graph);

        AppContainer appContainer = ((MyApplication) getActivity().getApplication()).appContainer;

        mWeightRecordItem = new ViewModelProvider((ViewModelStoreOwner) this, (ViewModelProvider.Factory) appContainer.weightFactory).get(WeightViewModel.class);

        mWeightRecordItem.getAllRecords().observe(getViewLifecycleOwner(), new Observer<List<WeightRecordItem>>() {
            @Override
            public void onChanged(List<WeightRecordItem> weightRecordItems) {
                generateGraph(weightRecordItems);
            }
        });

        return v;
    }

    private void generateGraph(List<WeightRecordItem> records) {
        series = new LineGraphSeries<>(getDataPoint(records));
        graphView.addSeries(series);

        // set date label formatter
        graphView.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(getActivity()));

        graphView.getGridLabelRenderer().setNumHorizontalLabels(3);

        graphView.getViewport().setYAxisBoundsManual(true);
        graphView.getViewport().setMinY(20);
        graphView.getViewport().setMaxY(200);

    }

    private DataPoint[] getDataPoint(List<WeightRecordItem> records) {
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
}