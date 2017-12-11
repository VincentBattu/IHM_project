package ihm.tse.fr.ihmproject;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

public class CookieActivity extends AppCompatActivity {


    private TextView cookieLeft;
    private TextView cookieMiddle;
    private TextView cookieRight;

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


        getWindowManager().getDefaultDisplay().getMetrics(metrics);
        vitesseLente = metrics.heightPixels / 125;
        vitesseMoyenne = metrics.heightPixels / 60;
        vitesseRapide = metrics.heightPixels / 30;

        cookieLeft = (TextView) findViewById(R.id.cookie1);
        cookieMiddle = (TextView) findViewById(R.id.cookie2);
        cookieRight = (TextView) findViewById(R.id.cookie3);

        TextView life = (TextView) findViewById(R.id.life);
        String title = getString(R.string.life).concat(" ").concat(getString(R.string.nbLife));
        life.setText(title);

        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 500) {

            int nb = 1;

            @Override
            public void onTick(long millisUntilFinished) {
                if (!goDownCookie(cookieLeft, nb)) {
                    nb++;
                } else {
                    TextView life = (TextView) findViewById(R.id.life);
                    Integer nbLife = Integer.parseInt(getString(R.string.nbLife)) - 1;
                    String title = getString(R.string.life).concat(" ").concat(nbLife.toString());
                    life.setText(title);
                }

            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };
        countDownTimer.start();

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
            }
        });

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
}
