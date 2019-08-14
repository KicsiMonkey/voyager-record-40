package hu.bme.yjzygk.voyagerrecord40.creation;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import hu.bme.yjzygk.voyagerrecord40.MainActivity;
import hu.bme.yjzygk.voyagerrecord40.R;
import hu.bme.yjzygk.voyagerrecord40.creation.list.RecordImage;
import hu.bme.yjzygk.voyagerrecord40.creation.list.RecordImageAdapter;
import hu.bme.yjzygk.voyagerrecord40.creation.list.RecordImageListLoader;
import hu.bme.yjzygk.voyagerrecord40.util.ImmersiveModeSwitcher;
import hu.bme.yjzygk.voyagerrecord40.util.LayoutAsImageSharer;

public class CreationActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 1;
    private static boolean isStorageAccessPermitted = false;
    private static final String TAG = "CreationActivity";
    private Button backButton;
    private ImageButton shareButton;
    private ImageButton saveButton;
//    private RelativeLayout loadingSpinner;
    private Button newButton;
    private RecyclerView recyclerView;
    private RelativeLayout canvasLayout;
    private RecordImageAdapter adapter;
    private ArrayList<RecordImage> imageList;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ImmersiveModeSwitcher.set(this);
        setContentView(R.layout.activity_creation);

        backButton = (Button) findViewById(R.id.back_button);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        shareButton = (ImageButton) findViewById(R.id.share_button);
        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                shareButton.setVisibility(View.GONE);
//                loadingSpinner.setVisibility(View.VISIBLE);
                onShareButtonClick();
            }
        });

        saveButton = (ImageButton) findViewById(R.id.save_button);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onSaveButtonClick();
            }
        });

//        loadingSpinner = (RelativeLayout) findViewById(R.id.loading_spinner);

        newButton = (Button) findViewById(R.id.new_button);
        newButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                restartActivity();
            }
        });

        recyclerView = (RecyclerView) findViewById(R.id.creation_list);
        canvasLayout = (RelativeLayout) findViewById(R.id.creation_canvas);
        initRecyclerView();
    }

    public void onShareButtonClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                LayoutAsImageSharer.shareLayout(this, canvasLayout);
            } else {
                requestStoragePermission(); // Code for permission
            }
        }
        else
        {
            // Code for Below 23 API Oriented Device
            // Do next code
            LayoutAsImageSharer.shareLayout(this, canvasLayout);
        }
    }

    public void onSaveButtonClick() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (checkStoragePermission()) {
                LayoutAsImageSharer.saveLayout(this, canvasLayout);
            } else {
                requestStoragePermission(); // Code for permission
            }
        }
        else
        {
            // Code for Below 23 API Oriented Device
            // Do next code
            LayoutAsImageSharer.saveLayout(this, canvasLayout);
        }
    }

    private boolean checkStoragePermission() {
        int result = ContextCompat.checkSelfPermission(CreationActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (result == PackageManager.PERMISSION_GRANTED) {
            return true;
        } else {
            return false;
        }
    }

    private void requestStoragePermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(CreationActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(CreationActivity.this, "Write External Storage permission allows us to do store images. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(CreationActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.e("value", "Permission Granted, Now you can use local drive .");
                } else {
                    Log.e("value", "Permission Denied, You cannot use local drive .");
                }
                break;
        }
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
//        loadingSpinner.setVisibility(View.GONE);
//        shareButton.setVisibility(View.VISIBLE);

    }

    private void startMainActivity() {
        Intent intent = new Intent(CreationActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private void restartActivity() {
        this.recreate();
    }

    private void initRecyclerView() {
        adapter = new RecordImageAdapter(getApplicationContext(), canvasLayout);
        loadImageList();
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
    }

    private void loadImageList() {
        imageList = new ArrayList<RecordImage>();
        try {
            RecordImageListLoader.loadImages(getApplicationContext(), imageList, getString(R.string.assets_file_extension));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Collections.sort(imageList);
        adapter.update(imageList);
    }

}
