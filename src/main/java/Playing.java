package com.example.javcoz.sudoku;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Layout;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Playing extends AppCompatActivity {
    int gameMode = 0;
    private final int EASY = 1;
    private final int MEDIUM = 2;
    private final int DIFFICULT = 3;
    public boolean TextWasScaled = false;
    String thePuzzle;
    Game theGame = new Game();
    private final int N=9;
    private EditText selectedEditText;


    private final int textBoxes[][]=
            {{R.id.c00,R.id.c01, R.id.c02, R.id.c03,R.id.c04,R.id.c05, R.id.c06,R.id.c07,R.id.c08},
                    {R.id.c10,R.id.c11, R.id.c12, R.id.c13,R.id.c14,R.id.c15, R.id.c16,R.id.c17,R.id.c18},
                    {R.id.c20,R.id.c21, R.id.c22, R.id.c23,R.id.c24,R.id.c25, R.id.c26,R.id.c27,R.id.c28},

                    {R.id.c30,R.id.c31, R.id.c32, R.id.c33,R.id.c34,R.id.c35, R.id.c36,R.id.c37,R.id.c38},
                    {R.id.c40,R.id.c41, R.id.c42, R.id.c43,R.id.c44,R.id.c45, R.id.c46,R.id.c47,R.id.c48},
                    {R.id.c50,R.id.c51, R.id.c52, R.id.c53,R.id.c54,R.id.c55, R.id.c56,R.id.c57,R.id.c58},

                    {R.id.c60,R.id.c61, R.id.c62, R.id.c63,R.id.c64,R.id.c65, R.id.c66,R.id.c67,R.id.c68},
                    {R.id.c70,R.id.c71, R.id.c72, R.id.c73,R.id.c74,R.id.c75, R.id.c76,R.id.c77,R.id.c78},
                    {R.id.c80,R.id.c81, R.id.c82, R.id.c83,R.id.c84,R.id.c85, R.id.c86, R.id.c87, R.id.c88},};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playing);


        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            thePuzzle = extras.getString("THEPUZZLESTRING");
            gameMode = extras.getInt("MODE");
            }
        int r1, r2, g1, g2, b1, b2;

        Button subButton = (Button) findViewById(R.id.SubmitButton);
        switch (gameMode){
            case EASY:
                subButton.setBackgroundResource(R.drawable.bt_submite);
                r1 = 00;
                r2= 102;
                g1=153;
                g2=204;
                b1=00;
                b2=102;
                break;
            case MEDIUM:
                subButton.setBackgroundResource(R.drawable.bt_submitm);
                r1=255;
                r2=255;
                g1=153;
                g2=204;
                b1=0;
                b2=0;
                break;
            case DIFFICULT:
                subButton.setBackgroundResource(R.drawable.bt_submitd);
                r1=239;
                r2=239;
                g1=106;
                g2=141;
                b1=0;
                b2=21;
                break;
            default:
                subButton.setBackgroundResource(R.drawable.bt_submite);
                r1 = 188;
                r2= 255;
                g1=62;
                g2=65;
                b1=22;
                b2=102;
                break;
        }

        theGame.fillBoardFromString(thePuzzle); // Fills both the oiginal and userViewable grids
        theGame.SolveSudoku(theGame.original); //Solves the puzzle in the grid not viewable by the user
        fillUIboard(r1, r2, g1, g2, b1, b2);                         // Fill the board that is visible to the user (sets as uneditable boxes with givens)

        Button goToMain = (Button) findViewById(R.id.GoToMainMenu);
        goToMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent theIntent = new Intent(Playing.this, MainActivity.class);
                startActivity(theIntent);
            }
        });

        Button hintButton = (Button) findViewById(R.id.HintButton);
        hintButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (selectedEditText instanceof EditText) {
                    String thID = selectedEditText.getResources().getResourceName(selectedEditText.getId());
                    int row = Character.getNumericValue(thID.charAt(thID.length() - 2));
                    int col = Character.getNumericValue(thID.charAt(thID.length() - 1));
                    String x = Integer.toString(theGame.original[row][col]);
                    //String s = Integer.toString(row)+Integer.toString(col);
                    //Toast toast = Toast.makeText(getApplicationContext(), s, Toast.LENGTH_SHORT);
                    //toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
                    //toast.show();
                    selectedEditText.setText(x);
                    }
                else {
                    Toast toast = Toast.makeText(getApplicationContext(), "Select a box first to reveal its value!", Toast.LENGTH_SHORT);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
                    toast.show();
                }

            }
        });

        Button solveitButton = (Button) findViewById(R.id.SolveItButton);
        solveitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillMissingNumbers();
                Toast toast = Toast.makeText(getApplicationContext(), "Showing Solution", Toast.LENGTH_SHORT);
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
                toast.show();
            }
        });

        Button submitButton = (Button) findViewById(R.id.SubmitButton);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int result = checkSubmission();
                Toast toast = Toast.makeText(getApplicationContext(), "", Toast.LENGTH_SHORT);

                switch (result){
                    case 0:
                        toast.setText("There is still empty cells in the grid");
                        break;
                    case 1:
                        toast.setText("The submitted configuration is not a valid solution.");
                        break;
                    case 2:
                        toast.setText("Congratulations. You have solved this puzzle.");
                        break;
                }
                toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
                toast.show();
            }
        });

    }




    public void fillUIboard(int r1, int r2, int g1, int g2, int b1, int b2){
        EditText aBox;
        for (int i=0; i<N; ++i)
            for (int j=0; j<N; ++j) {
                if (textBoxes[i][j] != 0) {
                    aBox = (EditText) findViewById(textBoxes[i][j]);
                    aBox.setOnFocusChangeListener(changeListener);
                    if (aBox != null) {
                        if(this.theGame.board[i][j]!=0){ // User-Given Values the first argument 51->00
                            aBox.setBackgroundColor(Color.rgb(r1, g1, b1));
                            aBox.setText(Integer.toString(this.theGame.board[i][j]));
                            aBox.setFocusable(false);
                        }
                        else {                          // Solution Values
                           // aBox.setText(Integer.toString(this.theGame.board[i][j]));
                            aBox.setBackgroundColor(Color.rgb(r2, g2, b2));
                        }
                    }
                }
            }

    }

    public void fillMissingNumbers(){
        EditText aBox;
        for (int i=0; i<N; ++i)
            for (int j=0; j<N; ++j) {
                if (textBoxes[i][j] != 0) {
                    aBox = (EditText) findViewById(textBoxes[i][j]);
                    if (aBox != null) {
                        if(this.theGame.board[i][j]==0){ // User-Given Values
                            aBox.setText(Integer.toString(this.theGame.original[i][j])); // "original" has the solution
                            aBox.setFocusable(false);
                        }
                        else {                          // Solution Values
                            // aBox.setText(Integer.toString(this.theGame.board[i][j]));
                            //aBox.setBackgroundColor(Color.rgb(101, 175, 220));

                        }
                    }
                }
            }

    }

    public int checkSubmission(){  // return: 0=there's empty user cells. 1=Not the solution, 2=correct solution submitted
        EditText aBox;
        for (int i=0; i<N; ++i){
            for (int j=0; j<N; ++j) {
                if (textBoxes[i][j] != 0) {
                    aBox = (EditText) findViewById(textBoxes[i][j]);
                    if (aBox != null) {
                        String s = aBox.getText().toString();
                        if(s.isEmpty()) {
                            return 0;
                            }
                        else if(!s.contentEquals(Integer.toString(theGame.original[i][j]) ) ) {
                            //Toast atoast = Toast.makeText(getApplicationContext(), s+Integer.toString(theGame.original[i][j]), Toast.LENGTH_SHORT);
                            //atoast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
                            //atoast.show();
                            return 1;
                            }
                        }
                    }
                }
            }
        return 2;

    }


    private View.OnFocusChangeListener changeListener= new View.OnFocusChangeListener(){
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus) {
                selectedEditText = (EditText) v;
                }
        }
    };

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
        if (root instanceof TextView) {
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
        scaleContents(findViewById(R.id.contents_playing), (findViewById(R.id.container_playing)));
    }


    // ********************************************************

}




