package SpaceProtector;

import java.awt.*;

public class Sprite {
	
    private Image image;
    private boolean show;
    private boolean destroyed;
    int x;
    int y;

    public Sprite() {
        show = true;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Image getImage() {
        return image;
    }

    public void setX(int x) {
        this.x = x;
    }
    
    public int getX() {
        return x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getY() {
        return y;
    }

    public void distroy() {
        show = false;
    }

    public boolean isDestroyed() {
        return this.destroyed;
    }
    
    public void setDestroyed(boolean destroyed) {
        this.destroyed = destroyed;
    }

    public boolean isShow() {
        return show;
    }

    protected void setShow(boolean show) {
        this.show = show;
    }
}
