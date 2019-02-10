import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.util.ArrayList;

class TankWar extends JComponent {
    static final int WIDTH = 800, HEIGHT = 600;

    private static final int REPAINT_INTERVAL = 50;

    private int x = WIDTH / 2, y = HEIGHT / 2;
    private int my = HEIGHT / 2 + 50;

    private Tank tank;

    private ArrayList<Missile> missiles;


    private TankWar() {
        this.initTankWar();
    }

    private void initTankWar() {
        missiles = new ArrayList<>();

        this.tank = new Tank(WIDTH / 2, 50);
        this.tank.initDirection(Direction.Down);

        this.addKeyListener(this.tank);
    }

    public void addMissile(Missile m) {
        this.missiles.add(m);
        System.out.println("addMissile======="+m);
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(250, 100, 300, 20);
        g.fillRect(100, 200, 20, 150);
        g.fillRect(680, 200, 20, 150);

        g.setColor(Color.MAGENTA);
        g.fillRect(360, 270, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + Tools.nextInt(10), 10, 50);
        g.drawString("Explodes: " + Tools.nextInt(10), 10, 70);
        g.drawString("Our Tank HP: " + Tools.nextInt(10), 10, 90);
        g.drawString("Enemies Left: " + Tools.nextInt(10), 10, 110);
        g.drawString("Enemies Killed: " + Tools.nextInt(10), 10, 130);

        this.tank.draw(g);

        int dist = (WIDTH - 120) / 9;
        for (int i = 0; i < 10; i++) {
            g.drawImage(new ImageIcon(this.getClass().getResource("images/tankU.gif")).getImage(),
                50 + dist * i, HEIGHT / 2 + 100, null);
        }
        g.drawImage(new ImageIcon(this.getClass().getResource("images/missileD.gif")).getImage(),
            WIDTH / 2, my, null);

        g.drawImage(new ImageIcon(this.getClass().getResource("images/10.gif")).getImage(),
            WIDTH / 2, 100, null);


        for (Missile m: this.missiles) {
            m.draw(g);
            System.out.println("for loop~~~~~~");
        }

        if (missiles.size() == 0) {
            System.out.println("missiles=======" + missiles);
        }

    }

    private void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        repaint();
                        Tools.sleepSilently(REPAINT_INTERVAL);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }.execute();
    }

    private static TankWar INSTANCE;

    static TankWar getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new TankWar();
        }
        return INSTANCE;
    }


    public static void main(String[] args) {
        PlatformImpl.startup(() -> {});
        Tools.setTheme();
        JFrame frame = new JFrame("Tank War");
        frame.setIconImage(new ImageIcon(TankWar.class.getResource("/icon.png")).getImage());
        frame.setSize(WIDTH, HEIGHT);
        frame.setLocation(400, 100);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setResizable(false);

        TankWar tankWar = TankWar.getInstance();
        frame.add(tankWar);
        // KeyListeners need to be on the focused component to work
        tankWar.setFocusable(true);
        frame.setVisible(true);
        tankWar.start();
    }
}
