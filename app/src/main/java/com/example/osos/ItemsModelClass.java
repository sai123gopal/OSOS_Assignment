package com.example.osos;

import android.net.Uri;

import java.util.ArrayList;

public class ItemsModelClass {
    String title;
    ArrayList<Uri> urisList;

    public ItemsModelClass(String title, ArrayList<Uri> urisList) {
        this.title = title;
        this.urisList = urisList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArrayList<Uri> getUrisList() {
        return urisList;
    }

    public void setUrisList(ArrayList<Uri> urisList) {
        this.urisList = urisList;
    }
}
