package com.example.javcoz.sudoku;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.DrawableRes;
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

public class PickPuzzleNum extends AppCompatActivity {
    int gameMode = 0;
    private final int EASY = 1;
    private final int MEDIUM = 2;
    private final int DIFFICULT = 3;

    private String[] easy_puzzles = {
            "379000014060010070080009005435007000090040020000800436900700080040080050850000249",
            "070000810000318902281470005400060000690103027000090006900054681106982000057000040",
            "000020010504018972080409005000000108690103027702000000900704080146980703050030000",
            "080002160000076200605008000006001038040030090850600700000700309002460000037100050",
            "009002060004000200020318970076050400240030096003090720068725040002000800030100600",
            "089002063004076205000308904000200408200030006803004000108705000502460800430100650",
            "780502060000070005020310970900251430041807590053694001068025040500060000030109052",
            "080540160000900205620300070900050038040807090850090001060005049502003000037089050",
            "062100480403080097009070120500090070200804009090020001075030800120040905084005710",
            "062050403003602507000400006500096200030804050008520001900001000106708900304060710"};
    private String[] medium_puzzles = {
            "980160000206000030000804090100005307008000900307200001020908000010000809000071065",
            "306009801000846000002005040503060020008000600090020405060900300000753000709600502",
            "340200050007840090002035006070460009000090000600027080800910300010053900030004012",
            "306009850007040200000005006570460029408090607690027085800900000004050900039600502",
            "210930600000800102000010390000503008720000063800206000093060000608009000002081036",
            "700020530008756400019000070200030060605000902090060003050000820002598300083070009",
            "602001003140706005050200010090300000400000006000008030070003060500902081300400907",
            "602700008040098602090260043060000071000000000410000060820047010104530080900002407",
            "006470002845009000007000800000800903010902080409001000001000600000600391500093400",
            "503080609470960053600001200840090000007000100000020064004600002960043071702010406"};
    private String[] difficult_puzzles = {
            "501740008000000050098600400040961580050000010016854070005006730070000000900072805",
            "001000093297680405000004070000540080000398000030026000010400000903065147560000900",
            "400007000072530900001809000109002803030000040504300109000703500007045360000600004",
            "070904060000000100530012900800100604040805070309006008003260085002000000080301020",
            "300070005010000673007390100000017830700000009048920000002035900195000080800060002",
            "001090500004600800790200030900400003040030090500009006080002014002005300009080200",
            "069004008320089740800002000200050100536000974008040005000400002053210097400700860",
            "004215000000000001050806420680002000207000905000900083078609030900000000000738100",
            "620900007700400060009000010006070054500000003940030700090000100050001006300002078",
            "009003075000079000304502090007040506060000040401030900080204709000810000140300200"};
    String puzzle_string;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pick_puzzle_num);


        Bundle extras = getIntent().getExtras();
        if (extras != null)
            gameMode = extras.getInt("MODE");

        int button_color = 0;
        switch (gameMode){
            case EASY:
                button_color = R.drawable.bt_easynum;
                break;
            case MEDIUM:
                button_color = R.drawable.bt_mednum;
                break;

            case DIFFICULT:
                button_color = R.drawable.bt_difnum;
                break;

            default:

                break;
        }
        for(int x=1; x<=10; ++x) {
            String theID = "b"+Integer.toString(x);
            int id = getResources().getIdentifier(theID, "id", getApplicationContext().getPackageName());
            final Button theButton = (Button) findViewById(id);
            theButton.setBackgroundResource(button_color);
            theButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    Toast toast = Toast.makeText(getApplicationContext(), theButton.getText().toString(), Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER | Gravity.CENTER, 0, 20); //GravityConstant, x, y,
                    toast.show();
                    */
                    int i = Integer.parseInt(theButton.getText().toString());
                    puzzle_string = getPuzzleAt(gameMode, i);
                    Intent theIntent = new Intent(PickPuzzleNum.this, Playing.class);
                    theIntent.putExtra("THEPUZZLESTRING", puzzle_string);
                    theIntent.putExtra("MODE", gameMode);
                    startActivity(theIntent);
                    finish();
                }
            });
        }


    }

    public String getPuzzleAt(int theMode, int puzzleIdx){
        String aString;
        switch (theMode) {
            case EASY:
                aString = easy_puzzles[puzzleIdx-1];
                break;
            case MEDIUM:
                aString = medium_puzzles[puzzleIdx - 1];
                break;
            case DIFFICULT:
                aString = difficult_puzzles[puzzleIdx - 1];
                break;
            default:
                aString = easy_puzzles[puzzleIdx - 1];
                break;
            }
        return aString;
        }

}
