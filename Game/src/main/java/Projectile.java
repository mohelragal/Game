import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class Projectile {
    private Circle sprite;
    private double speed;
    private int damage;
    private int direction;

    public Projectile(double x, double y, double speed, int damage) {
        this.speed = speed;
        this.damage = damage;
        this.sprite = new Circle(x, y, 5, Color.BLACK);
    }
    public void move() {
        sprite.setCenterX(sprite.getCenterX() + (speed * direction));
    }
    public void setDirection(int dir) {
        this.direction = dir;
    }
    public int getDirection() {
        return direction;
    }
    public Circle getSprite() {
        return sprite;
    }
    public double getX() {
        return sprite.getCenterX();
    }
    public int getDamage() {
        return damage;
    }
}