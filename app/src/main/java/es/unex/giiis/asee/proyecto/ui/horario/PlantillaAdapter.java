package es.unex.giiis.asee.proyecto.ui.horario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;

public class PlantillaAdapter extends RecyclerView.Adapter<PlantillaAdapter.ViewHolder> {
    private List<PlantillaItem> mItems = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(PlantillaItem item);     //Type of the element to be returned
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(PlantillaItem item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlantillaAdapter(OnDeleteClickListener deleteListener, OnItemClickListener listener) {
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public PlantillaAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                          int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.plantilla_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position), deleteListener, listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(PlantillaItem item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void update(PlantillaItem item) {

        int i = 0;
        for (PlantillaItem plantillaItem : mItems) {
            if (plantillaItem.getId() == item.getId()) {
                mItems.set(i, item);
                break;
            }
            i++;
        }
        notifyDataSetChanged();

    }

    public void delete(PlantillaItem item) {

        int i = 0;
        for (PlantillaItem plantillaItem : mItems) {
            if (plantillaItem.getId() == item.getId()) {
                mItems.remove(i);
                break;
            }
            i++;
        }
        notifyDataSetChanged();

    }

    public void clear() {

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<PlantillaItem> items) {

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public List<PlantillaItem> getList() {
        return mItems;
    }

    public Object getItem(int pos) {
        return mItems.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView title;
        private final TextView priorityView;
        private final TextView dayView;
        private final ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            // - Get the references to every widget of the Item View
            title = itemView.findViewById(R.id.titleText);
            priorityView = itemView.findViewById(R.id.dayText);
            dayView = itemView.findViewById(R.id.priorityText);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(final PlantillaItem plantillaItem, final OnDeleteClickListener deleteListener, final OnItemClickListener listener) {

            // - Display Title in TextView
            title.setText(plantillaItem.getTitle());

            // - Display Priority in a TextView
            priorityView.setText(plantillaItem.getPriority().toString());

            // - Display Day in a TextView
            dayView.setText(plantillaItem.getDay().toString());

            deleteButton.setOnClickListener(view -> deleteListener.onDeleteClick(plantillaItem));

            itemView.setOnClickListener(v -> listener.onItemClick(plantillaItem));
        }
    }

}

