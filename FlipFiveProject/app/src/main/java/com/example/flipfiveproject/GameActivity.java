package com.example.flipfiveproject;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.preference.PreferenceManager;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.preference.PreferenceManager;

import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.wajahatkarim3.easyflipview.EasyFlipView;

import java.lang.reflect.Array;
import java.nio.BufferUnderflowException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;


public class GameActivity extends AppCompatActivity {
    private int number_rows = 3;
    private int number_columns = 3;
    private List<CardView> cards = new ArrayList<>();
    private List<EasyFlipView> cells = new ArrayList<>();
    private List<String> initial_state_cells = new ArrayList<>();
    private boolean onResumeRun;
    private TextView viewCurrentScore;
    private TextView viewBestScore;
    private int valueCurrentScore = 0;
    private int valueBestScore;
    private ImageButton buttonRestartGame;
    private ImageButton buttonHint;
    private int rowHint;
    private int columnHint;
    private int counterLastSelectedCell;
    private SharedPreferences sharedPref;
    public static final String KEY_BEST_SCORE = "best_score";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        viewCurrentScore = (TextView) findViewById(R.id.current_score);
        viewBestScore = (TextView) findViewById(R.id.best_score);
        buttonRestartGame = (ImageButton) findViewById(R.id.restart_icon);
        buttonHint = (ImageButton) findViewById(R.id.hint_icon);

        cards.add((CardView) findViewById(R.id.card_row_1_col_1));
        cards.add((CardView) findViewById(R.id.card_row_1_col_2));
        cards.add((CardView) findViewById(R.id.card_row_1_col_3));
        cards.add((CardView) findViewById(R.id.card_row_2_col_1));
        cards.add((CardView) findViewById(R.id.card_row_2_col_2));
        cards.add((CardView) findViewById(R.id.card_row_2_col_3));
        cards.add((CardView) findViewById(R.id.card_row_3_col_1));
        cards.add((CardView) findViewById(R.id.card_row_3_col_2));
        cards.add((CardView) findViewById(R.id.card_row_3_col_3));

