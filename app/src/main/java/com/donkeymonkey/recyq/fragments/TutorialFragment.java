package com.donkeymonkey.recyq.fragments;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.donkeymonkey.recyq.R;
import com.donkeymonkey.recyq.Utils.OnSwipeTouchListener;
import com.donkeymonkey.recyq.activities.MainActivity;

import java.util.ArrayList;
import java.util.List;

public class TutorialFragment extends Fragment {

    private LinearLayout mOldLinearLayout;
    private LinearLayout mNewLinearLayout;

    private TextView mOldTitle;
    private TextView mNewTitle;

    private TextView mStepCounter;
    private  TextView mText;

    private String[] mStepArray;
    private String[] mTitleArray;
    private String[] mTextArray;
    private List<Integer> mColorArray = new ArrayList();

    private Typeface pt_mono_font;
    private Typeface volvobroad_font;

    public static TutorialFragment newInstance() {
        return new TutorialFragment();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = (View) inflater.inflate(R.layout.fragment_tutorial, container, false);

        mStepCounter = (TextView) view.findViewById(R.id.tutorial_steps);
        mOldTitle = (TextView) view.findViewById(R.id.tutorial_title_1);
        mNewTitle = (TextView) view.findViewById(R.id.tutorial_title_2);
        mText = (TextView) view.findViewById(R.id.tutorial_text);
        final LinearLayout pageControlView = (LinearLayout) view.findViewById(R.id.tutorial_linearlayout_pagecontrol);
        final Button button = (Button) view.findViewById(R.id.tutorial_button);

        mOldLinearLayout = (LinearLayout) view.findViewById(R.id.tutorial_linearlayout_1);
        mNewLinearLayout = (LinearLayout) view.findViewById(R.id.tutorial_linearlayout_2);

        ImageView page1 = (ImageView) view.findViewById(R.id.tutorial_page_1);
        ImageView page2 = (ImageView) view.findViewById(R.id.tutorial_page_2);
        ImageView page3 = (ImageView) view.findViewById(R.id.tutorial_page_3);
        ImageView page4 = (ImageView) view.findViewById(R.id.tutorial_page_4);

        final List<ImageView> pagecontrol = new ArrayList<>();
        pagecontrol.add(page1);
        pagecontrol.add(page2);
        pagecontrol.add(page3);
        pagecontrol.add(page4);

        mColorArray.add(ContextCompat.getColor(getContext(), R.color.colorGreen));
        mColorArray.add(ContextCompat.getColor(getContext(), R.color.colorBlue));
        mColorArray.add(ContextCompat.getColor(getContext(), R.color.colorYellow));
        mColorArray.add(ContextCompat.getColor(getContext(), R.color.colorRed));

        // Setting fonts
        pt_mono_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/pt_mono.ttf");
        volvobroad_font = Typeface.createFromAsset(getContext().getAssets(), "fonts/volvobroad.ttf");

        mStepCounter.setTypeface(pt_mono_font);
        mOldTitle.setTypeface(volvobroad_font);
        mNewTitle.setTypeface(volvobroad_font);
        mText.setTypeface(pt_mono_font);
        button.setTypeface(volvobroad_font);

        button.setVisibility(View.GONE);
        pageControlView.setVisibility(View.VISIBLE);

        page1.setAlpha(1.0f);
        page2.setAlpha(0.3f);
        page3.setAlpha(0.3f);
        page4.setAlpha(0.3f);

        mStepArray = getResources().getStringArray(R.array.tutorial_steps);
        mTitleArray = getResources().getStringArray(R.array.tutorial_titles);
        mTextArray = getResources().getStringArray(R.array.tutorial_texts);

        mStepCounter.setText(mStepArray[0]);
        mOldTitle.setText(mTitleArray[0]);
        mText.setText(mTextArray[0]);

        mOldLinearLayout.setOnTouchListener(new OnSwipeTouchListener(getContext()) {
            int index = 0;

            public void onSwipeRight() {
                if (index > 0) {
                    index = index - 1;

                    mStepCounter.setText(mStepArray[index]);
                    mText.setText(mTextArray[index]);

                    button.setVisibility(View.GONE);
                    pageControlView.setVisibility(View.VISIBLE);

                    for (int j = 0; j < pagecontrol.size(); j++) {
                        if (j == index) {
                            pagecontrol.get(index).setAlpha(1.0f);
                        } else {
                            pagecontrol.get(index).setAlpha(0.3f);
                        }
                    }

                    slideView(index, false);

                    mNewTitle.setTypeface(volvobroad_font);

                }
            }
            public void onSwipeLeft() {
                if (index < 3) {
                    index = index + 1;

                    mStepCounter.setText(mStepArray[index]);
                    mText.setText(mTextArray[index]);

                    if (index == 3) {
                        button.setVisibility(View.VISIBLE);
                        pageControlView.setVisibility(View.GONE);
                    } else {
                        button.setVisibility(View.GONE);
                        pageControlView.setVisibility(View.VISIBLE);
                    }

                    for (int j = 0; j < pagecontrol.size(); j++) {
                        if (j == index) {
                            pagecontrol.get(index).setAlpha(1.0f);
                        } else {
                            pagecontrol.get(index).setAlpha(0.3f);
                        }
                    }

                    slideView(index, true);

                    mNewTitle.setTypeface(volvobroad_font);

                }
            }

        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }

    public void slideView(final int index, final Boolean isSwipeLeft) {

        TranslateAnimation animateOldView;
        TranslateAnimation animateNewView;

        mOldTitle.setVisibility(View.GONE);

        mOldLinearLayout.setX(0);
        mNewLinearLayout.setX(0);

        if (isSwipeLeft) {
            animateOldView = new TranslateAnimation(0, mOldLinearLayout.getWidth()*-1, 0, 0);

            mOldLinearLayout.setBackgroundColor(mColorArray.get(index-1));
            mNewLinearLayout.setBackgroundColor(mColorArray.get(index));
        } else {
            animateOldView = new TranslateAnimation(0, mOldLinearLayout.getWidth(), 0, 0);

            mOldLinearLayout.setBackgroundColor(mColorArray.get(index+1));
            mNewLinearLayout.setBackgroundColor(mColorArray.get(index));
        }

        animateOldView.setDuration(500);

        animateOldView.setFillAfter(true);

        animateOldView.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                mNewTitle.setVisibility(View.GONE);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mNewTitle.setText(mTitleArray[index]);
                mNewTitle.setTypeface(volvobroad_font);
                mNewTitle.setTextSize(35);
                mNewTitle.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        mOldLinearLayout.startAnimation(animateOldView);

        mOldLinearLayout.setVisibility(View.VISIBLE); // Change visibility VISIBLE or GONE
        mNewLinearLayout.setVisibility(View.VISIBLE); // Change visibility VISIBLE or GONE
    }

}
