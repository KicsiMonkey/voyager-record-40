package hu.bme.yjzygk.voyagerrecord40.creation.list;

import android.content.Context;
import android.content.res.AssetManager;

import java.io.IOException;
import java.util.ArrayList;

public class RecordImageListLoader {
    public static AssetManager mngr;
    public static String extension;
    public static void loadImages(Context context, ArrayList<RecordImage> recordImages, String ext) throws IOException {
        ArrayList<String> imageFilePaths = new ArrayList<>();

        mngr = context.getAssets();
        extension = ext;

        String pathRoot="recordimages";
        String[] categories = mngr.list(pathRoot);

        for (String category : categories) {
            String pathCategory = pathRoot+'/'+category;
            String[] subCategories = mngr.list(pathCategory);

            for (String subCategory : subCategories) {
                if(subCategory.matches(".*\\."+extension)) {
                    String filePath = pathCategory+'/'+subCategory;
                    imageFilePaths.add(filePath);
                    recordImages.add(loadImage(filePath));
                } else {
                    String pathSubCategory = pathCategory+'/'+subCategory;
                    String[] subCategoryFiles = mngr.list(pathSubCategory);
                    for (String subCategoryFile : subCategoryFiles) {
                        String filePath = pathSubCategory+'/'+subCategoryFile;
                        imageFilePaths.add(filePath);
                        recordImages.add(loadImage(filePath));
                    }
                }
            }
        }
    }

    private static RecordImage loadImage(String path) throws IOException {
        /*
        InputStream ims = null;
        ims = mngr.open(path);
        // load image as Drawable
        //Drawable d = Drawable.createFromStream(ims, null);
        */
        String[] data = getIdCatSubCatFromPath(path);
        return new RecordImage(data, path);
    }

    private static String [] getIdCatSubCatFromPath(String path) {
        String[] tokens = path.split("/");
        String[] filenametokens = tokens[tokens.length-1].split("\\+");
        String id = filenametokens[0];
        String name = filenametokens[1].split("\\."+extension)[0];
        String category = tokens[1];
        String subcategory;
        if (tokens.length <= 3) {
            subcategory="";
        } else {
            subcategory=tokens[2];
        }
        return new String[]{id, name, category, subcategory};
    }
}
