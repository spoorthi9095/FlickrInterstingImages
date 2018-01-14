package com.spoorthi.assignmentflickr.data_classes;

/**
 * Created by Spoorthi on 1/13/2018.
 */

public class ImageData
{
    String imageUrl;
    String imageTitle;

    public ImageData(String imageUrl, String imageTitle) {
        this.imageUrl = imageUrl;
        this.imageTitle = imageTitle;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageTitle() {
        return imageTitle;
    }

    public void setImageTitle(String imageTitle) {
        this.imageTitle = imageTitle;
    }
}
