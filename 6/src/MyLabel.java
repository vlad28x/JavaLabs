import java.awt.*;
import java.util.Observable;

public class MyLabel extends Observable implements Runnable  {
    private Thread thread;
    private int id;
    private int x;
    private int y;
    private Color color;
    private String text;
    private int speed;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Color getColor() {
        return color;
    }

    public String getText() {
        return text;
    }

    public void setSpeed(int speed) {
        this.speed = 6 - speed;
    }

    public MyLabel(int id, Color color, String text, Integer speed) {
        this.id = id;
        x = 7;
        y = 40;
        this.color = color;
        this.text = text;
        this.speed = 6 - speed;
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        int length = text.length() + String.valueOf(id).length() + 1;
        boolean xPlus = true;
        boolean yPlus = true;
        while (true) {
            int widtxt = (int)(x + 6.5*length);
            if (widtxt >= Objects.width) {
                xPlus = false;
            }
            if (x <= 7) {
                xPlus = true;
            }
            if (y >= Objects.height - 10) {
                yPlus = false;
            }
            if (y <= 40) {
                yPlus = true;
            }
            if (xPlus) x++;
            else x--;
            if (yPlus) y++;
            else y--;
            setChanged();
            notifyObservers(this);
            try {
                Thread.sleep(6 + speed * 2);
            } catch (InterruptedException e) {
                System.out.println(e.toString());
            }
        }
    }
}
