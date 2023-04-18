import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel {


    public class Enemy extends Thread{
        private Point position;
        private boolean isAlive;
        private String direction;

        public Enemy(){
            position = getNewRectangleStartPosition();
            square_positions.add(position);
            enemy_pos.add(position);
            isAlive = true;
        }
        @Override
        public void run() {
            int ctr = 1;
            while(isAlive){
                try {
                    Thread.sleep(500);
                    ctr++;
                    if(ctr == 5){
                        Bullet bullet = new Bullet("Enemy",position, bullet_pos);
                        bullets.add(bullet);
                        bullet.start();
                        ctr = 1;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                direction = getRandomDirection();
                move();
            }
        }
        public void move(){
            int new_x = (int)position.getX();
            int new_y = (int)position.getY();
            switch (direction){
                case "UP":
                    new_y = position.y - 10;
                    break;
                case "DOWN":
                    new_y = position.y + 10;
                    break;
                case "LEFT":
                    new_x = position.x - 10;
                    break;
                case "RIGHT":
                    new_x = position.x + 10;
                    break;
            }
            if(new_x >= 0 && new_x < 500 && new_y >= 0 && new_y <= 500){
                position.setLocation(new_x, new_y);
            }
        }
    }
    public class Friend extends Thread{
        private Point position;
        private boolean isAlive;
        private String direction;

        public Friend(){
            position = getNewRectangleStartPosition();
            square_positions.add(position);
            friend_pos.add(position);
            isAlive = true;
        }
        @Override
        public void run() {
            int ctr = 1;
            while(isAlive){
                try {
                    Thread.sleep(500);
                    ctr++;
                    if(ctr == 5){
                        Bullet bullet = new Bullet("Friend",position, bullet_pos);
                        bullets.add(bullet);
                        bullet.start();
                        ctr = 1;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                direction = getRandomDirection();
                move();
            }
        }
        public void move(){
            int new_x = (int)position.getX();
            int new_y = (int)position.getY();
            switch (direction){
                case "UP":
                    new_y = position.y - 10;
                    break;
                case "DOWN":
                    new_y = position.y + 10;
                    break;
                case "LEFT":
                    new_x = position.x - 10;
                    break;
                case "RIGHT":
                    new_x = position.x + 10;
                    break;
            }
            if(new_x >= 0 && new_x < 500 && new_y >= 0 && new_y <= 500){
                position.setLocation(new_x, new_y);
            }
        }
    }
    public class AirCraft extends Thread{

        private Point position;
        private String direction;
        private boolean isAlive;

        public AirCraft(){
            position = new Point(250,250);
            this.direction = "";
            isAlive = true;
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    int new_x = position.x;
                    int new_y = position.y;
                    if(code == KeyEvent.VK_A){
                        direction = "LEFT";
                        new_x = position.x - 10;
                    }
                    else if(code == KeyEvent.VK_W){
                        direction = "UP";
                        new_y = position.y - 10;
                    }
                    else if(code == KeyEvent.VK_D){
                        direction = "RIGHT";
                        new_x = position.x + 10;
                    }
                    else if(code == KeyEvent.VK_S){
                        direction = "DOWN";
                        new_y = position.y + 10;
                    }
                    System.out.println("new pos: x:"+new_x+" ,y:"+new_y);
                    if(new_x > 0 && new_x < 500 && new_y > 0 && new_y < 500){
                        position.setLocation(new_x, new_y);
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    direction = "";
                }
            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Bullet bullet = new Bullet("AirCraft",position, bullet_pos);
                    bullets.add(bullet);
                    bullet.start();
                }
            });
        }

        @Override
        public void run() {
            while(isAlive){
                my_pos = position;
                repaint();
            }
        }
    }

    public ArrayList<Point> square_positions;
    public ArrayList<Point> enemy_pos;
    public ArrayList<Point> friend_pos;
    public ArrayList<Point> bullet_pos;

    public Point my_pos;
    public ArrayList<Bullet> bullets;

    public Game(){
        JFrame window = new JFrame();
        window.setSize(500,500);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setPreferredSize(new Dimension(500,500));
        this.setDoubleBuffered(true);
        window.add(this);
        window.pack();
        window.setLocationRelativeTo(null);
        window.setVisible(true);
        setFocusable(true);
        requestFocusInWindow();
        square_positions = new ArrayList<>();
        square_positions.add(new Point(250,250));
        my_pos = new Point(250,250);
        friend_pos = new ArrayList<>();
        enemy_pos = new ArrayList<>();
        bullet_pos = new ArrayList<>();
        bullets = new ArrayList<>();
    }
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
    public String getRandomDirection(){
        Random random = new Random();
        int random_no = random.nextInt(4);
        switch (random_no){
            case 0:
                return "LEFT";
            case 1:
                return "RIGHT";
            case 2:
                return "UP";
            case 3:
                return "DOWN";
        }
        return null;
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        Graphics g2 = (Graphics2D) g;
        g2.setColor(Color.GREEN);
        for(int i=0; friend_pos != null && i<friend_pos.size(); i++){
            Point pos = friend_pos.get(i);
            g2.fillRect(pos.x, pos.y, 10,10);
        }
        g2.setColor(Color.BLACK);
        for(int i=0; enemy_pos != null && i<enemy_pos.size(); i++){
            Point pos = enemy_pos.get(i);
            g2.fillRect(pos.x, pos.y, 10,10);
        }

        for(int i=0; bullets != null && i<bullets.size(); i++){
            Bullet bullet = bullets.get(i);
            if(bullet != null && bullet.isAlive()){
                String owner = bullet.getOwner();
                switch (owner){
                    case "AirCraft":
                        g2.setColor(Color.ORANGE);
                        break;
                    case "Enemy":
                        g2.setColor(Color.BLUE);
                        break;
                    case "Friend":
                        g2.setColor(Color.MAGENTA);
                        break;
                }
                Point left = bullet.getPositionLeft();
                Point right = bullet.getPositionRight();
                if (left != null){
                    g2.fillRect(left.x, left.y, 5,5);
                }
                if(right != null){
                    g2.fillRect(right.x, right.y, 5,5);
                }
            }
        }
        g2.setColor(Color.RED);
        if(my_pos != null)
            g2.fillRect(my_pos.x, my_pos.y, 10,10);

        g2.dispose();
    }

}
