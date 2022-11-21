package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;
import es.unex.giiis.asee.proyecto.R;

public class FavoriteExcerciseListAdapter extends RecyclerView.Adapter<FavoriteExcerciseListAdapter.FavoritesViewHolder>{
    private List<FavoriteExcerciseItem> mDataset;

    public interface OnListInteractionListener{
        void onListInteraction(FavoriteExcerciseItem item);
    }

    public interface OnDeleteButtonInteractionListener{
        void onDeleteInteraction(FavoriteExcerciseItem item);
    }

    private final FavoriteExcerciseListAdapter.OnListInteractionListener mListener;
    private final FavoriteExcerciseListAdapter.OnDeleteButtonInteractionListener deleteListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public FavoriteExcerciseListAdapter(List<FavoriteExcerciseItem> myDataset, FavoriteExcerciseListAdapter.OnListInteractionListener listener,
                                        FavoriteExcerciseListAdapter.OnDeleteButtonInteractionListener deleteListener) {
        this.mDataset = myDataset;
        this.mListener = listener;
        this.deleteListener = deleteListener;
    }

    @NonNull
    @Override
    public FavoriteExcerciseListAdapter.FavoritesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                            int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.favorite_excercise_item, parent, false);

        return new FavoriteExcerciseListAdapter.FavoritesViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(FavoriteExcerciseListAdapter.FavoritesViewHolder holder, int position) {
        holder.bind(mDataset.get(position),deleteListener,mListener);
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public void swap(List<FavoriteExcerciseItem> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }

    public void delete(FavoriteExcerciseItem item){

        int i = 0;
        for (FavoriteExcerciseItem plantillaItem: mDataset) {
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
        public TextView mTypeView;
        public TextView mMuscleView;
        public TextView mDifficultyView;
        public ImageButton deleteButton;

        public FavoritesViewHolder(View v) {
            super(v);
            mLabelView = v.findViewById(R.id.nameText);
            mTypeView = v.findViewById(R.id.typeText);
            mMuscleView = v.findViewById(R.id.muscleText);
            mDifficultyView = v.findViewById(R.id.difficultyText);
            deleteButton = v.findViewById(R.id.deleteButton);
        }

        public void bind(final FavoriteExcerciseItem FavoriteItem, final FavoriteExcerciseListAdapter.OnDeleteButtonInteractionListener deleteListener, final FavoriteExcerciseListAdapter.OnListInteractionListener listener) {
            DecimalFormat df = new DecimalFormat("0.00");
            mLabelView.setText(FavoriteItem.getTittle());
            mTypeView.setText(String.format("Type: %s", FavoriteItem.getType()));
            mMuscleView.setText(String.format("Muscle: %s", FavoriteItem.getMuscle()));
            mDifficultyView.setText(String.format("Difficulty: %s", FavoriteItem.getDifficulty()));

            itemView.setOnClickListener(v -> listener.onListInteraction(FavoriteItem));

            deleteButton.setOnClickListener(view -> deleteListener.onDeleteInteraction(FavoriteItem));
        }
    }
}
