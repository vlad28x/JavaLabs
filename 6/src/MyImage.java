import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Observable;

public class MyImage  extends Observable implements Runnable  {
    private Thread thread;
    private int id;
    private int x;
    private int y;
    private int speed;
    private BufferedImage image;
    private int width;
    private int height;

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

    public void setSpeed(int speed) {
        this.speed = 6 - speed;
    }

    public BufferedImage getImage() {
        return image;
    }

    public int getWidth() {
        return width;
    }

    public MyImage(int id, int speed) {
        this.id = id;
        this.speed = speed;
        x = 7;
        y = 40;
        try {
            image = ImageIO.read(new File("image.png"));
        } catch (IOException e) {
            System.out.println(e.toString());
        }
        this.speed = 6 - speed;
        width = image.getWidth();
        height = image.getHeight();
        thread = new Thread(this);
        thread.start();
    }

    @Override
    public void run() {
        int width = image.getWidth();
        int height = image.getHeight();
        boolean xPlus = true;
        boolean yPlus = true;
        while (true) {
            if (x >= Objects.width - width) {
                xPlus = false;
            }
            if (x <= 7) {
                xPlus = true;
            }
            if (y >= Objects.height - height) {
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
