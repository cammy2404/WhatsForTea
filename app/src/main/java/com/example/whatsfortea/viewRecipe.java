package com.example.whatsfortea;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class viewRecipe extends AppCompatActivity {

    String tag = "NewTagThatIsntUsed";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipe);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String recipeName = extras.getString("name");
            populateRecipeFromFile(recipeName);
        } else {
            somethingWentWrong("No extras supplied");
        }
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

    public void populateRecipeFromFile(String recipeName) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray item_array = obj.getJSONArray("recipes");

            int pos = -1;
            for (int i = 0; i < item_array.length(); i++) {
                JSONObject item = item_array.getJSONObject(i);
                String foundName = item.getString("name");
                Log.i(tag, recipeName + " compared to " + foundName);
                if (recipeName.equals(foundName)) {
                    pos = i;
                    Log.i(tag, "Match found at " + pos);
                    break;
                }
            }

            if (pos != -1) {
                Log.i(tag, "Into the build step");
                JSONObject recipe = item_array.getJSONObject(pos);

                String description = recipe.getString("description");
                JSONArray ingredients = recipe.getJSONArray("ingredients");
                JSONArray steps = recipe.getJSONArray("steps");

                displayRecipe(recipeName, description, ingredients, steps);
            } else {
                somethingWentWrong("pos = -1");
            }
        } catch (JSONException e) {
            e.printStackTrace();
            somethingWentWrong("Trying to fetch the other data");
        }
    }

    public void displayRecipe(String name, String description, JSONArray ingredients, JSONArray steps) {
        Log.i(tag, "Displaying " + name + " recipe");
        LinearLayout parent = (LinearLayout)findViewById(R.id.parent);

        TextView recipeTitle = new TextView(this);
        recipeTitle.setText(name);
        recipeTitle.setTextSize(48);
        recipeTitle.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        recipeTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        recipeTitle.setGravity(Gravity.CENTER_HORIZONTAL);
        parent.addView(recipeTitle);

        TextView ingredientsHeader = new TextView(this);
        ingredientsHeader.setText("Ingredients");
        ingredientsHeader.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        ingredientsHeader.setTextSize(32);
        ingredientsHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ingredientsHeader.setPadding(16, 64, 16, 16);
        parent.addView(ingredientsHeader);
        for (int i = 0; i < ingredients.length(); i++) {
            try {
                JSONObject ingredient = ingredients.getJSONObject(i);
                parent.addView(buildIngredient(ingredient));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(tag, "Ingredient item failed to load");
            }
        }

        TextView stepsHeader = new TextView(this);
        stepsHeader.setText("Steps");
        stepsHeader.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        stepsHeader.setTextSize(32);
        stepsHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        stepsHeader.setPadding(16, 64, 16, 16);
        parent.addView(stepsHeader);
        for (int i = 0; i < steps.length(); i++) {
            try {
                JSONObject step = steps.getJSONObject(i);
                parent.addView(buildStep(step));
            } catch (Exception e) {
                e.printStackTrace();
                Log.e(tag, "Step item failed to load");
            }
        }

    }

    public LinearLayout buildIngredient(JSONObject ingredient) {
        try {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.HORIZONTAL);
            item.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView itemName = new TextView(this);
            itemName.setText("- " + ingredient.getString("name"));
            itemName.setTextSize(26);
            itemName.setPadding(64, 16, 16, 16);
            itemName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            item.addView(itemName);

            TextView itemQuantity = new TextView(this);
            itemQuantity.setText(ingredient.getString("quantity") + " " + ingredient.getString("units"));
            itemQuantity.setTextSize(22);
            itemQuantity.setPadding(64, 16, 16, 16);
            itemQuantity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
            item.addView(itemQuantity);

            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public LinearLayout buildStep(JSONObject step) {
        try {
            LinearLayout item = new LinearLayout(this);
            item.setOrientation(LinearLayout.VERTICAL);
            item.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView itemName = new TextView(this);
            itemName.setText(step.getString("name"));
            itemName.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
            itemName.setTextSize(26);
            itemName.setPadding(16, 16, 16, 4);
            itemName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            item.addView(itemName);

            TextView itemQuantity = new TextView(this);
            itemQuantity.setText(step.getString("description"));
            itemQuantity.setTextSize(22);
            itemQuantity.setPadding(16, 4, 16, 16);
            itemQuantity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            item.addView(itemQuantity);

            return item;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    public void somethingWentWrong(String description) {
        new AlertDialog.Builder(this)
            .setTitle("Recipe Not Found")
            .setMessage("Something went wrong and we wouldn't find the recipe - " + description)
            .setNeutralButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    viewRecipe.super.onBackPressed();
                }
            })
            .setIcon(android.R.drawable.ic_dialog_alert)
            .show();
    }
}