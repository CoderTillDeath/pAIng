
class Paddle
{
	public static int PADDLE_HEIGHT = Pong.HEIGHT_DEF / 6;
	public static int PADDLE_LENGTH = Pong.HEIGHT_DEF / 60;
	public static int PADDLE_SPEED = 3;
	Pong g;
	int x;
	int y;
	boolean lost;
	int moving;
	int collide;
	
	public Paddle(Pong game, boolean p1)
	{
		g = game;
		
		x = p1?10:g.length-(10 + PADDLE_LENGTH);
		y = g.height/2 - PADDLE_HEIGHT/2;
		collide = 0;
	}
	
	public void update()
	{
		
		switch(moving)
		{
			case 1:	if(y >= PADDLE_SPEED)
						y -= PADDLE_SPEED;
					break;
			case 2:	if(y <= g.height - (PADDLE_SPEED + PADDLE_HEIGHT))
						y += PADDLE_SPEED;
					break;
		}
	}
	
	public void notMoving()
	{
		moving = 0;
	}
	
	public void moveUp()
	{
		moving = 1;
	}
	
	public void moveDown()
	{
		moving = 2;
	}
}
