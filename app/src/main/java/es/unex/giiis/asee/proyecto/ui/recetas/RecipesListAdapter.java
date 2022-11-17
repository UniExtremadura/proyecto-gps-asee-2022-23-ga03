package es.unex.giiis.asee.proyecto.ui.recetas;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.DecimalFormat;
import java.util.List;

import es.unex.giiis.asee.proyecto.AppExecutors;
import es.unex.giiis.asee.proyecto.NetworkImageLoaderRunnable;
import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class RecipesListAdapter extends RecyclerView.Adapter<RecipesListAdapter.RecipesViewHolder>{

    private List<Recipe> mDataset;

    public interface OnListInteractionListener{
        void onListInteraction(Recipe recipe);
    }

    public OnListInteractionListener mListener;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class RecipesViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mLabelView;
        public TextView mDishView;
        public TextView mMealView;
        public TextView mCaloryView;
        public ImageView mImageView;

        public View mView;

        public Recipe mItem;

        public RecipesViewHolder(View v) {
            super(v);
            mView=v;
            mLabelView = v.findViewById(R.id.recipeText);
            mMealView = v.findViewById(R.id.mealText);
            mDishView = v.findViewById(R.id.dishText);
            mCaloryView = v.findViewById(R.id.caloryText);
            mImageView = v.findViewById(R.id.imageView);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RecipesListAdapter(List<Recipe> myDataset, OnListInteractionListener listener) {
        mDataset = myDataset;
        mListener = listener;
    }

    @NonNull
    @Override
    public RecipesListAdapter.RecipesViewHolder onCreateViewHolder(ViewGroup parent,
                                                                   int viewType) {
        // create a new view
        // Create new views (invoked by the layout manager)
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recipe_item, parent, false);

        return new RecipesViewHolder(v);
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final RecipesViewHolder holder, int position) {
        DecimalFormat df = new DecimalFormat("0.00");
        holder.mItem = mDataset.get(position);
        holder.mLabelView.setText(mDataset.get(position).getLabel());
        holder.mMealView.setText(String.format("Meal type: %s", mDataset.get(position).getMealType().get(0)));
        holder.mDishView.setText(String.format("Dish type: %s", mDataset.get(position).getDishType().get(0)));
        holder.mCaloryView.setText(String.format("Calories/ration: %s %s", df.format(mDataset.get(position).getCalories()/mDataset.get(position).getYield()), " cal"));

        AppExecutors.getInstance().networkIO().execute(new NetworkImageLoaderRunnable(mDataset.get(position).getImage(), d -> holder.mImageView.setImageDrawable(d)));


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

    public List<Recipe> getDataset(){
        return mDataset;
    }

    public void swap(List<Recipe> dataset){
        mDataset = dataset;
        notifyDataSetChanged();
    }
}
