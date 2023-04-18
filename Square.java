import java.awt.*;

public class Square extends Thread{

    private Point position;
    private boolean isActive;
    private String direction;

    public Square(){

    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
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
