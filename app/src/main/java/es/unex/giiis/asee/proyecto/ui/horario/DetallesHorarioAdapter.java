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

public class DetallesHorarioAdapter extends RecyclerView.Adapter<DetallesHorarioAdapter.ViewHolder> {
    private List<CalendarDayItem> mItems = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(CalendarDayItem item);     //Type of the element to be returned
    }

    public interface OnModifyClickListener {
        void onModifyClick(CalendarDayItem item);     //Type of the element to be returned
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(CalendarDayItem item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;
    private final OnModifyClickListener modifyListener;
    private final OnDeleteClickListener deleteListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DetallesHorarioAdapter(OnDeleteClickListener deleteListener, OnModifyClickListener modifyListener, OnItemClickListener listener) {
        this.listener = listener;
        this.modifyListener = modifyListener;
        this.deleteListener = deleteListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.horario_item, parent, false);
        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position), deleteListener, modifyListener, listener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void delete(CalendarDayItem item) {

        int i = 0;
        for (CalendarDayItem calendarDayItem : mItems) {
            if (calendarDayItem.getId() == item.getId()) {
                mItems.remove(i);
                break;
            }
            i++;
        }
        notifyDataSetChanged();

    }

    public void load(List<CalendarDayItem> items) {

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public List<CalendarDayItem> getList() {
        return mItems;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private final TextView titleView;
        private final TextView timeView;
        private final TextView statusView;
        private final ImageButton modifyButton;
        private final ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            // - Get the references to every widget of the Item View
            titleView = itemView.findViewById(R.id.titleText);
            timeView = itemView.findViewById(R.id.timeTextView);
            statusView = itemView.findViewById(R.id.stateTextView);
            modifyButton = itemView.findViewById(R.id.modifyButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(final CalendarDayItem calendarDayItem, final OnDeleteClickListener deleteListener, OnModifyClickListener modifyListener, final OnItemClickListener listener) {

            // - Display Title in TextView
            titleView.setText(calendarDayItem.getTitle());
            timeView.setText(CalendarDayItem.FORMAT.format(calendarDayItem.getTime()));
            statusView.setText(String.valueOf(calendarDayItem.getStatus()));

            deleteButton.setOnClickListener(view -> deleteListener.onDeleteClick(calendarDayItem));
            modifyButton.setOnClickListener(view -> modifyListener.onModifyClick(calendarDayItem));
            itemView.setOnClickListener(v -> listener.onItemClick(calendarDayItem));
        }
    }
}