import javafx.scene.paint.Color;

public class FlameThrower extends Weapons {

    public FlameThrower() {
        super("Flame Thrower", 25, 1200, 6.0);
    }
    @Override
    public Projectile shoot(double startX, double startY) {
        Projectile p = new Projectile(startX, startY, speed, damage);
        p.getSprite().setFill(Color.ORANGE);
        p.getSprite().setRadius(12);
        return p;
    }
}