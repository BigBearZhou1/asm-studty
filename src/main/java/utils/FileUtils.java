package utils;

import java.io.*;

public class FileUtils {
    public static String getFilePath(String relativePath){
        String dir = FileUtils.class.getResource("/").getPath();
        return dir+relativePath;
    }

    public static void writeBytes(String filepath,byte[] bytes){
        File file = new File(filepath);
        File dirFile = file.getParentFile();
        mkdirs(dirFile);

        try (OutputStream out = new FileOutputStream(filepath);
             BufferedOutputStream buff = new BufferedOutputStream(out)) {
            buff.write(bytes);
            buff.flush();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    public static void mkdirs(File dirFile) {
        boolean file_exists = dirFile.exists();

        if (file_exists && dirFile.isDirectory()) {
            return;
        }

        if (file_exists && dirFile.isFile()) {
            throw new RuntimeException("Not A Directory: " + dirFile);
        }

        if (!file_exists) {
            boolean flag = dirFile.mkdirs();
            System.out.println("mkdirs res = "+flag);
        }
    }
}
