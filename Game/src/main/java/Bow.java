import javafx.scene.paint.Color;

public class Bow extends Weapons {
    public Bow() {
        super("Bow", 10, 600, 12.0);
    }
    @Override
    public Projectile shoot(double x, double y) {
        Projectile p = new Projectile(x, y, speed, damage);
        p.getSprite().setFill(Color.BLUE);
        return p;
    }
}