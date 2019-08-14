package hu.bme.yjzygk.voyagerrecord40.gallery;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import java.io.File;
import java.util.ArrayList;

import hu.bme.yjzygk.voyagerrecord40.MainActivity;
import hu.bme.yjzygk.voyagerrecord40.R;
import hu.bme.yjzygk.voyagerrecord40.util.ImmersiveModeSwitcher;

public class GalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeSwitcher.set(this);
        setContentView(R.layout.activity_gallery);

        Button backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.image_gallery);
        recyclerView.setHasFixedSize(true);

        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),8);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<String> paths = prepareData();

        ImageView zoomedPic = (ImageView) findViewById(R.id.gallery_zoomed_pic);
        LinearLayout backDrop = (LinearLayout) findViewById(R.id.gallery_back_drop);

        GalleryImageAdapter adapter = new GalleryImageAdapter(getApplicationContext(), paths, zoomedPic, backDrop);
        recyclerView.setAdapter(adapter);
    }

    private void startMainActivity() {
        Intent intent = new Intent(GalleryActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
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

    private ArrayList<String> prepareData() {
        File folder = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "Golden Record");
        File[] files = folder.listFiles();
        ArrayList<String> filePaths = new ArrayList<String>();
        for (File file : files) {
            filePaths.add(file.getAbsolutePath());
        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            filePaths.sort(new Comparator<String>() {
//                @Override
//                public int compare(String o1, String o2) {
//                    String[] tokens1 = o1.split("_"); // sort by date maybe TODO
//                    return 0;
//                }
//            });
//        }

        return filePaths;
    }
}
