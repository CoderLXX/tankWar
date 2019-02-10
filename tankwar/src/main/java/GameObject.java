import java.awt.*;

abstract class GameObject {
    int x, y;
    private boolean live = true;

    abstract void draw(Graphics g);

    abstract Rectangle getRectangle();

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }
}
