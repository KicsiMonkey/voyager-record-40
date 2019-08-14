package hu.bme.yjzygk.voyagerrecord40.intro;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import hu.bme.yjzygk.voyagerrecord40.R;

/**
 * Created by User on 2017.12.12..
 */

public class IntroTwoFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.two_intro, container, false);

        return rootView;
    }
}
