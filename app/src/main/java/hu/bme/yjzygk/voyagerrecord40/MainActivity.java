package hu.bme.yjzygk.voyagerrecord40;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import hu.bme.yjzygk.voyagerrecord40.creation.CreationActivity;
import hu.bme.yjzygk.voyagerrecord40.gallery.GalleryActivity;
import hu.bme.yjzygk.voyagerrecord40.intro.IntroActivity;
import hu.bme.yjzygk.voyagerrecord40.util.FirstLaunchSharedPrefManager;
import hu.bme.yjzygk.voyagerrecord40.util.ImmersiveModeSwitcher;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeSwitcher.set(this);
        setContentView(R.layout.activity_main);

        Button btnStart = (Button) findViewById(R.id.btn_start);
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCreationActivity();
            }
        });

        Button btnGallery = (Button) findViewById(R.id.btn_gallery);
        btnGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openGallery();
            }
        });

        Button btnWhatsThis = (Button) findViewById(R.id.btn_what);
        btnWhatsThis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startIntroActivity();
            }
        });
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

    private void startCreationActivity() {
        Intent intent = new Intent(MainActivity.this, CreationActivity.class);
        startActivity(intent);
        finish();
    }

    private void startIntroActivity() {
        FirstLaunchSharedPrefManager firstLaunchSharedPrefManager = new FirstLaunchSharedPrefManager(this);
        firstLaunchSharedPrefManager.setFirstLaunch(true);
        Intent intent = new Intent(MainActivity.this, IntroActivity.class);
        startActivity(intent);
        finish();
    }

    private void openGallery() {
        Intent intent = new Intent(MainActivity.this, GalleryActivity.class);
        startActivity(intent);
        finish();
    }
}
