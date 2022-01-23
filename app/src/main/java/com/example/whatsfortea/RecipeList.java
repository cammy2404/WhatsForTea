package com.example.whatsfortea;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        populateListFromFile();

        Button btn_add_item = (Button)findViewById(R.id.addItemButton);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNewItemToList("Name Here", "Description Here");
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("recipes.json");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, "UTF-8");
        } catch (IOException ex) {
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    public void populateListFromFile() {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray item_array = obj.getJSONArray("recipes");

            for (int i = 0; i < item_array.length(); i++) {
                JSONObject item = item_array.getJSONObject(i);
                String name = item.getString("name");
                String description = item.getString("description");

                addNewItemToList(name, description);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void addNewItemToList(String name, String description) {
        LinearLayout parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.VERTICAL);
        parent.setPadding(0, 0, 0, 32);
        parent.setBackgroundColor(Color.GRAY);

        LinearLayout header = new LinearLayout(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setOrientation(LinearLayout.HORIZONTAL);

//        ImageView imageIcon = new ImageView(this);
//        imageIcon.setImageResource(R.drawable.abc_vector_test);
//        imageIcon.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        header.addView(imageIcon);

        TextView recipeTitle = new TextView(this);
        recipeTitle.setText(name);
        recipeTitle.setTextSize(24);
        recipeTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        recipeTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.addView(recipeTitle);

        parent.addView(header);

        TextView recipeSummary = new TextView(this);
        recipeSummary.setText(description);
        recipeSummary.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.addView(recipeSummary);

        Button viewRecipeBtn = new Button(this);
        viewRecipeBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        viewRecipeBtn.setText("View Recipe");
        viewRecipeBtn.setTextSize(18);
        viewRecipeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(RecipeList.this, viewRecipe.class);
                i.putExtra("name", name);
                startActivity(i);
            }
        });
        parent.addView(viewRecipeBtn);

        Button addRecipeToMenuBtn = new Button(this);
        addRecipeToMenuBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addRecipeToMenuBtn.setText("Add this to my menu");
        parent.addView(addRecipeToMenuBtn);

        LinearLayout itemList = (LinearLayout)findViewById(R.id.itemList);
        itemList.addView(parent);
    }
}