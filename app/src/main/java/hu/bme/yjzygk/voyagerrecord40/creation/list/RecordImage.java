package hu.bme.yjzygk.voyagerrecord40.creation.list;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.io.IOException;
import java.io.InputStream;
import java.util.Random;

import hu.bme.yjzygk.voyagerrecord40.R;

public class RecordImage implements Comparable<RecordImage> {
    public int id;
    public String name;
    public String category;
    public String subCategory;

    public String imageFilePath = null;
    public int imageId;

    public RecordImage(String name, String category, String subCategory, int drawableId) {
        this.name = name;
        this.category = category;
        this.subCategory = subCategory;
        this.imageId = drawableId;
    }

    public RecordImage(String[] data, String filePath) {
        this.id = Integer.parseInt(data[0], 10);
        this.name = data[1];
        this.category = data[2];
        this.subCategory = data[3];
        this.imageFilePath = filePath;
    }

    public void addAndRemoveAsViewWithFadeOut(final ViewGroup viewGroup) {
        Random r = new Random();
        r.setSeed(System.currentTimeMillis());

        final ImageView imageView = new ImageView(viewGroup.getContext());

        InputStream ims = null;
        try {
            ims = RecordImageListLoader.mngr.open(imageFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //load image as Drawable
        Drawable d = null;
        if (ims != null) {
             d = Drawable.createFromStream(ims, null);
        }
        if (d != null) {
            imageView.setImageDrawable(d);
        }
        if (d==null || ims == null) {
            // load dummy
            imageView.setImageResource(R.drawable.record);
        }

        RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        int width = (int) (1.25f * viewGroup.getContext().getResources().getDimension(R.dimen.canvas_half_width));
        int height = (int) (1.25f * viewGroup.getContext().getResources().getDimension(R.dimen.canvas_half_width));
        if (params != null) {
            params.width = width;
            params.height = height;
        } else {
            imageView.setLayoutParams(new RelativeLayout.LayoutParams(width, height));
            params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
        }
        params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
        params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
        imageView.setLayoutParams(params);
        imageView.setAlpha(0.0f);
        imageView.setTranslationX((int)
                ((6f*r.nextDouble()-3f) *
                viewGroup.getContext().getResources().getDimension(R.dimen.imageview_translation_step)));
        imageView.setTranslationY((int)
                ((6f*r.nextDouble()-3f) *
                viewGroup.getContext().getResources().getDimension(R.dimen.imageview_translation_step)));
        viewGroup.addView(imageView);
        imageView.setVisibility(View.VISIBLE);

        imageView.animate().setDuration(50).alpha(1.0f).setListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                imageView.animate().setListener(null);
                imageView.clearAnimation();
                imageView.animate().setDuration(2000).alpha(0.0f).setListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imageView.animate().setListener(null);
                        imageView.clearAnimation();
                        imageView.setVisibility(View.GONE);
                        viewGroup.removeView(imageView);
                    }
                });
            }
        });
        /**/
    }

    @Override
    public int compareTo(@NonNull RecordImage o) {
        return this.id-o.id;
    }
}
