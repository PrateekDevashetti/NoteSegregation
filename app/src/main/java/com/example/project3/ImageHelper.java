package com.example.project3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.WindowManager;

// this method will help to decrease the size of the image so that we can get better performance
public class ImageHelper {
    public static int calculateInSampleSize(BitmapFactory.Options options , int reqWidth , int reqHeight){
        final int height = options.outHeight ;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight && width > reqWidth){
            final int halfWidth = width/2;
            final int halfHeight = height/2;

            while (( halfHeight / inSampleSize) >= reqHeight && (halfWidth / inSampleSize) >= reqWidth){
                inSampleSize = 2;
            }
        }
        return inSampleSize;
    }

    public static Bitmap decodeSampleBitmapFromPath(String pathName , int reqWidth , int reqHeight){
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);

        options.inSampleSize = calculateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(pathName, options);

    }
}
