package es.unex.giiis.asee.proyecto.ui.horario;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.NetworkImageLoaderRunnable;
import es.unex.giiis.asee.proyecto.R;

public class DetallesPlantillaAdapter extends RecyclerView.Adapter<DetallesPlantillaAdapter.ViewHolder> {
    private List<RecipePlantillaItem> mItems = new ArrayList<>();

    public interface OnItemClickListener {
        void onItemClick(RecipePlantillaItem item);     //Type of the element to be returned
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(RecipePlantillaItem item);     //Type of the element to be returned
    }

    private final OnItemClickListener listener;
    private final OnDeleteClickListener deleteListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public DetallesPlantillaAdapter(OnDeleteClickListener deleteListener, OnItemClickListener listener) {
        this.listener = listener;
        this.deleteListener = deleteListener;
    }

    // Create new views (invoked by the layout manager)
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent,
                                         int viewType) {
        // - Inflate the View for every element
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_diet_item, parent, false);

        return new ViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position), listener, deleteListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(RecipePlantillaItem item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void update(RecipePlantillaItem item) {

        int i = 0;
        for (RecipePlantillaItem recipePlantillaItem : mItems) {
            if (recipePlantillaItem.getId() == item.getId()) {
                mItems.set(i, item);
                break;
            }
            i++;
        }
        notifyDataSetChanged();

    }

    public void delete(RecipePlantillaItem item) {

        int i = 0;
        for (RecipePlantillaItem recipePlantillaItem : mItems) {
            if (recipePlantillaItem.getId() == item.getId()) {
                mItems.remove(i);
                break;
            }
            i++;
        }
        notifyDataSetChanged();

    }

    public void load(List<RecipePlantillaItem> items) {

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public List<RecipePlantillaItem> getList() {
        return mItems;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView mLabelView;
        private final TextView mPeriodView;
        private final ImageView mImageView;
        private final ImageButton deleteButton;

        public ViewHolder(View itemView) {
            super(itemView);

            // - Get the references to every widget of the Item View
            mLabelView = itemView.findViewById(R.id.recipeText);
            mPeriodView = itemView.findViewById(R.id.periodTextView);
            mImageView = itemView.findViewById(R.id.imageView);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }

        public void bind(final RecipePlantillaItem item, final OnItemClickListener listener, final OnDeleteClickListener deleteListener) {

            // - Display Title in TextView
            mLabelView.setText(item.getTitle());

            mPeriodView.setText(String.valueOf(item.getPeriod()));
            

            itemView.setOnClickListener(v -> listener.onItemClick(item));

            AppExecutors.getInstance().networkIO().execute(new NetworkImageLoaderRunnable(item.getImageurl(), mImageView::setImageDrawable));
            deleteButton.setOnClickListener(view -> deleteListener.onDeleteClick(item));
        }
    }
}

