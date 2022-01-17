package com.example.whatsfortea;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

public class RecipeList extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        LinearLayout itemList = (LinearLayout)findViewById(R.id.itemList);

        Button btn_add_item = (Button)findViewById(R.id.addItemButton);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                addNewItemToList(itemList);
            }
        });
    }

    public void addNewItemToList(LinearLayout itemList) {
        Button newBtn = new Button(this);
        newBtn.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        newBtn.setText("NEW Recipe");

        itemList.addView(newBtn);
    }
}