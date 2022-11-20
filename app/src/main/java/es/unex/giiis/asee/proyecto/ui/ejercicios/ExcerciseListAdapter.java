package es.unex.giiis.asee.proyecto.ui.ejercicios;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.exercisesModel.Excercise;

public class ExcerciseListAdapter extends RecyclerView.Adapter<ExcerciseListAdapter.ExcercisesViewHolder> {

    private List<Excercise> mDataset;

    public interface OnListInteractionListener {
        void onListInteraction(Excercise excercise);
    }


    public OnListInteractionListener mListener;

    // Provide a suitable constructor (depends on the kind of dataset)
    public ExcerciseListAdapter(List<Excercise> myDataset, OnListInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    @NonNull
    @Override
    public ExcerciseListAdapter.ExcercisesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                        int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.excercise_item, parent, false);

        return new ExcercisesViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ExcercisesViewHolder holder, int position) {

        holder.mNameView.setText(mDataset.get(position).getName());
        holder.mTypeView.setText(String.format("Type: %s", mDataset.get(position).getType()));
        holder.mMuscleView.setText(String.format("Muscle: %s", mDataset.get(position).getMuscle()));
        holder.mDifficultyView.setText(String.format("Difficulty: %s", mDataset.get(position).getDifficulty()));


        holder.mView.setOnClickListener(v -> {
            if (null != mListener) {
                // Notify the active callbacks interface (the activity, if the
                // fragment is attached to one) that an item has been selected.
                mListener.onListInteraction(holder.mItem);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    public List<Excercise> getDataset() {
        return mDataset;
    }

    public void swap(List<Excercise> dataset) {
        mDataset = dataset;
        notifyDataSetChanged();
        Log.d("prueba", mDataset.get(1).getName());
    }


    public static class ExcercisesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNameView;
        public TextView mTypeView;
        public TextView mMuscleView;
        public TextView mDifficultyView;

        public View mView;

        public Excercise mItem;

        public ExcercisesViewHolder(View v) {
            super(v);
            mView = v;
            mNameView = v.findViewById(R.id.excerciseText);
            mTypeView = v.findViewById(R.id.typeText);
            mMuscleView = v.findViewById(R.id.muscleText);
            mDifficultyView = v.findViewById(R.id.difficultyText);
        }
    }
}
