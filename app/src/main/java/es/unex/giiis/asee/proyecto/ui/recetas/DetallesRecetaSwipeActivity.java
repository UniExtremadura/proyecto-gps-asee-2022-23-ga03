package es.unex.giiis.asee.proyecto.ui.recetas;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.os.Bundle;
import com.google.gson.Gson;
import java.util.List;

import es.unex.giiis.asee.proyecto.R;
import es.unex.giiis.asee.proyecto.recipesmodel.Recipe;

public class DetallesRecetaSwipeActivity extends AppCompatActivity {

    private List<Recipe> data;
    private Recipe recipe;
    private ViewPager2 viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalles_receta_swipe);

        Intent intent = getIntent();
        Gson gson = new Gson();
        recipe = gson.fromJson(getIntent().getStringExtra("Recipe"), Recipe.class);
        data = (List<Recipe>) intent.getSerializableExtra("Data");

        viewPager = findViewById(R.id.viewpager);
        viewPager.setAdapter(new DetallesRecetaPagerAdapter(data, getSupportFragmentManager(),getLifecycle()));

        String webid = recipe.getUri().substring(recipe.getUri().lastIndexOf("_") + 1);

        for(int i = 0; i<data.size(); i++) {
            String dataId = data.get(i).getUri().substring(data.get(i).getUri().lastIndexOf("_") + 1);
            if(dataId.equals(webid)){
                viewPager.setCurrentItem(i);
                break;
            }
        }
    }
}