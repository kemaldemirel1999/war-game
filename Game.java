import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class Game extends JPanel {

    public class Enemy extends Square{

        public Enemy(){
            setPosition(getNewRectangleStartPosition());
            square_positions.add(getPosition());
            enemy_pos.add(getPosition());
            setActive(true);
        }
        @Override
        public void run() {
            int ctr = 1;
            while(isActive()){
                try {
                    Thread.sleep(500);
                    ctr++;
                    if(ctr == 5){
                        Bullet bullet = new Bullet("Enemy",getPosition(), bullet_pos);
                        bullets.add(bullet);
                        bullet.start();
                        ctr = 1;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                setDirection(getRandomDirection());
                move();
            }
        }
    }
    public class Friend extends Square{

        public Friend(){
            setPosition(getNewRectangleStartPosition());
            square_positions.add(getPosition());
            friend_pos.add(getPosition());
            setActive(true);
        }
        @Override
        public void run() {
            int ctr = 1;
            while(isActive()){
                try {
                    Thread.sleep(500);
                    ctr++;
                    if(ctr == 5){
                        Bullet bullet = new Bullet("Friend",getPosition(), bullet_pos);
                        bullets.add(bullet);
                        bullet.start();
                        ctr = 1;
                    }
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
                setDirection(getRandomDirection());
                move();
            }
        }

    }
    public class AirCraft extends Square{

        public AirCraft(){
            setPosition(new Point(250,250));
            setDirection("");
            setActive(true);
            addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {}

                @Override
                public void keyPressed(KeyEvent e) {
                    int code = e.getKeyCode();
                    int new_x = getPosition().x;
                    int new_y = getPosition().y;
                    if(code == KeyEvent.VK_A){
                        setDirection("LEFT");
                        new_x = getPosition().x - 10;
                    }
                    else if(code == KeyEvent.VK_W){
                        setDirection("UP");
                        new_y = getPosition().y - 10;
                    }
                    else if(code == KeyEvent.VK_D){
                        setDirection("RIGHT");
                        new_x = getPosition().x + 10;
                    }
                    else if(code == KeyEvent.VK_S){
                        setDirection("DOWN");
                        new_y = getPosition().y + 10;
                    }
                    System.out.println("new pos: x:"+new_x+" ,y:"+new_y);
                    if(new_x > 0 && new_x < 500 && new_y > 0 && new_y < 500){
                        Point pos = getPosition();
                        pos.setLocation(new_x,new_y);
                        setLocation(pos);
                    }
                }
                @Override
                public void keyReleased(KeyEvent e) {
                    setDirection("");
                }
            });
            addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {
                    super.mousePressed(e);
                    Bullet bullet = new Bullet("AirCraft",getPosition(), bullet_pos);
                    bullets.add(bullet);
                    bullet.start();
                }
            });
        }

        @Override
        public void run() {
            while(isActive()){
                my_pos = getPosition();
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
