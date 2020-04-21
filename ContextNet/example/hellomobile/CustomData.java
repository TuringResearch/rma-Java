package hellomobile;

import java.io.Serializable;

public class CustomData implements Serializable {
    private static final long serialVersionUID = 6093226637618022646L;
    private String caption;
    private String icon;

    public CustomData() {
    }

    public CustomData(String caption, String icon) {
        this.caption = caption;

        this.icon = icon;
    }

    public String getCaption() {
        return caption;
    }

    public String getIcon() {
        return icon;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    @Override
    public String toString() {
        String firstPart  = "Caption: " + caption;
        String secondPart = "\nIcon Height: " + icon;
        String thirdPart  = "\nIcon Width: " + icon;

        return firstPart + secondPart + thirdPart;
    }
}