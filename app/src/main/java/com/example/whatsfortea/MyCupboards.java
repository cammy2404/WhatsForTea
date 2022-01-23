package com.example.whatsfortea;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;

public class MyCupboards extends AppCompatActivity {

    boolean addItemActive = false;
    int screenWidth = 0;
    int screenHeight = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cupboards);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        screenHeight = displayMetrics.heightPixels;
        screenWidth = displayMetrics.widthPixels;

        LinearLayout itemList = (LinearLayout)findViewById(R.id.itemList);

        String[] itemLocations = {"cupboard", "fridge", "freezer"};
        for(String s : itemLocations) {
            populateListFromFile(s);
        }

        Button btn_add_item = (Button)findViewById(R.id.addItemButton);
        btn_add_item.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
//                String name = "Default";
//                String quantity = "Quantity";
//                String price = "1.50";
//                String location = "cupboard";
//                String summary = "This will have a short description of the item if the person has supplied one, it could have brands or just some generic note";
//                displayItem(name, quantity, price, location, summary);
                createItem();
            }
        });

        Button btn_view_cupboards = (Button)findViewById(R.id.cupboardsButton);
        btn_view_cupboards.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemList.removeAllViews();
                populateListFromFile(itemLocations[0]);
            }
        });

        Button btn_view_fridge = (Button)findViewById(R.id.fridgeButton);
        btn_view_fridge.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemList.removeAllViews();
                populateListFromFile(itemLocations[1]);
            }
        });

        Button btn_view_freezer = (Button)findViewById(R.id.freezerButton);
        btn_view_freezer.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemList.removeAllViews();
                populateListFromFile(itemLocations[2]);
            }
        });

        Button btn_view_all = (Button)findViewById(R.id.viewAllButton);
        btn_view_all.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                itemList.removeAllViews();
                for(String s : itemLocations) {
                    populateListFromFile(s);
                }
            }
        });

        ScrollView scrollView = (ScrollView)findViewById(R.id.scrollView);
        scrollView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent e) {
                return addItemActive;
            }
        });
    }

    public String loadJSONFromAsset() {
        String json = null;
        try {
            InputStream is = this.getAssets().open("items.json");
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

    public void populateListFromFile(String itemLocation) {
        try {
            JSONObject obj = new JSONObject(loadJSONFromAsset());
            JSONArray item_array = obj.getJSONArray(itemLocation);

            for (int i = 0; i < item_array.length(); i++) {
                JSONObject item = item_array.getJSONObject(i);
                String item_name = item.getString("name");
                String item_quantity = item.getString("quantity");
                String item_price = item.getString("price");
                String item_summary = item.getString("summary");

                displayItem(item_name, item_quantity, item_price, itemLocation, item_summary);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void displayItem(String name, String quantity, String price, String location, String summary) {
        LinearLayout parent = new LinearLayout(this);
        parent.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        parent.setOrientation(LinearLayout.HORIZONTAL);
        parent.setPadding(0, 16, 0, 16);

        TextView image_placeholder = new TextView(this);
        image_placeholder.setBackgroundColor(Color.GRAY);
        image_placeholder.setLayoutParams(new LinearLayout.LayoutParams(0, 250, 1));

        LinearLayout info = new LinearLayout(this);
        info.setLayoutParams(new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT, 3));
        info.setOrientation(LinearLayout.VERTICAL);

        TextView header_text = new TextView(this);
        header_text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header_text.setBackgroundColor(Color.LTGRAY);
        header_text.setText(name);
        header_text.setTextSize(24);
        info.addView(header_text);


        LinearLayout item_data = new LinearLayout(this);
        item_data.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        item_data.setOrientation(LinearLayout.HORIZONTAL);

        TextView item_quantity = new TextView(this);
        item_quantity.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
//        item_quantity.setBackgroundColor(Color.LTGRAY);
        item_quantity.setText(quantity);
        item_quantity.setTextSize(18);
        item_data.addView(item_quantity);

        TextView item_price = new TextView(this);
        item_price.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
//        item_price.setBackgroundColor(Color.CYAN);
        item_price.setText("£" + price);
        item_price.setTextSize(18);
        item_data.addView(item_price);

        TextView item_location = new TextView(this);
        item_location.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT, 1));
//        item_price.setBackgroundColor(Color.CYAN);
        item_location.setText(location);
        item_location.setTextSize(14);
        item_location.setGravity(Gravity.RIGHT);
        item_data.addView(item_location);

        info.addView(item_data);


        TextView summary_text = new TextView(this);
        summary_text.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        summary_text.setBackgroundColor(Color.YELLOW);
        summary_text.setText(summary);
        summary_text.setTextSize(14);
        info.addView(summary_text);

        parent.addView(image_placeholder);
        parent.addView(info);

        LinearLayout itemList = (LinearLayout)findViewById(R.id.itemList);
        itemList.addView(parent);
    }

    public void createItem() {
        setButtonsEnabled(false);
        addItemActive = true;

        TextView greySquare = new TextView(this);
        greySquare.setBackgroundColor(Color.argb(200, 50, 50, 50));
        greySquare.setLayoutParams(new ConstraintLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        ConstraintLayout frame = new ConstraintLayout(this);
        ConstraintLayout.LayoutParams layoutParams = new ConstraintLayout.LayoutParams((int)(screenWidth * 0.75), (int)(screenHeight * 0.6));
        layoutParams.endToEnd = ConstraintSet.PARENT_ID;
        layoutParams.startToStart = ConstraintSet.PARENT_ID;
        layoutParams.topToTop = ConstraintSet.PARENT_ID;
        layoutParams.bottomToBottom = ConstraintSet.PARENT_ID;
        frame.setLayoutParams(layoutParams);

        LinearLayout body = new LinearLayout(this);
        body.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        body.setOrientation(LinearLayout.VERTICAL);
        body.setBackgroundColor(Color.YELLOW);
        frame.addView(body);

        TextView header = new TextView(this);
        header.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        header.setText("Create New Item");
        header.setTextSize(32);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        header.setPadding(0, 16, 0, 16);
        header.setBackgroundColor(Color.CYAN);
        body.addView(header);


        String[] fields = {"name", "quantity", "cost"};

        for (String field : fields){

            TextView nameHeader = new TextView(this);
            nameHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nameHeader.setText("Item " + field);
            nameHeader.setTextSize(16);
            nameHeader.setGravity(Gravity.CENTER_HORIZONTAL);
            nameHeader.setPadding(0, 32, 0, 4);
            nameHeader.setBackgroundColor(Color.GREEN);
            body.addView(nameHeader);

            EditText nameInput = new EditText(this);
            nameInput.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            nameInput.setTextSize(16);
            nameInput.setPadding(32, 16, 32, 16);
            nameInput.setBackgroundColor(Color.WHITE);
            nameInput.setHighlightColor(Color.CYAN);
            body.addView(nameInput);

        }

//        TextView quantityHeader = new TextView(this);
//        quantityHeader.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        quantityHeader.setText("Item quantity");
//        quantityHeader.setTextSize(16);
//        quantityHeader.setGravity(Gravity.CENTER_HORIZONTAL);
//        quantityHeader.setPadding(0, 32, 0, 4);
//        quantityHeader.setBackgroundColor(Color.GREEN);
//        body.addView(quantityHeader);
//
//        EditText quantityInput = new EditText(this);
//        quantityInput.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        quantityInput.setTextSize(16);
//        quantityInput.setPadding(32, 4,32,16);
//        quantityInput.setBackgroundColor(Color.WHITE);
//        body.addView(quantityInput);

        ConstraintLayout screen = (ConstraintLayout)findViewById(R.id.parent);
        screen.addView(greySquare);
        screen.addView(frame);
    }

    public void setButtonsEnabled(boolean state) {
        Button tempButton = (Button)findViewById(R.id.cupboardsButton);
        tempButton.setEnabled(state);
        tempButton = (Button)findViewById(R.id.fridgeButton);
        tempButton.setEnabled(state);
        tempButton = (Button)findViewById(R.id.freezerButton);
        tempButton.setEnabled(state);
        tempButton = (Button)findViewById(R.id.viewAllButton);
        tempButton.setEnabled(state);
        tempButton = (Button)findViewById(R.id.addItemButton);
        tempButton.setEnabled(state);
    }

    public void removeAddItemScreen() {
        ConstraintLayout screen = (ConstraintLayout)findViewById(R.id.parent);
        screen.removeAllViews();
        setButtonsEnabled(true);
        addItemActive = false;
    }

    @Override
    public void onBackPressed() {
        if(addItemActive) {
            new AlertDialog.Builder(this)
                .setTitle("Forget Item?")
                .setMessage("Are you sure you want to forget about this item?")
                .setPositiveButton("Yes, I'm sure", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        removeAddItemScreen();
                    }
                })
                .setNegativeButton("Wait, no!", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                    }
                })
                .setIcon(android.R.drawable.ic_dialog_alert)
                .show();
        } else {
            super.onBackPressed();
        }
    }
}