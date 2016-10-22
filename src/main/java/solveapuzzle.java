package com.example.javcoz.sudoku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.animation.FastOutLinearInInterpolator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.EditText;
import android.widget.Toast;


public class solveapuzzle extends AppCompatActivity {
    public boolean TextWasScaled = false;
    static final int N = 9;
    private final int ids[][] =
            {{R.id.c00, R.id.c01, R.id.c02, R.id.c03, R.id.c04, R.id.c05, R.id.c06, R.id.c07, R.id.c08},
                    {R.id.c10, R.id.c11, R.id.c12, R.id.c13, R.id.c14, R.id.c15, R.id.c16, R.id.c17, R.id.c18},
                    {R.id.c20, R.id.c21, R.id.c22, R.id.c23, R.id.c24, R.id.c25, R.id.c26, R.id.c27, R.id.c28},

                    {R.id.c30, R.id.c31, R.id.c32, R.id.c33, R.id.c34, R.id.c35, R.id.c36, R.id.c37, R.id.c38},
                    {R.id.c40, R.id.c41, R.id.c42, R.id.c43, R.id.c44, R.id.c45, R.id.c46, R.id.c47, R.id.c48},
                    {R.id.c50, R.id.c51, R.id.c52, R.id.c53, R.id.c54, R.id.c55, R.id.c56, R.id.c57, R.id.c58},

                    {R.id.c60, R.id.c61, R.id.c62, R.id.c63, R.id.c64, R.id.c65, R.id.c66, R.id.c67, R.id.c68},
                    {R.id.c70, R.id.c71, R.id.c72, R.id.c73, R.id.c74, R.id.c75, R.id.c76, R.id.c77, R.id.c78},
                    {R.id.c80, R.id.c81, R.id.c82, R.id.c83, R.id.c84, R.id.c85, R.id.c86, R.id.c87, R.id.c88},};
    Game aGame;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_solveapuzzle);

        Toast toast1 = Toast.makeText(getApplicationContext(), "Input the values of the Sudoku puzzle that you want to solve.", Toast.LENGTH_LONG);
        toast1.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20);//GravityConstant, x, y,
        toast1.show();

        Button solveBtn = (Button) findViewById(R.id.SolveItButton);
        solveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aGame = new Game();

                if (!(fillJavaBoard())) {
                    Toast toast = Toast.makeText(getApplicationContext(), R.string.InvalidValues, Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20);//GravityConstant, x, y,
                    toast.show();

                    toast = Toast.makeText(getApplicationContext(), R.string.InvalidValuesDetails, Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20);//GravityConstant, x, y,
                    toast.show();
                } else {
                    boolean wasSolved = aGame.SolveSudoku(aGame.board);
                    if (wasSolved) {
                        Intent theIntent = new Intent(solveapuzzle.this, ShowSolution.class);
                        theIntent.putExtra("aGame", aGame);
                        startActivity(theIntent);
                    } else {
                        Toast toast = Toast.makeText(getApplicationContext(), R.string.Impossible, Toast.LENGTH_SHORT);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20);//GravityConstant, x, y,
                        toast.show();
                        toast = Toast.makeText(getApplicationContext(), R.string.ImpossibleDetails, Toast.LENGTH_LONG);
                        toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20);//GravityConstant, x, y,
                        toast.show();

                    }
                }
            }
        });
        Button goToMain = (Button) findViewById(R.id.GoToMainMenu);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(solveapuzzle.this, MainActivity.class);
                startActivity(theIntent);
            }
        });

    }


    public boolean fillJavaBoard() {
        EditText entry;
        for (int i = 0; i < N; ++i) {
            for (int j = 0; j < N; ++j) {
                if (ids[i][j] != 0) {
                    entry = (EditText) findViewById(ids[i][j]);
                    if (entry != null) {
                        String s = entry.getText().toString();
                        if (!(s.isEmpty())) {
                            int x = Integer.parseInt(s);
                            if (this.aGame.isSafe(aGame.board, i, j, x)) {
                                this.aGame.board[i][j] = x;
                                this.aGame.original[i][j] = x;
                            } else
                                return false;
                        }
                    }

                }
            }
        }
        return true;
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
        if ( (root instanceof TextView) && (TextWasScaled==false) ) {
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
    public void onWindowFocusChanged(boolean hasFocus){
        super.onWindowFocusChanged(hasWindowFocus());
        scaleContents(findViewById(R.id.contents_SolvePuzzle), (findViewById(R.id.container_SolvePuzzle)));
        }
    //********************************************************
}
