package com.example.javcoz.sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    public boolean TextWasScaled = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        /*
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        if (!prefs.getBoolean("firstTime", false)) {

            Toast toast = Toast.makeText(getApplicationContext(), "Thanks for testing.", Toast.LENGTH_LONG);
            toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
            toast.show();

            // mark first time has runned.
            SharedPreferences.Editor editor = prefs.edit();
            editor.putBoolean("firstTime", true);
            editor.commit();
        }
        */

        Button playButton = (Button) findViewById(R.id.PlaySudokuButton );
        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, playmenu.class));
            }
        });

        Button solveaPuzzle = (Button) findViewById(R.id.SolveAPuzzleButton);
        solveaPuzzle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, solveapuzzle.class));
            }
        });

        Button aboutButton = (Button) findViewById(R.id.AboutButton);
        aboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, About.class));
            }
        });

        Button exitButton = (Button) findViewById(R.id.ExitButton);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InitialActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.putExtra("EXIT", true);
                startActivity(intent);
            }
        });




    }







    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // ************************ SCALE UI TO THE DIMENTIONS OF ANY ARBITRARY PHONE-SCREEN


    private void scaleContents(View rootView, View container) {
        // Compute the scaling ratio
        float xScale = (float) container.getWidth() / rootView.getWidth();
        float yScale = (float) container.getHeight() / rootView.getHeight();
        float scale = Math.min(xScale, yScale);
        // Scale our contents
        if(!TextWasScaled)
            scaleViewAndChildren(rootView, scale);
        TextWasScaled= true;
    }


    // Scale the given view, its contents, and all of its children by the given factor.
    public void scaleViewAndChildren(View root, float scale) {
// Retrieve the view's layout information
        ViewGroup.LayoutParams layoutParams = root.getLayoutParams();
        // Scale the view itself
        if (layoutParams.width != ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.width != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.width *= scale;
        }
        if (layoutParams.height != ViewGroup.LayoutParams.MATCH_PARENT && layoutParams.height != ViewGroup.LayoutParams.WRAP_CONTENT) {
            layoutParams.height *= scale;
        }

        // If this view has margins, scale those too
        if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
            ViewGroup.MarginLayoutParams marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
            marginParams.leftMargin *= scale;
            marginParams.rightMargin *= scale;
            marginParams.topMargin *= scale;
            marginParams.bottomMargin *= scale;
        }


        // Set the layout information back into the view
        root.setLayoutParams(layoutParams);

        // Scale the view's padding
        root.setPadding(
                (int) (root.getPaddingLeft() * scale),
                (int) (root.getPaddingTop() * scale),
                (int) (root.getPaddingRight() * scale),
                (int) (root.getPaddingBottom() * scale));


        // If the root view is a TextView, scale the size of its text
        if ((root instanceof TextView) && (TextWasScaled == false)) {
            TextView textView = (TextView) root;
            textView.setTextSize(textView.getTextSize() * scale);
        }


        // If the root view is a ViewGroup, scale all of its children recursively
        if (root instanceof ViewGroup ) {
            ViewGroup groupView = (ViewGroup) root;
            for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
                scaleViewAndChildren(groupView.getChildAt(cnt), scale);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasWindowFocus());
        scaleContents(findViewById(R.id.conents_main), (findViewById(R.id.container_main)));
    }


    // ********************************************************



}
