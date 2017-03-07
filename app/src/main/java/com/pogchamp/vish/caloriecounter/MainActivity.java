package com.pogchamp.vish.caloriecounter;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class MainActivity extends AppCompatActivity  {


    private RelativeLayout relativeLayout,popupLayout;
    private LayoutInflater layoutInflater;
    private EditText foodName,foodAmount,foodSize,foodManual,calManual;
    private TextView calorieCounter;
    private int numCals,placeholder = 0;
    private String size;
    public String[] foodNames = new String[100];
    List<String> food_list = new ArrayList<String>(Arrays.asList(foodNames));

    private int counter = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        food_list.clear();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.mainmenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {



          switch(item.getItemId()) {

              case R.id.clear:
                  SharedPreferences pref;
                  pref = getSharedPreferences("info",MODE_PRIVATE);
                  SharedPreferences.Editor editor = pref.edit();
                  editor.putInt("val",0);
                  editor.apply();
                  numCals=0;
                  calorieCounter.setText(numCals+"");
                  food_list.clear();
                  break;

              case R.id.add_green:
                      popUpAuto(item);

                      break;
              case R.id.add_blue:

                  item.setEnabled(false);
                  //MANUAL ADDING
                 // Log.d("msg","WHAT IN THE ACTUAL FUCK IS GOING ON");

                  relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);
                  LayoutInflater layoutInflater2 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                  final View view1 = layoutInflater2.inflate(R.layout.activity_manual_add,null);

                  foodManual = (EditText)view1.findViewById(R.id.manualFood);
                  calManual = (EditText) view1.findViewById(R.id.manualCals);
                  Button bManualDone = (Button)view1.findViewById(R.id.manual_add);

                  relativeLayout.addView(view1,1600,1300);
                  relativeLayout.setOnTouchListener(new View.OnTouchListener() {
                      @Override
                      public boolean onTouch(View v, MotionEvent event) {

                          InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                          imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                          return false;

                      }
                  });



                  bManualDone.setOnClickListener(new View.OnClickListener() {
                      @Override
                      public void onClick(View v) {

                          String placeHolder;
                          placeHolder = calManual.getText().toString();
                          numCals += Integer.parseInt(placeHolder);
                          calorieCounter.setText(numCals+"");
                          relativeLayout.removeView(view1);
                          InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                          imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                         //foodNames[counter] = foodManual.getText().toString();
                          //counter++;
                          String cunt;
                          cunt = foodManual.getText().toString();
                          addElementToList(cunt);

                    item.setEnabled(true);
                      }
                  });


                  break;
          }


        return super.onOptionsItemSelected(item);
    }



    @Override
    protected void onResume() {
        super.onResume();

        String placeholder;
        calorieCounter = (TextView) findViewById(R.id.calorie);
        SharedPreferences shared = getSharedPreferences("info",MODE_PRIVATE);
        numCals = shared.getInt("val",1);
        calorieCounter.setText(numCals + "");
        //TODO RECALL STRING ON RESUME (STORE AND RETRIEVE FOOD_NAMES.LENGTH
       /* for(int i=0;i<foodNames.length;i++){
            placeholder = shared.getString(i+"",null);
            food_list.add(placeholder);

        }*/
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences pref;
        pref = getSharedPreferences("info",MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putInt("val",numCals);
        // TODO SAVE STRING ON PAUSE
        /*food_list.toArray(foodNames);
        for(int i=0;i<foodNames.length;i++){
            editor.putString(i+"",foodNames[i]);

        }
        */
        editor.apply();

    }

    public void popUpAuto(final MenuItem item){

        item.setEnabled(false);
        
        relativeLayout = (RelativeLayout) findViewById(R.id.activity_main);

        LayoutInflater layoutInflater1 = (LayoutInflater) getApplicationContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater1 .inflate(R.layout.activity_pop_up, null );
        foodName = (EditText)view.findViewById(R.id.foodName);
        foodAmount = (EditText)view.findViewById(R.id.foodAmount);
        foodSize = (EditText)view.findViewById(R.id.editText9);

        relativeLayout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                return false;
            }
        });

        Button bDone = (Button)view.findViewById(R.id.done);
        relativeLayout.addView(view,1300,1600);


        bDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(),0);
                calorieCounter = (TextView) findViewById(R.id.calorie);
                String x = foodName.getText().toString();
                x = x.toLowerCase();
                String y = foodAmount.getText().toString();
                size = foodSize.getText().toString();
                int z = Integer.parseInt(y);
                Log.d("FOOD NAME -------->", x);
                Log.d("FOOD AMOUNT ------->", String.valueOf(z));

                addElementToList(x);



                switch(x){

                    case "banana":
                        if(size.equals("small")){
                            numCals = numCals + (90*z);
                        }
                        else if(size.equals("med")){
                            numCals = numCals + (105*z);
                        }
                        else if(size.equals("large")){
                            numCals = numCals + (121*z);
                        }
                        else{
                            numCals = numCals + (105*z);
                        }
                        break;


                    case "bread":
                        numCals = numCals + (66*z);
                        break;

                    case "noodles":
                        numCals = numCals + (221*z);
                        break;

                    case "bacon":
                        if(size.equals("small")){
                            numCals = numCals + (46*z);
                        }
                        else if(size.equals("med")){
                            numCals = numCals + (46*z);
                        }
                        else if(size.equals("large")){
                            numCals = numCals + (61*z);
                        }
                        else{
                            numCals = numCals + (46*z);
                        }
                        break;


                    case "plain dosa":
                        numCals = numCals + (120*z);
                        break;


                    case "masala dosa":
                        numCals = numCals + (415*z);
                        break;


                    case "idli":
                        numCals = numCals + (51*z);
                        break;


                    case "vada":
                        numCals = numCals + (103*z);
                        break;


                    case "rice":
                        numCals = numCals + (206*z);
                        break;


                    case "butter chicken":
                        numCals = numCals +(384*z);
                        break;


                    case "egg":
                        numCals = numCals + (78*z);
                        break;


                    case "cheese slices":
                        numCals = numCals + (113*z);
                        break;


                    case "nasi lemak":
                        numCals = numCals + (1017*z);
                        break;

                    case "basic pizza":
                        numCals = numCals + (285*z);
                        break;

                    case "pizza":
                        numCals = numCals + (285*z);
                        break;

                    case "chicken pizza":
                        numCals = numCals + (170*z);
                        break;

                    case "burger":
                        numCals = numCals + (515*z);
                        break;

                    default:
                        Log.d("msg","you done messed up");
                }

                calorieCounter.setText(numCals + "");
                relativeLayout.removeView(view);
                item.setEnabled(true);
            }
        });


    }

    public void addElementToList(String x){

        ListView lv = (ListView) findViewById(R.id.list);
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this,android.R.layout.simple_expandable_list_item_1,food_list);
        lv.setAdapter(arrayAdapter);
        food_list.add(x);
        arrayAdapter.notifyDataSetChanged();

    }

}

