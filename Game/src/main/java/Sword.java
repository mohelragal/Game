import javafx.scene.paint.Color;
public class Sword extends Weapons {
    public Sword() {
        super("Sword", 15, 700, 9.0);
    }
    @Override
    public Projectile shoot(double x, double y) {
        Projectile p = new Projectile(x, y, speed, damage);
        p.getSprite().setFill(Color.WHITESMOKE);
        p.getSprite().setRadius(7);
        return p;
    }
}