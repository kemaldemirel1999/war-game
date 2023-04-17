import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JFrame {

    public class Enemy extends Thread{
        private Point position;

        public Enemy(){
            position = getNewRectangleStartPosition();
            square_positions.add(position);
        }
        @Override
        public void run() {
            System.out.println("yes");
        }

    }
    public class Friend extends Thread{
        private Point position;

        public Friend(){
            position = getNewRectangleStartPosition();
            square_positions.add(position);
            friend_pos.add(position);
        }
        @Override
        public void run() {
            System.out.println("run");
        }
    }
    public class AirCraft extends Thread{

        private Point position;
        private String direction;
        private boolean isAlive;

        public AirCraft(){
            position = new Point(250,250);
            direction = "";
            isAlive = true;
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    if(code == KeyEvent.VK_A){
                        direction = "LEFT";
                    }
                    else if(code == KeyEvent.VK_W){
                        direction = "UP";
                    }
                    else if(code == KeyEvent.VK_D){
                        direction = "RIGHT";
                    }
                    else if(code == KeyEvent.VK_S){
                        direction = "DOWN";
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    direction = "";
                }
            });
        }

        @Override
        public void run() {
            while(isAlive){
                int new_x = (int)position.getX();
                int new_y = (int)position.getY();
                switch (direction){
                    case "UP":
                        new_y = position.y - 10;
                        break;
                    case "LEFT":
                        new_x = position.x - 10;
                        break;
                    case "DOWN":
                        new_y = position.y + 10;
                        break;
                    case "RIGHT":
                        new_x = position.x + 10;
                        break;
                }
                if(new_x >= 0 && new_x <= 500 && new_y >= 0 && new_y <= 500){
                    position.setLocation(new_x, new_y);
                }
            }
        }
    }

    public ArrayList<Point> square_positions;
    public ArrayList<Point> enemy_pos;
    public ArrayList<Point> friend_pos;
    public Point my_pos;

    public Point getNewRectangleStartPosition(){
        Random random = new Random();
        int random_y = random.nextInt(51)*10;
        int random_x = random.nextInt(51)*10;
        Point point = new Point(random_x, random_y);
        while(square_positions.contains(point)){
            random_y = random.nextInt(51)*10;
            random_x = random.nextInt(51)*10;
            point = new Point(random_x, random_y);
        }
        return point;
    }
    public Game(){
        setSize(500,500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        square_positions = new ArrayList<>();
        square_positions.add(new Point(250,250));
        my_pos = new Point(250,250);
        friend_pos = new ArrayList<>();
        enemy_pos = new ArrayList<>();
    }
}
