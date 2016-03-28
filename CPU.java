class CPU extends Network
{
	public void propagate(boolean dummy)
	{
		double a = (p.b.y + Ball.BALL_HEIGHT) - (control.y + Paddle.PADDLE_HEIGHT/2);
		
		if(a < 0)
			control.moveUp();
		else
			control.moveDown();
	}
}
