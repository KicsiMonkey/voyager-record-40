package hu.bme.yjzygk.voyagerrecord40.intro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import hu.bme.yjzygk.voyagerrecord40.MainActivity;
import hu.bme.yjzygk.voyagerrecord40.R;
import hu.bme.yjzygk.voyagerrecord40.util.FirstLaunchSharedPrefManager;
import hu.bme.yjzygk.voyagerrecord40.util.ImmersiveModeSwitcher;

public class IntroActivity extends AppCompatActivity {
    private FirstLaunchSharedPrefManager firstLaunchSharedPrefManager;
    private ViewPager viewPager;
    private IntroPagerAdapter introPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private Button btnSkip, btnNext;
    private int currentPageIdx;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeSwitcher.set(this);
        firstLaunchSharedPrefManager = new FirstLaunchSharedPrefManager(this);
        if (!firstLaunchSharedPrefManager.isFirstLaunch()) {
            startMainActivity();
        }
        setContentView(R.layout.activity_intro);

        viewPager = (ViewPager) findViewById(R.id.vpIntro);
        introPagerAdapter = new IntroPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(introPagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                refreshDots(position);
                if (position == introPagerAdapter.getCount()-1) {
                    btnNext.setText(getString(R.string.start));
                    btnSkip.setVisibility(View.GONE);
                } else {
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        btnSkip = (Button) findViewById(R.id.btn_skip);
        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });
        btnNext = (Button) findViewById(R.id.btn_next);
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (currentPageIdx == introPagerAdapter.getCount()-1) {
                    startMainActivity();
                } else {
                    viewPager.setCurrentItem(currentPageIdx+1);
                }
            }
        });

        refreshDots(0);
    }

    @Override
    protected void onStart() {
        super.onStart();
        ImmersiveModeSwitcher.set(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ImmersiveModeSwitcher.set(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ImmersiveModeSwitcher.set(this);
    }

    private void startMainActivity() {
        firstLaunchSharedPrefManager.setFirstLaunch(false);
        Intent intent = new Intent(IntroActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void refreshDots(int newCurrentPageIdx) {
        currentPageIdx = newCurrentPageIdx;
        dots = new TextView[introPagerAdapter.getCount()];

        int colorActive = getResources().getIntArray(R.array.array_dot_active)[newCurrentPageIdx];
        int colorInactive = getResources().getIntArray(R.array.array_dot_inactive)[newCurrentPageIdx];

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; ++i) {
            dots[i] = new TextView(this);
            dots[i].setText("•");
            dots[i].setTextSize(35);
            dots[i].setTextColor( newCurrentPageIdx==i ? colorActive : colorInactive);
            dotsLayout.addView(dots[i]);
        }
    }


}
