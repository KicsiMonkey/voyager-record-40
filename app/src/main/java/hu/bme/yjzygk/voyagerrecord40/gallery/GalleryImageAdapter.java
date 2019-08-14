package hu.bme.yjzygk.voyagerrecord40.gallery;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import hu.bme.yjzygk.voyagerrecord40.R;

public class GalleryImageAdapter extends RecyclerView.Adapter<GalleryImageAdapter.GalleryImageViewHolder> {
    private ArrayList<String> paths;
    private Context context;
    private ImageView zoomedPic;
    private LinearLayout backDrop;

    public GalleryImageAdapter(Context context, ArrayList<String> paths, final ImageView zoomedPic, final LinearLayout backDrop) {
        this.paths = paths;
        this.context = context;
        this.zoomedPic = zoomedPic;
        this.backDrop = backDrop;
        zoomedPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomedPic.setVisibility(View.INVISIBLE);
                backDrop.setVisibility(View.GONE);
            }
        });
        backDrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                zoomedPic.setVisibility(View.INVISIBLE);
                backDrop.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public GalleryImageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gallery_image_list, parent, false);
        return new GalleryImageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(GalleryImageViewHolder holder, final int position) {
        //holder.imagePreview.setImageBitmap(
                //PreviewLoader.decodeSampledBitmapFromFilePath(paths.get(position), 100, 100));
        Glide.with(context).load(paths.get(position)).crossFade().into(holder.imagePreview);
        holder.imagePreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.parse(paths.get(position)), "image/*");
                context.startActivity(intent);
                */
                backDrop.setVisibility(View.VISIBLE);
                zoomedPic.setVisibility(View.VISIBLE);
                Glide.with(context).load(paths.get(position)).crossFade().into(zoomedPic);
            }
        });
    }

    @Override
    public int getItemCount() {
        return paths.size();
    }

    public class GalleryImageViewHolder extends RecyclerView.ViewHolder {
        ImageView imagePreview;

        public GalleryImageViewHolder(View imageRecordView) {
            super(imageRecordView);
            imagePreview = (ImageView) imageRecordView.findViewById(R.id.gallery_image_list_cell);
        }
    }
}
