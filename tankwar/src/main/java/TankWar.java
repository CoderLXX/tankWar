import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyAdapter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TankWar extends JComponent {
    static final int WIDTH = 800, HEIGHT = 600;

    private static final int REPAINT_INTERVAL = 50;

    private int x = WIDTH / 2, y = HEIGHT / 2;
    private int my = HEIGHT / 2 + 50;

    private Tank tank;

    private ArrayList<Missile> missiles;
    private List<Explode> explodes;
    private final List<Wall> walls;
    private List<Tank> enemyTanks;



    private TankWar() {
        this.walls = Arrays.asList(
                new Wall(250, 100, 300, 28),
                new Wall(100, 200, 28, 280),
                new Wall(680, 200, 28, 280)
        );

        this.initTankWar();
    }

    private void initTankWar() {
        this.missiles = new ArrayList<>();
        this.explodes = new ArrayList<>();
        this.enemyTanks = new ArrayList<>();
        this.initEnemyTanks();

        this.tank = new Tank(WIDTH / 2, 50, false);
        this.tank.initDirection(Direction.Down);


    }

    public void addMissile(Missile m) {
        this.missiles.add(m);
    }

    void addExplode(Explode explode) { this.explodes.add(explode);}

    public void removeMissile(Missile m) {
        this.missiles.remove(m);
    }

    private void initEnemyTanks() {
        int dist = (WIDTH - 120) / 9;
        for (int i = 0; i < 2; i++) {
            Tank tank = new Tank(50 + dist * i, HEIGHT / 2 + 100, true);
            tank.initDirection(Direction.Up);
            this.enemyTanks.add(tank);
        }
    }

    private void triggerEvent() {
        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTanks(enemyTanks);
            m.hitTank(tank);
            m.hitWalls(walls);
        }

        for (int i = 0; i < enemyTanks.size(); i++) {
            Tank enemyT = enemyTanks.get(i);
            if (!enemyT.isLive()) {
                enemyTanks.remove(i);
                continue;
            }
            enemyT.collidesWithWalls(walls);
            enemyT.collidesWithTanks(enemyTanks);
            enemyT.directionRandom();
        }

        this.tank.collidesWithTanks(enemyTanks);

        explodes.removeIf(e -> !e.isLive());
    }

    @Override
    protected void paintComponent(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (Wall w: walls) {
            w.draw(g);
        }


        g.setColor(Color.MAGENTA);
        g.fillRect(360, 270, 15, 15);

        g.setColor(Color.WHITE);
        g.setFont(new Font("Default", Font.BOLD, 14));
        g.drawString("Missiles: " + missiles.size(), 10, 50);
        g.drawString("Explodes: " + explodes.size(), 10, 70);
        g.drawString("Our Tank HP: " + 10, 10, 90);
        g.drawString("Enemies Left: " + 10, 10, 110);
        g.drawString("Enemies Killed: " + 10, 10, 130);

        this.tank.draw(g);
        tank.collidesWithWalls(walls);



        for (Tank tank: enemyTanks) {
            tank.draw(g);
        }

        for (int i = 0; i < missiles.size(); i++) {
            Missile m = missiles.get(i);
            m.hitTank(tank);
            m.hitWalls(walls);
            m.draw(g);
        }

        for (int i = 0; i < explodes.size(); i++) {
            Explode explode = explodes.get(i);
            explode.draw(g);
        }

    }

    private void start() {
        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                while (true) {
                    try {
                        repaint();
                        triggerEvent();
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


        frame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                tankWar.tank.keyPressed(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                tankWar.tank.keyReleased(e);
            }
        });

        frame.setVisible(true);
        tankWar.start();
    }
}
