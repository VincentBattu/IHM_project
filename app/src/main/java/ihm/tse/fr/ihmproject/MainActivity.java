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

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private TextView cookie;

    private CountDownTimer countDownTimer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        cookie = (TextView) findViewById(R.id.test);
        initPoscookie();

       countDownTimer = new CountDownTimer(Long.MAX_VALUE, 25) {

            @Override
            public void onTick(long millisUntilFinished) {
                //mooveCookie();
            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        }.start();

        cookie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                if (parent == null){
                    return;
                }
                TextView textView = parent.findViewById(R.id.counter);
                Integer counter = Integer.parseInt(textView.getText().toString()) + 1;
                textView.setText(counter.toString());
                mooveCookie();
            }
        });

    }

    private void mooveCookie(){

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        params.leftMargin = cookie.getWidth() + new Random().nextInt(metrics.widthPixels - 2*cookie.getWidth());
        params.rightMargin = cookie.getWidth() + new Random().nextInt(metrics.widthPixels - 2*cookie.getWidth());
        params.topMargin = cookie.getHeight() + new Random().nextInt(metrics.heightPixels - 3*cookie.getHeight());
        params.bottomMargin = cookie.getHeight() + new Random().nextInt(metrics.heightPixels - 3*cookie.getHeight());
        cookie.setLayoutParams(params);

    }

    private void initPoscookie(){

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        params.leftMargin = cookie.getWidth() + new Random().nextInt(metrics.widthPixels - 2*cookie.getWidth());
        params.bottomMargin = metrics.heightPixels - 2 * cookie.getHeight();
        cookie.setLayoutParams(params);

    }
}
