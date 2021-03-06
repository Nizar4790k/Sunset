package com.example.sunset;

import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;

import androidx.fragment.app.Fragment;

public class SunsetFragment extends Fragment {


    private View mSceneView;
    private View mSunView;
    private View mSkyView;

    private int mBlueSkyColor;
    private int mSunsetSkyColor;
    private int mNightSkyColor;

    public static SunsetFragment newInstance(){
        return new SunsetFragment();
    }


    private boolean isNigth=false;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sunset, container, false);

        mSceneView = view;
        mSunView = view.findViewById(R.id.sun);
        mSkyView = view.findViewById(R.id.sky);


        Resources resources = getResources();
        mBlueSkyColor = resources.getColor(R.color.blue_sky);
        mSunsetSkyColor = resources.getColor(R.color.sunset_sky);
        mNightSkyColor = resources.getColor(R.color.night_sky);

        mSceneView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!isNigth){
                    startSunSet();
                    isNigth=true;
                }else{
                    startSunRise();
                    isNigth=false;
                }

            }
        });


        return view;
    }


    private void startSunSet() {
        float sunYStart = mSunView.getTop();
        float sunYEnd = mSkyView.getHeight();

        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator sunsetSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mBlueSkyColor, mSunsetSkyColor)
                .setDuration(3000);
        sunsetSkyAnimator.setEvaluator(new ArgbEvaluator());


        ObjectAnimator nightSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mSunsetSkyColor, mNightSkyColor)
                .setDuration(1500);

        nightSkyAnimator.setEvaluator(new ArgbEvaluator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).with(sunsetSkyAnimator).
                before(nightSkyAnimator);
        animatorSet.start();


    }

    private void startSunRise(){

        float sunYStart = mSkyView.getHeight();
        float sunYEnd =  mSunView.getTop();


        ObjectAnimator heightAnimator = ObjectAnimator
                .ofFloat(mSunView, "y", sunYStart, sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());


        ObjectAnimator heightAnimator2 = ObjectAnimator
                .ofFloat(mSunView, "y", -sunYStart, -sunYEnd).setDuration(3000);
        heightAnimator.setInterpolator(new AccelerateInterpolator());

        ObjectAnimator sunriseSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor",  mSunsetSkyColor,mBlueSkyColor)
                .setDuration(3000);
        sunriseSkyAnimator.setEvaluator(new ArgbEvaluator());

        heightAnimator.setRepeatCount(3);


        /*
        ObjectAnimator morningSkyAnimator = ObjectAnimator
                .ofInt(mSkyView, "backgroundColor", mNightSkyColor,mSunsetSkyColor)
                .setDuration(1500);


         */
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(heightAnimator).
                before(sunriseSkyAnimator);
        animatorSet.start();

        AnimatorSet animatorSet1 = new AnimatorSet();
        animatorSet1.play(heightAnimator2);


    }



}
