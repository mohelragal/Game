import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Circle;
import javafx.scene.Group;
import javafx.scene.image.Image;
import java.util.ArrayList;
import java.util.List;
//done by Mohamed elragal 2304260
public class Character {
    protected String name;
    protected int maxHealth;
    protected int currentHealth;
    protected Group sprite;
    protected Weapons currentWeapon;
    protected List<Weapons> inventory = new ArrayList<>();

    public Character(String name, int maxHp, int x, int y, Color color) {
        this(name, maxHp, maxHp, x, y, color);
    }
    public Character(String name, int maxHp, int currentHp, int x, int y, Color color) {
        this.name = name;
        this.maxHealth = maxHp;
        this.currentHealth = currentHp;

        Rectangle body = new Rectangle(40, 60);
        body.setFill(color);
        body.setStroke(Color.BLACK);

        Circle head = new Circle(15);
        Image eye = new Image(getClass().getResourceAsStream("eye.JFIF"));
        head.setFill(new ImagePattern(eye));
        head.setStroke(Color.BLACK);

        head.setCenterX(20);
        head.setCenterY(20);

        sprite = new Group(body, head);

        sprite.setLayoutX(x);
        sprite.setLayoutY(y);

        inventory.add(new Bow());
        inventory.add(new Sword());
        inventory.add(new FlameThrower());
    }
    public void move(double dx, double dy) {
        double newY = sprite.getLayoutY() + dy;

        if (newY >= 0 && newY <= 540)
            sprite.setLayoutY(newY);

        sprite.setLayoutX(sprite.getLayoutX() + dx);
    }

    public void equipWeapon(Weapons w) {
        this.currentWeapon = w;
    }

    public void switchWeapon() {
        if (currentWeapon == null)
            return;

        int currentIndex = -1;

        for (int i = 0; i < inventory.size(); i++) {
            if (inventory.get(i).getType().equals(currentWeapon.getType())) {
                currentIndex = i;
                break;
            }
        }

        int nextIndex = (currentIndex + 1) % inventory.size();
        this.currentWeapon = inventory.get(nextIndex);
    }

    public Weapons getWeapon() {
        return currentWeapon;
    }
    public Group getSprite() {
        return sprite;
    }
    public double getX() {
        return sprite.getLayoutX();
    }
    public double getY() {
        return sprite.getLayoutY();
    }
    public int getCurrentHealth() {
        return currentHealth;
    }
    public int getMaxHealth() {
        return maxHealth;
    }
    public void takeDamage(int damage) {
        currentHealth -= damage;
        if (currentHealth < 0) currentHealth = 0;
    }
    public boolean isDead() {
        return currentHealth <= 0;
    }
}

