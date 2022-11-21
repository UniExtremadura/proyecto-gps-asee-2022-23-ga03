package es.unex.giiis.asee.proyecto.ui.recetas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.NetworkImageLoaderRunnable;
import es.unex.giiis.asee.proyecto.R;

public class FavoriteRecipeListAdapter extends RecyclerView.Adapter<FavoriteRecipeListAdapter.FavoritesViewHolder>{
    private List<FavoriteRecipeItem> mDataset;

    public interface OnListInteractionListener{
        void onListInteraction(FavoriteRecipeItem item);
    }

    public interface OnDeleteButtonInteractionListener{
        void onDeleteInteraction(FavoriteRecipeItem item);
    }

    private final OnListInteractionListener mListener;
    private final OnDeleteButtonInteractionListener deleteListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoriteRecipeListAdapter(List<FavoriteRecipeItem> myDataset, OnListInteractionListener listener,
                                OnDeleteButtonInteractionListener deleteListener) {
        this.mDataset = myDataset;
        this.mListener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public FavoriteRecipeListAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                       int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_favorite_item, parent, false);

        return new FavoritesViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavoritesViewHolder holder, int position) {
        holder.bind(mDataset.get(position),deleteListener,mListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<FavoriteRecipeItem> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }

    public void delete(FavoriteRecipeItem item){

        int i = 0;
        for (FavoriteRecipeItem plantillaItem: mDataset) {
            if(plantillaItem.getId() == item.getId()) {
                mDataset.remove(i);
                break;
            }
            i++;
        }
        notifyDataSetChanged();

    }

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class FavoritesViewHolder extends RecyclerView.ViewHolder {

        public TextView mLabelView;
        public TextView mCaloryView;
        public ImageView mImageView;
        public ImageButton deleteButton;

        public FavoritesViewHolder(View v) {
            super(v);
            mLabelView = v.findViewById(R.id.recipeText);
            mCaloryView = v.findViewById(R.id.caloryText);
            mImageView = v.findViewById(R.id.imageView);
            deleteButton = v.findViewById(R.id.deleteButton);
        }

        public void bind(final FavoriteRecipeItem FavoriteItem, final OnDeleteButtonInteractionListener deleteListener, final OnListInteractionListener listener) {
            DecimalFormat df = new DecimalFormat("0.00");
            mLabelView.setText(FavoriteItem.getTitle());
            mCaloryView.setText(String.format("Calories/ration: %s %s", df.format(FavoriteItem.getCalories()), " cal"));

            AppExecutors.getInstance().networkIO().execute(new NetworkImageLoaderRunnable(FavoriteItem.getImageurl(), d -> mImageView.setImageDrawable(d)));

            itemView.setOnClickListener(v -> listener.onListInteraction(FavoriteItem));

            deleteButton.setOnClickListener(view -> deleteListener.onDeleteInteraction(FavoriteItem));
        }
    }
}