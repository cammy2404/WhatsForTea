package com.example.whatsfortea;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        Button btn_add_item = (Button)findViewById(R.id.addItemButton);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNewItemToList();
            }
        });
    }

    public void addNewItemToList() {
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
        recipeTitle.setText("Recipe Title");
        recipeTitle.setTextSize(24);
        recipeTitle.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        recipeTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.addView(recipeTitle);

        parent.addView(header);

        TextView recipeSummary = new TextView(this);
        recipeSummary.setText("Recipe summary goes in here, it might be a little bit longer so should hopefully take up a couple of lines, meaning that the wrapping is working as expected");
        recipeSummary.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.addView(recipeSummary);

        Button viewRecipeBtn = new Button(this);
        viewRecipeBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        viewRecipeBtn.setText("View Recipe");
        viewRecipeBtn.setTextSize(18);
        parent.addView(viewRecipeBtn);

        Button addRecipeToMenuBtn = new Button(this);
        addRecipeToMenuBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addRecipeToMenuBtn.setText("Add this to my menu");
        parent.addView(addRecipeToMenuBtn);

        LinearLayout itemList = (LinearLayout)findViewById(R.id.itemList);
        itemList.addView(parent);
    }
}