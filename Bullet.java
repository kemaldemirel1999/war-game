import java.awt.*;
import java.util.ArrayList;

public class Bullet extends Thread{
    private Point position_left;
    private Point position_right;
    private boolean left_bullet_alive;
    private boolean right_bullet_alive;
    private String owner;

    public Bullet(String owner, Point pos, ArrayList<Point> bullet_pos){
        this.owner = owner;
        position_left = new Point(pos.x-10, pos.y);
        position_right = new Point(pos.x+10, pos.y);
        left_bullet_alive = true;
        right_bullet_alive = true;
        bullet_pos.add(position_left);
        bullet_pos.add(position_right);
    }
    public void fire(){
        if(left_bullet_alive)
            position_left.x -= 10;
        if(right_bullet_alive)
            position_right.x += 10;
    }
    public void check_bullets(){
        if(position_left!= null && position_left.x <= 0){
            left_bullet_alive = false;
            position_left = null;
        }
        if(position_right!= null && position_right.x >= 500){
            right_bullet_alive = false;
            position_right = null;
        }
    }
    @Override
    public void run(){
        while(left_bullet_alive || right_bullet_alive){
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fire();
            check_bullets();
        }
    }
    public Point getPositionLeft(){
        return position_left;
    }
    public Point getPositionRight(){
        return position_right;
    }
    public String getOwner(){
        return this.owner;
    }

}
