package hu.bme.yjzygk.voyagerrecord40.creation.list;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import hu.bme.yjzygk.voyagerrecord40.R;

public class RecordImageAdapter extends RecyclerView.Adapter<RecordImageAdapter.RecordImageViewHolder> {

    private final List<RecordImage> images;
    private List<ImageView> addedImageViews;
    private Context context;
    private ViewGroup canvas;


    public RecordImageAdapter(final Context context, final ViewGroup canvas) {
        this.images = new ArrayList<>();
        this.addedImageViews = new ArrayList<>();
        this.context = context;
        this.canvas = canvas;
    }

    @Override
    public RecordImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View imageRecordView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_record_image_list, parent, false);
        return new RecordImageViewHolder(imageRecordView);
    }

    @Override
    public void onBindViewHolder(final RecordImageViewHolder holder, int position) {
        final RecordImage image = images.get(position);
        holder.imageName.setText(image.name);
        //holder.imagePreview.setImageResource(R.drawable.record);
        holder.imageListRow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Random r = new Random();
                r.setSeed(System.currentTimeMillis());

                ImageView imageView = new ImageView(context);
                ImageView sameSubImageView = null;

                for (int i = addedImageViews.size()-1; i >= 0 && sameSubImageView==null; --i) {
                    ImageView iv = addedImageViews.get(i);
                    if(iv.getContentDescription() != null && image.subCategory != null) {
                        if(image.subCategory.length() > 0 && iv.getContentDescription().equals(image.subCategory)) {
                            sameSubImageView = iv;
                            imageView.setLayoutParams(iv.getLayoutParams());
                            if(r.nextDouble() >= 0.5) {
                                float ty = iv.getTranslationY();
                                int h = imageView.getHeight();
                                if (r.nextDouble() >= 0.5) {//(ty >= 0) {
                                    imageView.setTranslationY((float) (ty + (h / (r.nextDouble()*1.2f + 0.8f))));
                                } else {
                                    imageView.setTranslationY((float) (ty + (h / (r.nextDouble()*1.2f + 0.8f))));
                                }
                                if(r.nextDouble() >= 0.7) {
                                    float tx = iv.getTranslationX();
                                    int w = imageView.getWidth();
                                    if (tx >= 0) {
                                        imageView.setTranslationX((float) (tx - (w / (r.nextDouble() + 1.0f))));
                                    } else {
                                        imageView.setTranslationX((float) (tx + (w / (r.nextDouble() + 1.0f))));
                                    }
                                }
                            } else {
                                float tx = iv.getTranslationX();
                                int w = imageView.getWidth();
                                if( r.nextDouble() >= 0.5) {//(tx >= 0) {
                                    imageView.setTranslationX((float) (tx - (w / (r.nextDouble() + 1.0f))));
                                } else {
                                    imageView.setTranslationX((float) (tx + (w / (r.nextDouble() + 1.0f))));
                                }
                                if (r.nextDouble() >= 0.7) {
                                    float ty = iv.getTranslationY();
                                    int h = imageView.getHeight();
                                    if(ty >= 0) {
                                        imageView.setTranslationY((float) (ty + (h / (r.nextDouble() + 1.0f))));
                                    } else {
                                        imageView.setTranslationY((float) (ty + (h / (r.nextDouble() + 1.0f))));
                                    }
                                }
                            }
                        }
                    }
                }

                if (sameSubImageView == null) {
                    RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) imageView.getLayoutParams();

                    double randomScaleFactor = (r.nextDouble()+0.7);

                    int width = (int) (randomScaleFactor * (double) context.getResources().getDimension(R.dimen.imageview_height));
                    int height = (int) (randomScaleFactor * (double) context.getResources().getDimension(R.dimen.imageview_width));
                    if (params != null) {
                        params.width = width;
                        params.height = height;
                    } else {
                        params = new RelativeLayout.LayoutParams(width, height);
                    }
                    int XTranslationMaxStep =
                            (int)(( context.getResources().getDimension(R.dimen.canvas_half_width)
                                    - (width/2) )
                                    / context.getResources().getDimension(R.dimen.imageview_translation_step));
                    int YTranslationMaxStep =
                            (int)(( context.getResources().getDimension(R.dimen.canvas_half_height)
                                    - (height/2))
                                    / context.getResources().getDimension(R.dimen.imageview_translation_step));
                    float stepSize = context.getResources().getDimension(R.dimen.imageview_translation_step);
                    int XTranslationSteps = r.nextInt(XTranslationMaxStep*2+1) - XTranslationMaxStep;
                    int YTranslationSteps = r.nextInt(YTranslationMaxStep*2+1) - XTranslationMaxStep;


                    params.addRule(RelativeLayout.CENTER_HORIZONTAL, RelativeLayout.TRUE);
                    params.addRule(RelativeLayout.CENTER_VERTICAL, RelativeLayout.TRUE);
                    imageView.setLayoutParams(params);
                    imageView.setTranslationX(stepSize*XTranslationSteps);
                    imageView.setTranslationY(stepSize*YTranslationSteps);

                }
                int drawableResourceId;
                int imageVersionNumber = 0; //r.nextInt(4 - 1) + 1;
                if (sameSubImageView != null) {
                    imageVersionNumber = (int) sameSubImageView.getTag();
                }


                switch(image.category) {
                    case "architecture":
                        if (sameSubImageView == null) {
                            imageVersionNumber = r.nextInt(3)+1;
                        }
                        switch (imageVersionNumber) {
                            case 1: drawableResourceId = R.drawable.architecture_1; break;
                            case 2: drawableResourceId = R.drawable.architecture_2; break;
                            case 3: drawableResourceId = R.drawable.architecture_3; break;
                            default: drawableResourceId = R.drawable.architecture_1; break;
                        }
                        break;
                    case "nature":
                        if (sameSubImageView == null) {
                            imageVersionNumber = r.nextInt(11)+1;
                        }
                        switch (imageVersionNumber) {
                            case 1: drawableResourceId = R.drawable.nature_1; break;
                            case 2: drawableResourceId = R.drawable.nature_2; break;
                            case 3: drawableResourceId = R.drawable.nature_3; break;
                            case 4: drawableResourceId = R.drawable.nature_4; break;
                            case 5: drawableResourceId = R.drawable.nature_5; break;
                            case 6: drawableResourceId = R.drawable.nature_6; break;
                            case 7: drawableResourceId = R.drawable.nature_7; break;
                            case 8: drawableResourceId = R.drawable.nature_8; break;
                            case 9: drawableResourceId = R.drawable.nature_9; break;
                            case 10: drawableResourceId = R.drawable.nature_10; break;
                            case 11: drawableResourceId = R.drawable.nature_11; break;
                            default: drawableResourceId = R.drawable.nature_1; break;
                        }
                        break;
                    case "people":
                        if (sameSubImageView == null) {
                            imageVersionNumber = r.nextInt(7)+1;
                        }
                        switch (imageVersionNumber) {
                            case 1: drawableResourceId = R.drawable.people_1; break;
                            case 2: drawableResourceId = R.drawable.people_2; break;
                            case 3: drawableResourceId = R.drawable.people_3; break;
                            case 4: drawableResourceId = R.drawable.people_4; break;
                            case 5: drawableResourceId = R.drawable.people_5; break;
                            case 6: drawableResourceId = R.drawable.people_6; break;
                            case 7: drawableResourceId = R.drawable.people_7; break;
                            default: drawableResourceId = R.drawable.people_1; break;
                        }
                        break;
                    case "science":
                        if (sameSubImageView == null) {
                            imageVersionNumber = r.nextInt(6)+1;
                        }
                        switch (imageVersionNumber) {
                            case 1: drawableResourceId = R.drawable.science_1; break;
                            case 2: drawableResourceId = R.drawable.science_2; break;
                            case 3: drawableResourceId = R.drawable.science_3; break;
                            case 4: drawableResourceId = R.drawable.science_4; break;
                            case 5: drawableResourceId = R.drawable.science_5; break;
                            case 6: drawableResourceId = R.drawable.science_6; break;
                            default: drawableResourceId = R.drawable.science_1; break;
                        }
                        break;
                    default:
                        if (sameSubImageView == null) {
                            imageVersionNumber = r.nextInt(3)+1;
                        }
                        switch (imageVersionNumber) {
                            case 1: drawableResourceId = R.drawable.architecture_1; break;
                            case 2: drawableResourceId = R.drawable.architecture_2; break;
                            case 3: drawableResourceId = R.drawable.architecture_3; break;
                            default: drawableResourceId = R.drawable.architecture_1; break;
                        }
                        break;
                }

                imageView.setImageResource(drawableResourceId);
                imageView.setContentDescription(image.subCategory);
                imageView.setTag(imageVersionNumber);

                canvas.addView(imageView);
                image.addAndRemoveAsViewWithFadeOut(canvas);
                holder.imageListRow.setClickable(false);
                removeImage(image);
                addedImageViews.add(imageView);
            }
        });
    }

    @Override
    public int getItemCount() {
        return images.size();
    }

    /*
    public void addImage(RecordImage image) {
        images.add(image);
        notifyItemInserted(images.size() - 1);
    }*/

    public void update(List<RecordImage> recordImages) {
        images.clear();
        images.addAll(recordImages);
        notifyDataSetChanged();
    }

    public synchronized void removeImage(RecordImage image) {
        int p = images.indexOf(image);
        //image.delete();
        images.remove(p);
        notifyItemRemoved(p);
    }




    public class RecordImageViewHolder extends RecyclerView.ViewHolder {
        LinearLayout imageListRow;
        TextView imageName;
        //ImageView imagePreview;

        public RecordImageViewHolder(View imageRecordView) {
            super(imageRecordView);

            imageListRow = (LinearLayout) imageRecordView;
            imageName = (TextView) imageRecordView.findViewById(R.id.record_image_name);
            //imagePreview = (ImageView) imageRecordView.findViewById(R.id.record_image_preview);
        }
    }
}