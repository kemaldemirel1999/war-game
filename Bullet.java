import java.awt.*;
import java.util.ArrayList;

public class Bullet extends Thread{
    private Point position_left;
    private Point position_right;
    private boolean left_bullet_alive;
    private boolean right_bullet_alive;
    private String owner;
    private ArrayList<Object> enemies;

    public Bullet(String owner, Point pos, ArrayList<Point> bullet_pos, ArrayList enemies){
        this.owner = owner;
        position_left = new Point(pos.x-10, pos.y);
        position_right = new Point(pos.x+10, pos.y);
        left_bullet_alive = true;
        right_bullet_alive = true;
        bullet_pos.add(position_left);
        bullet_pos.add(position_right);
        this.enemies = enemies;
    }
    public void fire(){
        if(left_bullet_alive)
            position_left.x -= 10;
        if(right_bullet_alive)
            position_right.x += 10;
    }
    public void checkBullets(){
        if(position_left!= null && position_left.x <= 0){
            left_bullet_alive = false;
            position_left = null;
        }
        if(position_right!= null && position_right.x >= 500){
            right_bullet_alive = false;
            position_right = null;
        }
    }
    public void kill(Game.Enemy enemy){
        enemy.setActive(false);
    }
    public void kill(Game.Friend friend){
        friend.setActive(false);
    }
    public void kill(Game.AirCraft airCraft){
        airCraft.setActive(false);
    }
    public void checkEnemy(){
        if(enemies != null) {
            for(Object square : enemies){
                if(owner.equals("Enemy")){
                    if(square.getClass().equals(Game.Friend.class)){
                        Game.Friend friend = (Game.Friend)square;
                        if(position_left!= null && position_left.distance(friend.getPosition()) <= 5){
                            System.out.println("kill");
                            kill(friend);
                        }
                        if(position_right!= null && position_right.distance(friend.getPosition()) <= 5){
                            System.out.println("kill");
                            kill(friend);
                        }
                    }
                    else if(square.getClass().equals(Game.AirCraft.class)){
                        Game.AirCraft airCraft = (Game.AirCraft)square;
                        if(position_left!= null && position_left.distance(airCraft.getPosition()) <= 5){
                            kill(airCraft);
                            System.out.println("kill");
                        }
                        if(position_right!= null && position_right.distance(airCraft.getPosition()) <= 5){
                            kill(airCraft);
                            System.out.println("kill");
                        }
                    }
                }
                else if(owner.equals("Friend") || owner.equals("AirCraft")){
                    Game.Enemy enemy = (Game.Enemy) square;
                    if(position_left!= null && position_left.distance(enemy.getPosition()) <= 5){
                        kill(enemy);
                        System.out.println("kill");
                    }
                    if(position_right!= null && position_right.distance(enemy.getPosition()) <= 5){
                        kill(enemy);
                        System.out.println("kill");
                    }
                }
            }
        }

    }
    @Override
    public void run(){
        while(left_bullet_alive || right_bullet_alive){
            checkEnemy();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            fire();
            checkBullets();
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
