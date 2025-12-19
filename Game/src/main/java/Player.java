public class Player {
    private Character charcter;
    private Weapons weapon;
    private double x;
    private double y;
    private double speed =3;

    public Player(Character charcter, Weapons weapon, double x, double y, double speed) {
        this.charcter = charcter;
        this.weapon = weapon;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }
    public void move(double dx,double dy){
        x+=dx;
        y+=dy;
    }
    public Projectile shoot(){
        return weapon.shoot(x,y);
    }
}