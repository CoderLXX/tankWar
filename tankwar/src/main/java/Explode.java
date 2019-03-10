import javax.swing.*;
import java.awt.*;

class Explode extends GameObject {
    private int step = 0;

    private static final Image[] ExplodeImages = new Image[11];

    private static Toolkit tk = Toolkit.getDefaultToolkit();


    static {
                for (int i = 0; i < ExplodeImages.length; i ++) {
                    ExplodeImages[i] = tk.getImage(Explode.class.getClassLoader().getResource("images/" + i + ".gif"));
                }
    }

    Explode(int x, int y) {
        
        this.x = x;
        this.y = y;

    }

    @Override
    void draw(Graphics g) {
        boolean live = step < ExplodeImages.length;
        if (!live) {
            this.setLive(false);
            return;
        }
        g.drawImage(ExplodeImages[step++], x, y, null);

    }

    @Override
    Rectangle getRectangle() {
        return new Rectangle(x, y, ExplodeImages[step].getWidth(null), ExplodeImages[step].getHeight(null));
    }



}
