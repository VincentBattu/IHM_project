package ihm.tse.fr.ihmproject;

import android.animation.ObjectAnimator;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private TextView cookie;

    private CountDownTimer countDownTimer;

    private int vitesse;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.activity_main);

        cookie = (TextView) findViewById(R.id.cookie1);
        initPoscookie();

        TextView life = (TextView) findViewById(R.id.life);
        String title = getString(R.string.life).concat(" ").concat(getString(R.string.nbLife));
        life.setText(title);

        /*ObjectAnimator anim = new ObjectAnimator();
        anim.setDuration(60000);
        anim.setTarget(cookie.getLayoutParams());
        anim.setPropertyName("topMargin");
        anim.setupStartValues();
        anim.setupEndValues();*/

       countDownTimer = new CountDownTimer(Long.MAX_VALUE, 500) {

           int nb=1;
            @Override
            public void onTick(long millisUntilFinished) {
                if(!goDownCookie(nb)){
                    nb++;
                }

            }

            @Override
            public void onFinish() {
                countDownTimer.start();
            }
        };
       countDownTimer.start();

        cookie.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                View parent = (View)v.getParent();
                if (parent == null){
                    return;
                }
                TextView textView = ((View)parent.getParent()).findViewById(R.id.counter);
                Integer counter = Integer.parseInt(textView.getText().toString()) + 1;
                textView.setText(counter.toString());
                //mooveCookie();
            }
        });

    }

    private boolean goDownCookie(int nb){

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //params.topMargin = cookie.getHeight() + new Random().nextInt(metrics.heightPixels - 3*cookie.getHeight());
       //params.bottomMargin = cookie.getHeight() + new Random().nextInt(metrics.heightPixels - 3*cookie.getHeight());

        vitesse = metrics.heightPixels / 125;

        if(nb * vitesse >= metrics.heightPixels){
            return true;
        }
        params.topMargin = metrics.heightPixels - nb * vitesse;
        //params.bottomMargin = metrics.heightPixels - cookie.getHeight() - nb * vitesse;
        cookie.setLayoutParams(params);
        return false;
    }

    private void initPoscookie(){

        ViewGroup.MarginLayoutParams params = (ViewGroup.MarginLayoutParams) cookie.getLayoutParams();
        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        //params.leftMargin = cookie.getWidth() + new Random().nextInt(metrics.widthPixels - 2*cookie.getWidth());
        params.leftMargin = (metrics.widthPixels/4) - 1 * cookie.getWidth();
        params.bottomMargin = metrics.heightPixels - 3 * cookie.getHeight();
        params.topMargin = 3 * cookie.getHeight();
        cookie.setLayoutParams(params);

    }
}
