package com.jaiselrahman.filepicker.utils;

import com.jaiselrahman.filepicker.R;

public class EasyFile {

    public static String getExtension(String filePath) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String[] split = fileName.split("\\.");
        return split[split.length - 1];
    }

    public static StringBuilder getFileName(String filePath, Boolean showExtension) {
        String fileName = filePath.substring(filePath.lastIndexOf("/") + 1);
        String[] split = fileName.split("\\.");
        StringBuilder fileNameToUpload = new StringBuilder();
        int index = 0;
        for (String name : split) {
            if (!showExtension) {
                if (index == split.length - 1) {
                    break;
                }
            }
            if (index != 0) {
                fileNameToUpload.append(".");
            }
            fileNameToUpload.append(name);
            index++;
        }
        return fileNameToUpload;
    }

    public static Integer getThumbnail(String extension) {

        switch (extension) {
            case "doc":
            case "dot":
            case "wbk":
            case "docx":
            case "docm":
            case "dotx":
            case "dotm":
            case "docb":
                return R.drawable.ic_word;
            case "zip":
            case "7z":
                return R.drawable.ic_zip;
            default:
                return R.drawable.ic_pdf;
        }

    }

}
