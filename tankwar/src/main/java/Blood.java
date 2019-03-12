import javax.swing.*;
import java.awt.*;

class Blood extends GameObject {
    private int step;

    private final int WIDTH = 20, HEIGHT = 20;

    private final int[][] points = {
            {400,400}, {420,400}, {440,400}, {440,420}, {440,440}, {420,440}, {400,440}, {400,420}
    };

    Blood() {
        this.x = points[0][0];
        this.y = points[0][1];
    }

    @Override
    void draw(Graphics g) {
        if (!this.isLive()) return;
        g.setColor(Color.MAGENTA);
        g.fillRect(x, y, WIDTH, HEIGHT);
        step++;
        step %= points.length;
        x = points[step][0];
        x = points[step][1];
    }

    @Override
    Rectangle getRectangle() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }
}
