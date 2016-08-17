package com.jack.myexperience.tools;


import com.jack.myexperience.MyApplication;

import java.io.File;
import java.util.UUID;

public class FileManager {

    //"/storage/emulated/0/Android/data/包名/files/camera/"
    public static final String CAMERA_DIR = "camera/";
    //"/storage/emulated/0/Android/data/包名/files/crop/"
    public static final String CROP_DIR = "crop/";
    //"/storage/emulated/0/Android/data/包名/files/compress/"
    public static final String COMPRESS_DIR = "compress/";

    /**
     * @return 存储拍照的目录
     */
    public static File cameraDir() {
        return new File(MyApplication.getInstance().getExternalFilesDir(null), CAMERA_DIR);
    }

    /**
     * @return 存储裁图的目录
     */
    public static File cropDir() {
        return new File(MyApplication.getInstance().getExternalFilesDir(null), CROP_DIR);
    }

    /**
     * @return 存储压缩图片的目录
     */
    public static File compressDir() {
        return new File(MyApplication.getInstance().getExternalFilesDir(null), COMPRESS_DIR);
    }

    /**
     * 创建一个新的File，保存新的裁图
     *
     * @return File对象
     */
    public static File newCropFile() {
        File dir = cropDir();
        if (!dir.exists()) {
            dir.mkdirs();
        }

        return new File(cropDir(), "yierya" + UUID.randomUUID() + ".png");
    }

    //递归删除指定路径下的所有文件
    public static void deleteAll(File file) {
        if (file != null && file.exists()) {
            if (file.isFile() || file.list().length == 0) {
                file.delete();
            } else {
                File[] files = file.listFiles();
                for (File f : files) {
                    deleteAll(f);//递归删除每一个文件
                    f.delete();//删除该文件夹
                }
            }
        }
    }

}
