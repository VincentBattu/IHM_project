package ihm.tse.fr.ihmproject;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class CookieActivity extends AppCompatActivity {


    private List<ImageView> cookies = new ArrayList<>();

    private CountDownTimer appearCookie;

    private CountDownTimer countDownTimer;

    private DisplayMetrics metrics = new DisplayMetrics();

    private RelativeLayout layout;

    private TextView life;

    private Integer nbLife = 3;

    private int speed;

    private int counter = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_cookie);

        runOnUiThread(new Runnable() {
            @Override
            public void run() {

                if (!isFinishing()){
                    new AlertDialog.Builder(CookieActivity.this)
                            .setTitle(R.string.titleDialog)
                            .setMessage(R.string.textDialog)
                            .setCancelable(false)
                            .setPositiveButton(R.string.ready, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                    appearCookie.start();
                                }
                            }).show();
                }
            }
        });

        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        speed = metrics.heightPixels / 20;

        life = findViewById(R.id.life);
        String title = getString(R.string.life).concat(" ").concat(getString(R.string.nbLife));
        life.setText(title);

        layout = findViewById(R.id.layoutCookie);


        appearCookie = new CountDownTimer(Long.MAX_VALUE, 2000) {

            Integer nb = 1;

            @Override
            public void onTick(long millisUntilFinished) {
                final ImageView cookie = new ImageView(getApplicationContext());
                cookie.setImageResource(R.drawable.image_cookie);
                /*cookie.setMinimumHeight(32);
                cookie.setMinimumWidth(32);*/
                cookie.setMaxHeight(32);
                cookie.setMaxWidth(32);
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
                    speed *= 1.3;
                    nb = 0;
                } else {
                    nb++;
                }
            }

            @Override
            public void onFinish() {
            }
        };

        countDownTimer = new CountDownTimer(Long.MAX_VALUE, 500) {

            @Override
            public void onTick(long millisUntilFinished) {

                Iterator<ImageView> i = cookies.iterator();
                while (i.hasNext()){
                    ImageView cookie = i.next();
                    float y = cookie.getY();
                    cookie.setY(y + speed);

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


    private void initPos(ImageView cookie) {

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
