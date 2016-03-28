
class Ball
{
	public static int BALL_LENGTH = Paddle.PADDLE_LENGTH;
	public static int BALL_HEIGHT = Paddle.PADDLE_LENGTH;
	Pong g;
	double x;
	double y;
	double speed;
	double angle;
	
	public Ball(Pong game)
	{
		this.g = game;
		
		x = g.length/2;
		y = (int)(Math.random() * (g.height - 20)) + 10;
		
		speed = Math.random() * 2 + 1;
		
		int side = (int)(Math.random() * 2);
		
		switch(side)
		{
			case 0:	angle = Math.PI / 2 * (Math.random() * 1.2 - .6);
					break;
			case 1:	angle = Math.PI / 2 * (Math.random() * 1.2 - .6) + Math.PI;
					break;
		}
	}
	
	public void update()
	{
		x += Math.cos(angle) * speed;
		y -= Math.sin(angle) * speed;
		collisionCheck();
	}
	
	public void collisionCheck()
	{
		if(y <= 0 && angle > 0 && angle < Math.PI)
		{
			angle = -angle;
		}
		
		if(y >= (g.height - BALL_HEIGHT) && ((angle < 0 && angle > -Math.PI) || (angle > Math.PI/2 && angle < Math.PI * 2  ) ))
		{
			angle = -angle;
		}
		
		if(x < 0)
		{
			g.p1.lost = true;
		}
		
		if(x > g.length - BALL_LENGTH)
		{
			g.p2.lost = true;
		}
		
		if(x >= g.p1.x && x <= (g.p1.x + Paddle.PADDLE_LENGTH) && y >= g.p1.y - BALL_HEIGHT && y <= g.p1.y + Paddle.PADDLE_HEIGHT)
		{
			double num = (g.p1.y + (double) g.p1.PADDLE_HEIGHT / 2) - (y + (double) BALL_HEIGHT / 2);
			
			num /= Paddle.PADDLE_HEIGHT / 2;
			
			angle = num*Math.PI/2;
			g.p1.collide++;
		}
		
		if(x >= g.p2.x - BALL_LENGTH && x <= (g.p2.x + Paddle.PADDLE_LENGTH - BALL_LENGTH) && y >= g.p2.y - BALL_HEIGHT && y <= g.p2.y + (Paddle.PADDLE_HEIGHT))
		{
			double num = (g.p2.y + (double) g.p2.PADDLE_HEIGHT / 2) - (y + (double) BALL_HEIGHT / 2);
			
			num /= Paddle.PADDLE_HEIGHT / 2;
			
			if(num > 0)
			{
				num = 1 - num;
				angle = (num*Math.PI/2) + Math.PI/2;
			}
			else
			{
				num = -(1 + num);
				angle =  num*Math.PI/2 - Math.PI/2;
			}
			g.p2.collide++;
		}
	}
}

