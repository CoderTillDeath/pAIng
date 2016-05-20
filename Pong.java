import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Pong extends JFrame
{
    public static int LENGTH_DEF = 8 * 80;
    public static int HEIGHT_DEF = 6 * 80;
    Paddle p1;
    Paddle p2;
    Ball b;
    int length;
    int height;
    
    public static int PIXEL_SIZE = 1;
    int topBorder = 26;
    int sideBorder = 3;
    
    public Pong(int s)
    {
        length = LENGTH_DEF;
        height = HEIGHT_DEF;
        
        p1 = new Paddle(this,true);
        p2 = new Paddle(this,false);
        b = new Ball(this);
        
        if(s / 3 == 0)
            setLocation(s*length,0);
        else
            setLocation((s%3)*length, height + topBorder);
        
        setSize(length * PIXEL_SIZE + 2*sideBorder,height * PIXEL_SIZE + sideBorder + topBorder);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        setResizable(false);
        addKeyListener(new GameListener());
        
        setContentPane(new MyPanel());
        setVisible(true);
    }
    
    public Pong(int x, int y)
    {
        length = x;
        height = y;
        
        p1 = new Paddle(this,true);
        p2 = new Paddle(this,false);
        b = new Ball(this);
    }
    
    public void update()
    {
        b.update();
        p1.update();
        p2.update();
        repaint();
    }
    
    class MyPanel extends JPanel
    {
        public void paintComponent(Graphics g)
        {
            g.setColor(Color.BLACK);
            g.fillRect((int)p1.x * PIXEL_SIZE, (int)p1.y * PIXEL_SIZE, Paddle.PADDLE_LENGTH * PIXEL_SIZE, Paddle.PADDLE_HEIGHT * PIXEL_SIZE);
            g.fillRect((int)p2.x * PIXEL_SIZE, (int)p2.y * PIXEL_SIZE, Paddle.PADDLE_LENGTH * PIXEL_SIZE, Paddle.PADDLE_HEIGHT * PIXEL_SIZE);
            g.fillRect((int)b.x * PIXEL_SIZE, (int)b.y * PIXEL_SIZE, Ball.BALL_LENGTH * PIXEL_SIZE, Ball.BALL_HEIGHT * PIXEL_SIZE);
        }
    }

    public class GameListener implements KeyListener
    {
	public void keyReleased (KeyEvent e) {
	    switch(e.getKeyCode())
	    {
	    case 38: 
	    case 40: p2.notMoving();
		break;
	    case 44: 
	    case 79: p1.notMoving();
		break;
	    }
	}

	public void keyPressed (KeyEvent e) {
	    switch(e.getKeyCode())
	    {
	    case 38: p2.moveUp();
		break;
	    case 40: p2.moveDown();
		break;
	    case 44: p1.moveUp();
		break;
	    case 79: p1.moveDown();
		break;
	    }
	}

	public void keyTyped (KeyEvent e) {
	    
	}
    }

    public static void main(String[] args)
    {
	Pong p = new Pong(1);

	for (int i = 0; i < 1000; i++) {
	    p.update();
	    
	    try {
		Thread.sleep(40);
	    } catch (InterruptedException ignore) { }

	}
    }
}
