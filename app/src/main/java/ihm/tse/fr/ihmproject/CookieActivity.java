package ihm.tse.fr.ihmproject;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class CookieActivity extends AppCompatActivity {


    private List<TextView> cookies = new ArrayList<>();

    private CountDownTimer appearCookie;

    private CountDownTimer countDownTimer;

    private DisplayMetrics metrics = new DisplayMetrics();

    private RelativeLayout layout;

    private TextView life;

    private Integer nbLife = 3;

    private int vitesse;

    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cookie);

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        vitesse = metrics.heightPixels / 20;

        life = (TextView) findViewById(R.id.life);
        String title = getString(R.string.life).concat(" ").concat(getString(R.string.nbLife));
        life.setText(title);

        layout = (RelativeLayout) findViewById(R.id.layoutCookie);


        appearCookie = new CountDownTimer(Long.MAX_VALUE, 2000) {

            Integer nb = 1;

            @Override
            public void onTick(long millisUntilFinished) {
                final TextView cookie = new TextView(getApplicationContext());
                cookie.setText("cookie");
                layout.addView(cookie);
                cookies.add(cookie);

                cookie.setOnClickListener(new View.OnClickListener() {

                    @Override
                    public void onClick(View v) {
                        View parent = (View) v.getParent();
                        if (parent == null) {
                            return;
                        }
                        counter++;
                        TextView textView = ((View) parent.getParent()).findViewById(R.id.counter);
                        Integer counter = Integer.parseInt(textView.getText().toString()) + 1;
                        textView.setText(counter.toString());
                        v.setVisibility(View.GONE);
                        cookies.remove(v);
                    }
                });

                initPos(cookie);

                if (nb == 10) {
                    vitesse *= 1.3;
                    nb = 0;
                } else {
                    nb++;
                }
            }

            @Override
            public void onFinish() {
            }
        }.start();

        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 500) {

            @Override
            public void onTick(long millisUntilFinished) {

                Iterator<TextView> i = cookies.iterator();
                while (i.hasNext()){
                    TextView cookie = i.next();
                    float y = cookie.getY();
                    cookie.setY(y + vitesse);

                    if (cookie.getY() >= metrics.heightPixels) {
                        nbLife--;
                        i.remove();
                        String title = getString(R.string.life).concat(" ").concat(nbLife.toString());
                        life.setText(title);
                        if (nbLife <= 0) {
                            appearCookie.cancel();
                            countDownTimer.cancel();
                            BestScore.scores.add(counter);
                            startActivity(new Intent(CookieActivity.this, GameOverActivity.class));
                        }

                    }
                }
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        }.start();


    }


    private void initPos(TextView cookie) {

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Random r = new Random();
        int valeurMax = size.x;
        int x = r.nextInt(valeurMax - 77);
        params.leftMargin = x;
        params.topMargin = 1;

        cookie.setLayoutParams(params);
    }
}
