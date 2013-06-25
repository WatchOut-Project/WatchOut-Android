
package com.utopia.watchout.helpers.objects;

public class FBImage {
    private int height;
    private int width;
    private String url;

    public FBImage(int height, int width, String url) {
        this.height = height;
        this.width = width;
        this.url = url;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public String getUrl() {
        return url;
    }
}
