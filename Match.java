
class Match
{
	Pong p;
	Network p1;
	Network p2;
	
	public Match(Pong g, Network n1, Network n2)
	{
		p = g;
		p1 = n1;
		p1.setPong(p,true);
		p2 = n2;
		p2.setPong(p,false);
	}
	
	public boolean over()
	{
		return p == null || p.p1.lost || p.p2.lost;
	}
	
	public void dispose()
	{
		p.dispose();
	}
	
	public void update()
	{
		p.update();
		p1.propagate(false);
		p2.propagate(false);
	}
}
