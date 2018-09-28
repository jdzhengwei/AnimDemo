package com.example.animdemo;

import java.util.Random;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.TypeEvaluator;
import android.animation.ValueAnimator;
import android.animation.ValueAnimator.AnimatorUpdateListener;
import android.app.Activity;
import android.graphics.PointF;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class GoldAnimActivity extends Activity {

	private Button btn;
	private RelativeLayout boot_layout;
	private ImageView wallet;
	private Random random = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_goldanim);
		Utils.countDevicePixels(this);
		
		boot_layout = (RelativeLayout) findViewById(R.id.boot_layout);
		btn = (Button) findViewById(R.id.btn);
		wallet = (ImageView) findViewById(R.id.iv_wallet);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				startShakeAnim();
				for (int i = 0; i < 100; i++){
					ImageView imgGold = new ImageView(GoldAnimActivity.this);
					imgGold.setBackgroundResource(random.nextBoolean() ? R.drawable.gold_1 : R.drawable.gold_2);
					imgGold.setVisibility(View.INVISIBLE);
					boot_layout.addView(imgGold);
					startParabolaAnim(imgGold, i);
				}
			}
		});
	}

	private int count = 0;
	private void startShakeAnim() {
		final Animation animation1 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_1);
		final Animation animation2 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_2);
		final Animation animation3 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_3);
		final Animation animation4 = AnimationUtils.loadAnimation(this, R.anim.anim_rotate_4);
		wallet.startAnimation(animation1);
		animation1.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				wallet.startAnimation(animation2);
			}
		});
		animation2.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				wallet.startAnimation(animation3);
			}
		});
		animation3.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				count++;
				if (count == 5){
					count = 0;
					wallet.startAnimation(animation4);
				} else {					
					wallet.startAnimation(animation2);
				}
			}
		});
	}

	private void startParabolaAnim(final View view, int index) {
		final float x = wallet.getX() + Utils.dp2px(30, this);
		final float y = wallet.getY() + Utils.dp2px(30, this);
		final int maxHeight = random.nextInt(300) + 500;
		final float xRandomFrac = random.nextFloat() * 3;
		final int xRandom = random.nextInt(200) + 100;
		final boolean isLeft = random.nextBoolean();
		
		ValueAnimator valueAnimator = new ValueAnimator();
		valueAnimator.setDuration(2000);
		valueAnimator.setObjectValues(new PointF(0, 0));
		valueAnimator.setInterpolator(new LinearInterpolator());
		valueAnimator.setEvaluator(new TypeEvaluator<PointF>() {

			@Override
			public PointF evaluate(float fraction, PointF startValue,
					PointF endValue) {
				PointF point = new PointF();
                if (isLeft){  
                	point.x = x - xRandom * fraction * xRandomFrac;
                } else {
                	point.x = x + xRandom * fraction * xRandomFrac;
                }
                if (fraction < 0.3){                	
                	point.y = y + 0.5f * 200 * (fraction * 3) * (fraction * 3) + (-fraction) * Utils.dp2px(maxHeight, GoldAnimActivity.this);
                } else {
                	view.bringToFront();
                	point.y = y + 0.5f * 200 * (fraction * 3) * (fraction * 3) + (fraction - 0.6f) * Utils.dp2px(maxHeight, GoldAnimActivity.this);
                }
                return point;
			}
		});
		valueAnimator.setStartDelay(index * 20);
		valueAnimator.start();  
        valueAnimator.addUpdateListener(new AnimatorUpdateListener() {  
            @Override  
            public void onAnimationUpdate(ValueAnimator animation)  
            {  
            	if (animation.getAnimatedFraction() > 0.05){
            		view.setVisibility(View.VISIBLE);
            	}
                PointF point = (PointF) animation.getAnimatedValue();
                view.setX(point.x);  
                view.setY(point.y);  
            }  
        });  
        valueAnimator.addListener(new AnimatorListenerAdapter() {
        	
			@Override
			public void onAnimationEnd(Animator animation) {
				boot_layout.removeView(view);
			}
        	
		});
	}
}
