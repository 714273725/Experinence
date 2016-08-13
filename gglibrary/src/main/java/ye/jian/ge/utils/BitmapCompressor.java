package ye.jian.ge.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.util.Log;

import java.io.ByteArrayOutputStream;

/**
 *  图片压缩工具
 * Created by Administrator on 2016/6/18.
 */
public class BitmapCompressor {
    public native static String compressBitmapNative(Bitmap bit, int w, int h, int quality, byte[] fileNameBytes,
                                                     boolean optimize);

    public native static void test(Bitmap data);

    public static void compressBitmap(Bitmap image, String filePath,String sourcePath) {
        // 最大图片大小 100KB
        int maxSize = 100;
        // 获取尺寸压缩倍数
        int ratio = getRatioSize(image.getWidth(), image.getHeight());
        // 压缩Bitmap到对应尺寸Canvas canvas = new Canvas(result);

        Bitmap result = Bitmap.createBitmap(image.getWidth() / ratio, image.getHeight() / ratio, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(result);
        Rect rect = new Rect(0, 0, image.getWidth() / ratio, image.getHeight() / ratio);
        canvas.drawBitmap(image, null, rect, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        // 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
        int options = 100;
        if(sourcePath.endsWith(".jpg")||sourcePath.endsWith(".jpeg")||sourcePath.endsWith(".JPG")||sourcePath.endsWith(".JPEG")){
            result.compress(Bitmap.CompressFormat.JPEG, options, baos);
            // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
            while (baos.toByteArray().length / 1024 > maxSize) {
                // 重置baos即清空baos
                baos.reset();
                // 每次都减少10
                options -= 10;
                // 这里压缩options%，把压缩后的数据存放到baos中
                result.compress(Bitmap.CompressFormat.JPEG, options, baos);
            }
        }else if(sourcePath.endsWith(".png")||sourcePath.endsWith(".PNG")){

        }else {
            return;
        }

        // JNI保存图片到SD卡 这个关键
        saveBitmap(result, options, filePath, true);
        // 释放Bitmap
        if (result != null && !result.isRecycled()) {
            result.recycle();
            result = null;
        }
    }

    public static int getRatioSize(int bitWidth, int bitHeight) {
        // 图片最大分辨率
        int imageHeight = 1280;
        int imageWidth = 960;
        // 缩放比
        int ratio = 1;
        // 缩放比,由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可
        if (bitWidth > bitHeight && bitWidth > imageWidth) {
            // 如果图片宽度比高度大,以宽度为基准
            ratio = bitWidth / imageWidth;
        } else if (bitWidth < bitHeight && bitHeight > imageHeight) {
            // 如果图片高度比宽度大，以高度为基准
            ratio = bitHeight / imageHeight;
        }
        // 最小比率为1
        if (ratio <= 0)
            ratio = 1;
        return ratio;
    }

    private static void saveBitmap(Bitmap bit, int quality, String fileName, boolean optimize) {
        try {
            String result =compressBitmapNative(bit, bit.getWidth(), bit.getHeight(), quality, fileName.getBytes(), optimize);
            Log.e("cc", (result == null ? "null" : result + ""));
        } catch (UnsatisfiedLinkError e) {
            e.printStackTrace();
        }
    }


}
