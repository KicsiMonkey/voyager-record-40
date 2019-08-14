package hu.bme.yjzygk.voyagerrecord40.intro;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

class IntroPagerAdapter extends FragmentPagerAdapter {
    private static final int NUM_PAGES = 3;

    public IntroPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                return new IntroOneFragment();
            case 1:
                return new IntroTwoFragment();
            default:
                return new IntroThreeFragment();
        }
    }

    @Override
    public int getCount() {
        return NUM_PAGES;
    }
}