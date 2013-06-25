
package com.utopia.watchout.helpers.objects;

public class FBStatus {
    private long id;
    private String name;
    private String message;
    private String placeName;
    private FBImage[] images;
    private long created_time_millis;

    public FBStatus(long id, String name, String message, String placeName, FBImage[] images,
            long created_time_millis) {
        this.id = id;
        this.name = name;
        this.message = message;
        this.images = images;
        this.placeName = placeName;
        this.created_time_millis = created_time_millis;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getMessage() {
        return message;
    }

    public String getNameAndMessage() {
        String nameAndMessage = "";
        if (name != null && message != null)
            nameAndMessage = name + ", " + message;
        else if (name != null)
            nameAndMessage = name;
        else if (message != null)
            nameAndMessage = message;

        return nameAndMessage;
    }

    public String getPlaceName() {
        return placeName;
    }

    public FBImage[] getImages() {
        return images;
    }

    public long getCreatedTimeMillis() {
        return created_time_millis;
    }
}
