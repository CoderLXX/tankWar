import java.awt.*;

abstract class GameObject {
    static int x, y;
    static boolean live = true;

    abstract void draw(Graphics g);

    abstract Rectangle getRectangle();

    public static boolean isLive() {
        return live;
    }

    public static void setLive(boolean live) {
        GameObject.live = live;
    }
}
