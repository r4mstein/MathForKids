package ua.r4mstein.mathforkids;

import android.app.Dialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.plattysoft.leonids.ParticleSystem;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private static final int ID_NUMBER = 0;

    private TextView mMathExpressionTextView;
    private TextView mRightAnswers;
    private TextView mWrongAnswers;
    private Button mButton0;
    private Button mButton1;
    private Button mButton2;
    private Button mButton3;
    private ImageView mEmoIconImageView;

    final CharSequence[] mDigits = {"2", "3", "4", "5", "6", "7", "8", "9", "All"};
    private ArrayList<Integer> mAnswers = new ArrayList<>();
    private int mLocationOfCorrectAnswer;
    private int mCorrectAnswer;
    private int mIncorrectAnswer;
    private int mRightScore = 0;
    private int mWrongScore = 0;
    private Random mRandom = new Random();
    private String mMathExpression;
    private boolean mChooseDigit = false;
    private int a;
    private int b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mMathExpressionTextView = (TextView) findViewById(R.id.mathExpressionTextView);
        mRightAnswers = (TextView) findViewById(R.id.rightAnswersTextView);
        mWrongAnswers = (TextView) findViewById(R.id.wrongAnswersTextView);
        mEmoIconImageView = (ImageView) findViewById(R.id.emoIcon);
        mButton0 = (Button) findViewById(R.id.button0);
        mButton1 = (Button) findViewById(R.id.button1);
        mButton2 = (Button) findViewById(R.id.button2);
        mButton3 = (Button) findViewById(R.id.button3);

        generateMultiplyQuestion();

    }

    public void chooseAnswers(View view) {
        switch (view.getId()) {
            case R.id.button0:
                setRightOrWrongLabels(mButton0.getText().toString());
                break;
            case R.id.button1:
                setRightOrWrongLabels(mButton1.getText().toString());
                break;
            case R.id.button2:
                setRightOrWrongLabels(mButton2.getText().toString());
                break;
            case R.id.button3:
                setRightOrWrongLabels(mButton3.getText().toString());
                break;
        }

    }

    private void generateMultiplyQuestion() {
        mMathExpression = "multiply";
        a = mRandom.nextInt(11);
        b = mRandom.nextInt(11 - a);
        mCorrectAnswer = a + b;
        mMathExpressionTextView.setText(Integer.toString(a) + " + " + Integer.toString(b) + " = ?");
        generateAnswersForQuestions();
    }

    private void generateSubtractionQuestion() {
        mMathExpression = "subtraction";
        a = mRandom.nextInt(11);
        b = mRandom.nextInt(a + 1);
        mCorrectAnswer = a - b;
        mMathExpressionTextView.setText(Integer.toString(a) + " - " + Integer.toString(b) + " = ?");
        generateAnswersForQuestions();
    }

    private void generateMultiplicationQuestion() {
        if (mChooseDigit == false) {
            a = mRandom.nextInt(10);
            while (a == 0 || a == 1) {
                a = mRandom.nextInt(10);
            }
        }
        b = mRandom.nextInt(10);
        mCorrectAnswer = a * b;
        mMathExpressionTextView.setText(Integer.toString(a) + " * " + Integer.toString(b) + " = ?");
        generateAnswersForQuestions();
    }

    private void generateDivisionQuestion() {
        if (mChooseDigit == false) {
            a = mRandom.nextInt(10);
            while (a == 0) {
                a = mRandom.nextInt(10);
            }
            int c = mRandom.nextInt(10);
            while (c == 0) {
                c = mRandom.nextInt(10);
            }
            b = a * c;
        }
        b = mRandom.nextInt(10 * a);
        while (b == 0 || b % a != 0){
            b = mRandom.nextInt(10 * a);
        }
        mCorrectAnswer = b / a;
        mMathExpressionTextView.setText(Integer.toString(b) + " : " + Integer.toString(a) + " = ?");
        generateAnswersForQuestions();
    }

    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case ID_NUMBER:
                AlertDialog.Builder builder = new AlertDialog.Builder(this, R.style.MyAlertDialogStyle);
                builder.setTitle("Choose the number:")
                        .setItems(mDigits, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if (mDigits[i].equals("All")) {
                                    mChooseDigit = false;
                                    if (mMathExpression.equals("multiplication")) {
                                        generateMultiplicationQuestion();
                                    } else if (mMathExpression.equals("division")) {
                                        generateDivisionQuestion();
                                    }
                                } else {
                                    mChooseDigit = true;
                                    a = Integer.parseInt(mDigits[i].toString());
                                    if (mMathExpression.equals("multiplication")) {
                                        generateMultiplicationQuestion();
                                    } else if (mMathExpression.equals("division")) {
                                        generateDivisionQuestion();
                                    }
                                }
                            }
                        })
                        .setCancelable(false);
                return builder.create();
            default:
                return null;
        }
    }

    private void generateAnswersForQuestions() {
        mLocationOfCorrectAnswer = mRandom.nextInt(4);
        mAnswers.clear();

        for (int i = 0; i < 4; i++) {
            if (i == mLocationOfCorrectAnswer) {
                mAnswers.add(mCorrectAnswer);
            } else {
                if (mMathExpression.equals("multiply") || mMathExpression.equals("subtraction")) {
                    mIncorrectAnswer = mRandom.nextInt(11);
                } else if (mMathExpression.equals("multiplication") || mMathExpression.equals("division")) {
                    mIncorrectAnswer = mRandom.nextInt(a * 10);
                }

                while (mIncorrectAnswer == mCorrectAnswer) {
                    mIncorrectAnswer = mRandom.nextInt(11);
                }
                mAnswers.add(mIncorrectAnswer);
            }
        }

        mButton0.setText(Integer.toString(mAnswers.get(0)));
        mButton1.setText(Integer.toString(mAnswers.get(1)));
        mButton2.setText(Integer.toString(mAnswers.get(2)));
        mButton3.setText(Integer.toString(mAnswers.get(3)));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_multiply) {
            generateMultiplyQuestion();
            clearData();
            return true;
        } else if (id == R.id.action_subtraction) {
            generateSubtractionQuestion();
            clearData();
            return true;
        } else if (id == R.id.action_multiplication) {
            mMathExpression = "multiplication";
            clearData();
            showDialog(ID_NUMBER);
            return true;
        } else if (id == R.id.action_division) {
            mMathExpression = "division";
            clearData();
            showDialog(ID_NUMBER);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void setRightOrWrongLabels(String string) {
        if (string.equals(Integer.toString(mCorrectAnswer))) {
            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.aplo);
            player.start();
            mEmoIconImageView.setImageResource(R.drawable.like);
            mRightScore++;
            mRightAnswers.setText("Right Answers: " + mRightScore);

            new ParticleSystem(this, 50, R.drawable.star2v16, 1500)
                    .setSpeedRange(0.2f, 0.5f)
                    .oneShot(mEmoIconImageView, 50);

            switch (mMathExpression) {
                case "multiply":
                    generateMultiplyQuestion();
                    break;
                case "subtraction":
                    generateSubtractionQuestion();
                    break;
                case "multiplication":
                    generateMultiplicationQuestion();
                    break;
                case "division":
                    generateDivisionQuestion();
                    break;
            }

        } else {
            MediaPlayer player = MediaPlayer.create(getApplicationContext(), R.raw.sm);
            player.start();
            mEmoIconImageView.setImageResource(R.drawable.dislike);
            mWrongScore++;
            mWrongAnswers.setText("Wrong Answers: " + mWrongScore);
        }
    }

    private void clearData() {
        mRightScore = 0;
        mWrongScore = 0;
        mRightAnswers.setText(R.string.right_answers);
        mWrongAnswers.setText(R.string.wrong_answers);
    }


}