        cells.add((EasyFlipView) findViewById(R.id.cell_row_1_col_1));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_1_col_2));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_1_col_3));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_2_col_1));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_2_col_2));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_2_col_3));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_3_col_1));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_3_col_2));
        cells.add((EasyFlipView) findViewById(R.id.cell_row_3_col_3));

        cells.get(0).setOnTouchListener(touchListenerCellRow0Column0);
        cells.get(1).setOnTouchListener(touchListenerCellRow0Column1);
        cells.get(2).setOnTouchListener(touchListenerCellRow0Column2);
        cells.get(3).setOnTouchListener(touchListenerCellRow1Column0);
        cells.get(4).setOnTouchListener(touchListenerCellRow1Column1);
        cells.get(5).setOnTouchListener(touchListenerCellRow1Column2);
        cells.get(6).setOnTouchListener(touchListenerCellRow2Column0);
        cells.get(7).setOnTouchListener(touchListenerCellRow2Column1);
        cells.get(8).setOnTouchListener(touchListenerCellRow2Column2);

        for(int index=0; index<number_rows*number_columns; ++index) {
            cells.get(index).setOnFlipListener(flipListenerCell);
        }

        for(int index=0; index<number_rows*number_columns; ++index) {
            if (Math.random() >= 0.5) {
                initial_state_cells.add("BACK_SIDE");
            } else {
                initial_state_cells.add("FRONT_SIDE");
            }
        }

        buttonRestartGame.setOnClickListener(clickListenerRestartGame);
        buttonHint.setOnClickListener(clickListenerHint);

        sharedPref = PreferenceManager.getDefaultSharedPreferences(GameActivity.this);
        valueBestScore = sharedPref.getInt(GameActivity.KEY_BEST_SCORE, 0);
        viewBestScore.setText(Integer.toString(valueBestScore));

        onResumeRun = false;

    }

    protected  void onResume() {
        super.onResume();
        if (!onResumeRun) {
            Log.d("Status", "onResume called");
            for (int index = 0; index < number_rows * number_columns; ++index) {
                if (initial_state_cells.get(index).equals("BACK_SIDE")) {
                    valueCurrentScore -= 1;
                    Log.d("Status", Integer.toString(valueCurrentScore));
                    cells.get(index).flipTheView(false);
                }
            }
            onResumeRun = true;
        }
    }

    @Override
    public void onSaveInstanceState(Bundle instanceState) {
        instanceState.putString("valueCurrentScore", Integer.toString(valueCurrentScore));
        for(int index=0; index<number_rows*number_columns; ++index) {
            instanceState.putString(String.format(Locale.getDefault(),"flipStateCell%d", index), cells.get(index).getCurrentFlipState().toString());
        }
        super.onSaveInstanceState(instanceState);
    }

    @Override
    public void onRestoreInstanceState(Bundle instanceState) {
        String savedValueCurrentScore = instanceState.getString("valueCurrentScore");
        valueCurrentScore = Integer.parseInt(savedValueCurrentScore);
        for(int index=0; index<number_rows*number_columns; ++index) {
            String old_state_cell = instanceState.getString(String.format(Locale.getDefault(), "flipStateCell%d", index));
            if (!initial_state_cells.get(index).equals(old_state_cell)) {
                initial_state_cells.set(index, old_state_cell);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.activity_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.help:
                Intent helpIntent = new Intent(GameActivity.this, HelpActivity.class);
                startActivity(helpIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    View.OnTouchListener touchListenerCellRow0Column0 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(0).setCardBackgroundColor(Color.WHITE);
                    cards.get(0).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 2;
                    cells.get(0).flipTheView();
                    cells.get(1).flipTheView();
                    cells.get(3).flipTheView();
                    counterLastSelectedCell = 3;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow0Column1 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(1).setCardBackgroundColor(Color.WHITE);
                    cards.get(1).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 3;
                    cells.get(1).flipTheView();
                    cells.get(0).flipTheView();
                    cells.get(2).flipTheView();
                    cells.get(4).flipTheView();
                    counterLastSelectedCell = 4;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow0Column2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(2).setCardBackgroundColor(Color.WHITE);
                    cards.get(2).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 2;
                    cells.get(2).flipTheView();
                    cells.get(1).flipTheView();
                    cells.get(5).flipTheView();
                    counterLastSelectedCell = 3;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow1Column0 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(3).setCardBackgroundColor(Color.WHITE);
                    cards.get(3).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 3;
                    cells.get(3).flipTheView();
                    cells.get(0).flipTheView();
                    cells.get(4).flipTheView();
                    cells.get(6).flipTheView();
                    counterLastSelectedCell = 4;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow1Column1 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(4).setCardBackgroundColor(Color.WHITE);
                    cards.get(4).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 4;
                    cells.get(4).flipTheView();
                    cells.get(1).flipTheView();
                    cells.get(3).flipTheView();
                    cells.get(5).flipTheView();
                    cells.get(7).flipTheView();
                    counterLastSelectedCell = 5;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow1Column2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(5).setCardBackgroundColor(Color.WHITE);
                    cards.get(5).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 3;
                    cells.get(5).flipTheView();
                    cells.get(2).flipTheView();
                    cells.get(4).flipTheView();
                    cells.get(8).flipTheView();
                    counterLastSelectedCell = 4;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow2Column0 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(6).setCardBackgroundColor(Color.WHITE);
                    cards.get(6).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 2;
                    cells.get(6).flipTheView();
                    cells.get(3).flipTheView();
                    cells.get(7).flipTheView();
                    counterLastSelectedCell = 3;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow2Column1 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(7).setCardBackgroundColor(Color.WHITE);
                    cards.get(7).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 3;
                    cells.get(7).flipTheView();
                    cells.get(6).flipTheView();
                    cells.get(4).flipTheView();
                    cells.get(8).flipTheView();
                    counterLastSelectedCell = 4;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };

    View.OnTouchListener touchListenerCellRow2Column2 = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    cards.get(8).setCardBackgroundColor(Color.WHITE);
                    cards.get(8).setContentPadding(0,0,0,0);
                    valueCurrentScore -= 2;
                    cells.get(8).flipTheView();
                    cells.get(5).flipTheView();
                    cells.get(7).flipTheView();
                    counterLastSelectedCell = 3;
                    break;
                case MotionEvent.ACTION_UP:
                    v.performClick();
                    break;
                default:
                    break;
            }
            return true;
        }
    };


    EasyFlipView.OnFlipAnimationListener flipListenerCell = new EasyFlipView.OnFlipAnimationListener() {
        @Override
        public void onViewFlipCompleted(EasyFlipView flipView, EasyFlipView.FlipState newCurrentSide) {
            valueCurrentScore += 1;
            Log.d("Status", Integer.toString(valueCurrentScore));
            viewCurrentScore.setText(Integer.toString(valueCurrentScore));

            if (isGameFinished("FRONT_SIDE") || isGameFinished("BACK_SIDE")) {
                counterLastSelectedCell -= 1;
                if (counterLastSelectedCell == 0) {

                    if (valueCurrentScore < valueBestScore || valueBestScore == 0) {
                        valueBestScore = valueCurrentScore;
                        viewBestScore.setText(Integer.toString(valueBestScore));
                        SharedPreferences.Editor editor = sharedPref.edit();
                        editor.putInt(GameActivity.KEY_BEST_SCORE, valueBestScore);
                        editor.apply();
                    }

                    AlertDialog.Builder alertFinishedGame = new AlertDialog.Builder(GameActivity.this);
                    alertFinishedGame.setTitle("Game finished!");
                    alertFinishedGame.setMessage(String.format("Current score: %d \n Best score: %d", valueCurrentScore, valueBestScore));
                    alertFinishedGame.setPositiveButton("New game", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            restartGame();
                            Toast toast = Toast.makeText(GameActivity.this, "Game Restarted!", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                    alertFinishedGame.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    alertFinishedGame.create().show();
                }
            }

            Log.d("Status", "am ajuns aici");
        }
    };

    private boolean isGameFinished(String side) {
        for (int index=0; index<9; ++index) {
            if (!cells.get(index).getCurrentFlipState().toString().equals(side)) {
                return false;
            }
        }
        return true;
    }


    View.OnClickListener clickListenerRestartGame = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertRestartGame = new AlertDialog.Builder(GameActivity.this);
            alertRestartGame.setTitle("Restarting the game");
            alertRestartGame.setMessage("Are you sure you want to restart the current game?");
            alertRestartGame.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.this.restartGame();
                    Toast toast = Toast.makeText(GameActivity.this, "Game Restarted!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            alertRestartGame.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast toast = Toast.makeText(GameActivity.this, "Option Quit!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            alertRestartGame.create().show();
        }
    };

    private void restartGame() {
        valueCurrentScore = 0;
        viewCurrentScore.setText("0");
        for (int index = 0; index < number_rows * number_columns; ++index) {
            if (Math.random() >= 0.33) {
                valueCurrentScore -= 1;
                cells.get(index).flipTheView(false);
            }
        }
    }


    View.OnClickListener clickListenerHint = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            AlertDialog.Builder alertHint = new AlertDialog.Builder(GameActivity.this);
            alertHint.setTitle("Getting a hint");
            alertHint.setMessage("You got stuck and don't know how to continue playing this game. Do you want to get a small hint?");
            alertHint.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    GameActivity.this.findHint();
                    AlertDialog.Builder alertHintResponse = new AlertDialog.Builder(GameActivity.this);
                    alertHintResponse.setTitle("Hint");
                    alertHintResponse.setMessage(String.format("Select the cell from row %d and column %d", rowHint+1, columnHint+1));
                    alertHintResponse.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            cards.get(rowHint*number_columns+columnHint).setCardBackgroundColor(Color.parseColor("#D81B60"));
                            cards.get(rowHint*number_columns+columnHint).setContentPadding(5,5,5,5);
                            dialog.cancel();
                        }
                    });
                    alertHintResponse.create().show();
                }
            });
            alertHint.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Toast toast = Toast.makeText(GameActivity.this, "Option Quit!", Toast.LENGTH_SHORT);
                    toast.show();
                }
            });
            alertHint.create().show();
        }
    };

    private void findHint() {
        List<Integer> queueUnseenStateCells = new ArrayList<>();
        List<Integer> queueSeenStateCells = new ArrayList<>();
        rowHint = -1;
        columnHint = -1;

        int userStateCells = 0;
        for (int index = 0; index < number_rows * number_columns; ++index) {
            if (cells.get(index).getCurrentFlipState().toString().equals("BACK_SIDE")) {
                userStateCells |= (1 << index);
            }
        }
        int completeStateCells = 0;

        queueUnseenStateCells.add(completeStateCells);
        boolean foundSolution = false;
        while (queueUnseenStateCells.size() != 0) {
            int currentStateCells = queueUnseenStateCells.get(0);
            queueUnseenStateCells.remove(0);

            if (!queueSeenStateCells.contains(currentStateCells)) {
                queueSeenStateCells.add(currentStateCells);

                for (int row=0; row<number_rows; ++row) {
                    for (int col=0; col<number_columns; ++col) {
                        int newStateCells = getNewStateCells(currentStateCells, row, col);
                        if (!queueSeenStateCells.contains(newStateCells)) {
                            queueUnseenStateCells.add(newStateCells);
                        }
                        if(newStateCells == userStateCells) {
                            rowHint = row;
                            columnHint = col;
                            foundSolution = true;
                            break;
                        }
                    }
                    if (foundSolution) {
                        break;
                    }
                }
            }
            if (foundSolution) {
                break;
            }
        }
    }

    private int getNewStateCells(int stateCells, int row, int column) {
        int position = row * number_columns + column;
        switch (position) {
            case 0:
                stateCells ^= 1 << 0;
                stateCells ^= 1 << 1;
                stateCells ^= 1 << 3;
                break;
            case 1:
                stateCells ^= 1 << 1;
                stateCells ^= 1 << 0;
                stateCells ^= 1 << 2;
                stateCells ^= 1 << 4;
                break;
            case 2:
                stateCells ^= 1 << 2;
                stateCells ^= 1 << 1;
                stateCells ^= 1 << 5;
                break;
            case 3:
                stateCells ^= 1 << 3;
                stateCells ^= 1 << 0;
                stateCells ^= 1 << 4;
                stateCells ^= 1 << 6;
                break;
            case 4:
                stateCells ^= 1 << 4;
                stateCells ^= 1 << 1;
                stateCells ^= 1 << 3;
                stateCells ^= 1 << 5;
                stateCells ^= 1 << 7;
                break;
            case 5:
                stateCells ^= 1 << 5;
                stateCells ^= 1 << 2;
                stateCells ^= 1 << 4;
                stateCells ^= 1 << 8;
                break;
            case 6:
                stateCells ^= 1 << 6;
                stateCells ^= 1 << 3;
                stateCells ^= 1 << 7;
                break;
            case 7:
                stateCells ^= 1 << 7;
                stateCells ^= 1 << 4;
                stateCells ^= 1 << 6;
                stateCells ^= 1 << 8;
                break;
            case 8:
                stateCells ^= 1 << 8;
                stateCells ^= 1 << 5;
                stateCells ^= 1 << 7;
                break;
            default:
                assert true;
        }
        return stateCells;
    }
}
