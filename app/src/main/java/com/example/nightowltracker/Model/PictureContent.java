package com.example.nightowltracker.Model;

import android.net.Uri;

import java.io.File;
import java.util.ArrayList;

public class PictureContent {
    static final ArrayList<PictureItem> ITEMS = new ArrayList<>();

    public static void loadImage(File file) {
        PictureItem newItem = new PictureItem();
        newItem.uri = Uri.fromFile(file);
        newItem.date = getDateFromUri(newItem.uri);
        addItem(newItem);
    }

    private static String getDateFromUri(Uri uri) {
        return null;
    }

    private static void addItem(PictureItem item) {
        ITEMS.add(0, item);
    }
}