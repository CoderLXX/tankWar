import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Missile extends GameObject {

    private static final int XSPEED = 10, YSPEED = 10;
    public static final int WIDTH = 10, HEIGHT = 10;

    private final Direction dir;

    private boolean isEnemy;

    private static Image[] MissileImages = null;
    private static Map<String, Image> mImgs = new HashMap<String, Image>();

    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private boolean kL, kU, kR, kD;

    static {
        MissileImages = new Image[] {
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileL.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileLU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileRU.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileR.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileRD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileD.gif")),
                tk.getImage(Tank.class.getClassLoader().getResource("images/missileLD.gif")),

        };

        mImgs.put("L", MissileImages[0]);
        mImgs.put("LU", MissileImages[1]);
        mImgs.put("U", MissileImages[2]);
        mImgs.put("RU", MissileImages[3]);
        mImgs.put("R", MissileImages[4]);
        mImgs.put("RD", MissileImages[5]);
        mImgs.put("D", MissileImages[6]);
        mImgs.put("LD", MissileImages[7]);

    }


    Missile(int x, int y, Direction dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    Missile(int x, int y, Direction dir, boolean isEnemy) {
        this(x, y, dir);
        this.isEnemy = isEnemy;
    }



    @Override
    void draw(Graphics g) {
        if (!isLive()) {
            TankWar.getInstance().removeMissile(this);
            return;
        }
        g.drawImage(mImgs.get(dir.abbrev), x, y, null);
        move();

    }

    public void move() {
        switch (dir) {
            case Left:
                x -= XSPEED;
                break;
            case LeftUp:
                x -= XSPEED;
                y -= YSPEED;
                break;
            case Up:
                y -= YSPEED;
                break;
            case RightUp:
                x += XSPEED;
                y -= YSPEED;
                break;
            case Right:
                x += XSPEED;
                break;
            case RightDown:
                x += XSPEED;
                y += YSPEED;
                break;
            case Down:
                y += YSPEED;
                break;
            case LeftDown:
                x -= XSPEED;
                y += YSPEED;
                break;
        }
        if (x < 0 || y < 0 || x > TankWar.WIDTH || y > TankWar.HEIGHT) {
            this.setLive(false);
        }
    }

    @Override
    Rectangle getRectangle() {
        return new Rectangle(x, y, WIDTH, HEIGHT);
    }

    boolean hitTank(Tank tank) {
        if (this.isLive() && this.getRectangle().intersects(tank.getRectangle())
         && tank.isLive() && this.isEnemy != tank.isEnemy()) {
            this.setLive(false);
            if (!tank.isEnemy()) {
                tank.setLife(tank.getLife() - 10);
                if (tank.getLife() <= 0) {
                    tank.setLive(false);
                }
            } else {
                tank.setLive(false);
                Explode e = new Explode(x, y);
                TankWar.getInstance().addExplode(e);
            }

            return true;
        }
        return false;
    }

    void hitTanks(List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            if (hitTank(tanks.get(i))) {
                break;
            }
        }
    }

    void hitWalls(List<Wall> walls) {
        for (Wall w: walls) {
            boolean isHit = this.isLive() && this.getRectangle().intersects(w.getRectangle());
            if (isHit) {
                this.setLive(false);
                break;
            }
        }

    }
}
