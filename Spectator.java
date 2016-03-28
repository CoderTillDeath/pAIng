import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class Spectator extends JFrame implements Runnable
{
	public static int PIXEL_SIZE = 1;
	int topBorder = 26;
	int sideBorder = 3;
	Trainer t;
	Pong p;
	
	public Spectator(Trainer t)
	{
		this.t = t;
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		setResizable(false);
//		addKeyListener(new GameListener());
		
		setContentPane(new MyPanel());
		setVisible(true);
	}
	
	public void run()
	{
		while(t.training)
		{
			while(p != null && !p.p1.lost && !p.p2.lost)
			{
				repaint();
			}
		}
	}
	
	public void setPong(Pong p)
	{
		this.p = p;
		setSize(p.length * PIXEL_SIZE + 2*sideBorder,p.height * PIXEL_SIZE + sideBorder + topBorder);
	}
	
	class MyPanel extends JPanel
	{
		public void paintComponent(Graphics g)
		{
			if(p != null)
			{
				g.setColor(Color.BLACK);
				g.fillRect((int)p.p1.x * PIXEL_SIZE, (int)p.p1.y * PIXEL_SIZE, Paddle.PADDLE_LENGTH * PIXEL_SIZE, Paddle.PADDLE_HEIGHT * PIXEL_SIZE);
				g.fillRect((int)p.p2.x * PIXEL_SIZE, (int)p.p2.y * PIXEL_SIZE, Paddle.PADDLE_LENGTH * PIXEL_SIZE, Paddle.PADDLE_HEIGHT * PIXEL_SIZE);
				g.fillRect((int)p.b.x * PIXEL_SIZE, (int)p.b.y * PIXEL_SIZE, Ball.BALL_LENGTH * PIXEL_SIZE, Ball.BALL_HEIGHT * PIXEL_SIZE);
			}
		}
	}
	
	class GameListener implements KeyListener
	{
		public void keyPressed(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case 38:	p.p2.moveUp();
							break;
				case 40:	p.p2.moveDown();
							break;
				case 44:	p.p1.moveUp();
							break;
				case 79:	p.p1.moveDown();
							break;
			}
		}
		
		public void keyTyped(KeyEvent e)
		{
			
		}
		
		public void keyReleased(KeyEvent e)
		{
			switch(e.getKeyCode())
			{
				case 38:	
				case 40:	p.p2.notMoving();
							break;
				case 44:	
				case 79:	p.p1.notMoving();
							break;
			}
			
		}
	}
	
	// EEEWWWWWWWW
	/*
	public static void main(String[] args)
	{
		Pong p = new Pong();
		
		Thread d = new Thread(new Spectator(p));
		
		d.start();
	}*/
}
