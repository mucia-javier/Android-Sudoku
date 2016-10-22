package com.example.javcoz.sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ShowSolution extends AppCompatActivity {
    public boolean TextWasScaled = false;

    private final int N = 9;
    private int viewBoxes[][] =
            {{R.id.d00, R.id.d01, R.id.d02, R.id.d03, R.id.d04, R.id.d05, R.id.d06, R.id.d07, R.id.d08},
                    {R.id.d10, R.id.d11, R.id.d12, R.id.d13, R.id.d14, R.id.d15, R.id.d16, R.id.d17, R.id.d18},
                    {R.id.d20, R.id.d21, R.id.d22, R.id.d23, R.id.d24, R.id.d25, R.id.d26, R.id.d27, R.id.d28},

                    {R.id.d30, R.id.d31, R.id.d32, R.id.d33, R.id.d34, R.id.d35, R.id.d36, R.id.d37, R.id.d38},
                    {R.id.d40, R.id.d41, R.id.d42, R.id.d43, R.id.d44, R.id.d45, R.id.d46, R.id.d47, R.id.d48},
                    {R.id.d50, R.id.d51, R.id.d52, R.id.d53, R.id.d54, R.id.d55, R.id.d56, R.id.d57, R.id.d58},

                    {R.id.d60, R.id.d61, R.id.d62, R.id.d63, R.id.d64, R.id.d65, R.id.d66, R.id.d67, R.id.d68},
                    {R.id.d70, R.id.d71, R.id.d72, R.id.d73, R.id.d74, R.id.d75, R.id.d76, R.id.d77, R.id.d78},
                    {R.id.d80, R.id.d81, R.id.d82, R.id.d83, R.id.d84, R.id.d85, R.id.d86, R.id.d87, R.id.d88},};
    Game theGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_solution);

        //Pull the object from the previous Activity and use the data on that object
        // to solve the sudoku puzzle
        Intent anIntent = getIntent();
        theGame = (Game) anIntent.getSerializableExtra("aGame");
        fillSolutionBoard();
        Toast toast = Toast.makeText(getApplicationContext(), R.string.Showing_Solution, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
        toast.show();

        //Puzzle has been solved and solution has been shown on the screen.
        // Give the option to EXIT or INPUT_A_NEW_PUZZLE
        Button goToMain = (Button) findViewById(R.id.GoToMainMenu);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(ShowSolution.this, MainActivity.class);
                startActivity(theIntent);
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


    public void fillSolutionBoard() {
        TextView aBox;
        for (int i = 0; i < N; ++i)
            for (int j = 0; j < N; ++j) {
                if (viewBoxes[i][j] != 0) {
                    aBox = (TextView) findViewById(viewBoxes[i][j]);
                    if (aBox != null) {
                        if (this.theGame.original[i][j] != 0) { // User-Given Values
                            aBox.setText(Integer.toString(this.theGame.original[i][j]));
                        } else {                          // Solution Values
                            aBox.setText(Integer.toString(this.theGame.board[i][j]));
                            aBox.setBackgroundColor(Color.rgb(101, 175, 220));

                        }

                    }
                }
            }

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
        TextWasScaled = true;
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
        if (root instanceof ViewGroup) {
            ViewGroup groupView = (ViewGroup) root;
            for (int cnt = 0; cnt < groupView.getChildCount(); ++cnt)
                scaleViewAndChildren(groupView.getChildAt(cnt), scale);
        }
    }


    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasWindowFocus());
        scaleContents(findViewById(R.id.show_solution_contents), (findViewById(R.id.show_solution_container)));
    }


    // ********************************************************
}
