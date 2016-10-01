package com.apppartner.androidprogrammertest;

import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
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
                view.setY(event.getRawY() + dY);
                view.setX(event.getRawX() + dX);

                lastAction = MotionEvent.ACTION_MOVE;
                break;

            case MotionEvent.ACTION_UP:
                if (lastAction == MotionEvent.ACTION_DOWN) {
                    animate(imageView);
                    animationButton.setEnabled(false);
                }
                break;

            default:
                return false;
        }
        return true;
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
                animationButton.setEnabled(true);
                img.setVisibility(visibility);
            }
            public void onAnimationRepeat(Animation animation) {}
            public void onAnimationStart(Animation animation) {}
        });

        img.startAnimation(fadeOut);
    }
}
