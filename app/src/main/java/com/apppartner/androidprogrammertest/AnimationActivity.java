package com.apppartner.androidprogrammertest;

import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.apppartner.androidprogrammertest.Utils.Font;


public class AnimationActivity extends BaseAppActivity implements View.OnTouchListener
{
    private float dX;
    private float dY;
    private int lastAction;
    private Button animationButton;
    private ImageView imageView;
    int visibility;
    int from = 0;
    int to = 0;
    TextView animPromt, bonusText;
    private int SCROLL_THREASHOLD;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        bonusText = (TextView) findViewById(R.id.bonus_point_id);
        animPromt = (TextView) findViewById(R.id.anim_prompt_id);

        bonusText.setTypeface(Font.setFont(this, Font.FontType.MachinatoSemiBoldItalic.toString()));
        animPromt.setTypeface(Font.setFont(this, Font.FontType.MachinatoExtraLight.toString()));

        animationButton = (Button) findViewById(R.id.animation_btn_id);

        animationButton.setOnTouchListener(this);

        imageView = (ImageView) findViewById(R.id.fadeImg_id);

        toolbarText.setText("Animation");
        toolbarText.setTypeface(Font.setFont(this, Font.FontType.MachinatoExtraLight.toString()));

        adjustScrollThreashold();
        Log.d("THRESHOLD", ""+SCROLL_THREASHOLD);
    }

    @Override
    protected int getLayoutResource() {
        return R.layout.activity_animation;
    }

    @Override
    public void onBackPressed()
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    @Override
    public boolean onTouch(View view, MotionEvent event) {

        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                dX = view.getX() - event.getRawX();
                dY = view.getY() - event.getRawY();
                lastAction = MotionEvent.ACTION_DOWN;

                break;

            case MotionEvent.ACTION_MOVE:
                if ((Math.abs(dX) > SCROLL_THREASHOLD || Math.abs(dY) > SCROLL_THREASHOLD))
                {
                    view.setY(event.getRawY() + dY);
                    view.setX(event.getRawX() + dX);

                    lastAction = MotionEvent.ACTION_MOVE;

                    break;
                }


            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN) {
                    animate(imageView);
                    animationButton.setEnabled(false);
                    animationButton.setAlpha(0.5f);
                }
                lastAction = MotionEvent.ACTION_UP;
                break;

            default:
                return false;
        }
        return true;
    }

    public void adjustScrollThreashold()
    {
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        int densityDpi = (int)(metrics.density * 160f);

        //LDPI
        if (densityDpi <= 120)
            SCROLL_THREASHOLD = 135;
            //MDPI
        else if (densityDpi <= 160)
            SCROLL_THREASHOLD = 150;
            //TVDPI
        else if (densityDpi <= 213)
            SCROLL_THREASHOLD = 180;
            //HDPI
        else if (densityDpi <= 240)
            SCROLL_THREASHOLD = 190;
            //XHDPI
        else if (densityDpi <= 320)
            SCROLL_THREASHOLD = 220;
            //XXHDPI
        else if (densityDpi <= 480)
            SCROLL_THREASHOLD = 280;
            //XXXHDPI
        else if (densityDpi <= 640)
            SCROLL_THREASHOLD = 500;
            //NO DPI
        else
            SCROLL_THREASHOLD = 200;

    }

    private void animate(final ImageView img) {

        if (img.getVisibility() == View.GONE){
            from = 0;
            to = 1;
            visibility = View.VISIBLE;
        }else{
            from = 1;
            to = 0;
            visibility = View.GONE;
        }

        final Animation fadeOut = new AlphaAnimation(from, to);

        fadeOut.setInterpolator(new AccelerateInterpolator());
        fadeOut.setDuration(1200);

        fadeOut.setAnimationListener(new Animation.AnimationListener()
        {
            public void onAnimationEnd(Animation animation)
            {
                img.setVisibility(visibility);
                animationButton.setEnabled(true);
                animationButton.setAlpha(1);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }

}
