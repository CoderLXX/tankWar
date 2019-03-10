import java.awt.*;
import javax.swing.*;

class Wall extends GameObject {

    int width, height;

    Wall(int x, int y, int width, int height){
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    void draw(Graphics g) {
        g.setColor(Color.lightGray);
        g.fillRect(x, y, width, height);
    }

    @Override
    Rectangle getRectangle() {
        return new Rectangle(x, y, width, height);
    }
}
