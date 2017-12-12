package ihm.tse.fr.ihmproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CookieActivity extends AppCompatActivity {


    private TextView cookieLeft;
    private TextView test;
    /*private TextView cookieMiddle;
    private TextView cookieRight;*/

    private List<Integer> colonnesCookie = new ArrayList<>();
    private List<Integer> vitesseCookie = new ArrayList<>();

    private CountDownTimer appearCookie;

    private CountDownTimer countDownTimer;

    private DisplayMetrics metrics = new DisplayMetrics();

    private int vitesseLente;
    private int vitesseMoyenne;
    private int vitesseRapide;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cookie);

        Random r = new Random();
        int valeurMin =0;
        int valeurMax =2;
        for(int i =0; i<20;i++){
            colonnesCookie.add(valeurMin + r.nextInt(valeurMax - valeurMin));
            vitesseCookie.add(valeurMin + r.nextInt(valeurMax - valeurMin));
        }

        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        vitesseLente = metrics.heightPixels / 100;
        vitesseMoyenne = metrics.heightPixels / 60;
        vitesseRapide = metrics.heightPixels / 30;

        cookieLeft = (TextView) findViewById(R.id.cookie1);
        //cookieMiddle = (TextView) findViewById(R.id.cookie2);
        //cookieRight = (TextView) findViewById(R.id.cookie3);

        TextView life = (TextView) findViewById(R.id.life);
        String title = getString(R.string.life).concat(" ").concat(getString(R.string.nbLife));
        life.setText(title);

        RelativeLayout layout = (RelativeLayout) findViewById(R.id.layoutCookie);
        test = new TextView(cookieLeft.getContext());
        test.setText("test");
        /*android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentLeft="true"
        android:layout_alignParentStart="true"
        android:layout_alignParentTop="true"
        android:clickable="true"
        android:text="@string/text"
        app:layout_constraintBottom_toBottomOf="parent"
        app:layout_constraintHorizontal_bias="1.0"
        app:layout_constraintLeft_toLeftOf="parent"
        app:layout_constraintRight_toRightOf="parent"
        app:layout_constraintTop_toTopOf="parent"
        app:layout_constraintVertical_bias="0.029"*/

        initPos(cookieLeft);

        appearCookie = new CountDownTimer(Long.MAX_VALUE, 1000) {

            Integer nb = 1;

            @Override
            public void onTick(long millisUntilFinished) {
                int cookie = colonnesCookie.get(nb);
                int vitesse = vitesseCookie.get(nb);
                initPos(test);
                countDownTimer.start();
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };

        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 500) {

            int nb = 1;

            @Override
            public void onTick(long millisUntilFinished) {
                if (!goDownCookie(cookieLeft, nb)) {
                    nb++;
                } else {
                    TextView life = (TextView) findViewById(R.id.life);
                    Integer nbLife = Integer.parseInt(getString(R.string.nbLife)) - 1;
                    if(nbLife >= 0){
                        String title = getString(R.string.life).concat(" ").concat(nbLife.toString());
                        life.setText(title);
                    }
                }

            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };

        appearCookie.start();

        cookieLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent();
                if (parent == null) {
                    return;
                }
                TextView textView = ((View) parent.getParent()).findViewById(R.id.counter);
                Integer counter = Integer.parseInt(textView.getText().toString()) + 1;
                textView.setText(counter.toString());
                v.setVisibility(View.GONE);
            }
        });

        /*cookieRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent();
                if (parent == null) {
                    return;
                }
                TextView textView = ((View) parent.getParent()).findViewById(R.id.counter);
                Integer counter = Integer.parseInt(textView.getText().toString()) + 1;
                textView.setText(counter.toString());
                v.setVisibility(View.GONE);
            }
        });

        cookieMiddle.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View parent = (View) v.getParent();
                if (parent == null) {
                    return;
                }
                TextView textView = ((View) parent.getParent()).findViewById(R.id.counter);
                Integer counter = Integer.parseInt(textView.getText().toString()) + 1;
                textView.setText(counter.toString());
                v.setVisibility(View.GONE);
            }
        });*/

    }

    private boolean goDownCookie(TextView cookie, int nb) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();

        if (nb * vitesseLente >= metrics.heightPixels) {
            return true;
        }
        params.topMargin = nb * vitesseLente;
        cookie.setLayoutParams(params);
        return false;
    }

    private void initPos(TextView cookie) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();

        Random r = new Random();
        int valeurMin = params.width;
        int valeurMax = 0;
        params.leftMargin = valeurMin + r.nextInt(valeurMax - valeurMin);
        cookie.setLayoutParams(params);
    }
}
