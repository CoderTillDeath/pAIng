import java.util.ArrayList;

class Trainer
{
	boolean training;
	ArrayList<Network> networks;
	
	
	public Trainer()
	{
		training = true;
		initializeNetworks();
		trainWithAI();
	}
	
	public void trainWithAI()
	{
		long gen = 1;
		ArrayList<Match> matches = new ArrayList<Match>();
		int MATCHES_PER_BATCH = 1;
		int delay = 10;
		
		
		while(training)
		{
			int count = 0;
			System.out.println("Generation: " + gen);
			for(int x = 0; x < networks.size(); x += MATCHES_PER_BATCH)
			{
				System.out.println("Match: " + x);
				matches.clear();
				
				for(int m = 0; m < MATCHES_PER_BATCH; m++)
				{
					matches.add(new Match(new Pong(m), networks.get(x + m), new CPU()));
				}
				
				long trueBegin = System.currentTimeMillis();
				while(keepGoing(matches))
				{
					long begin = System.currentTimeMillis();
					for(int m = 0; m < matches.size(); m++)
						if(!matches.get(m).over())
							matches.get(m).update();
					long end = System.currentTimeMillis();
					if(end - begin < delay && gen>10)
					{
						try{Thread.sleep(delay - (int)(end - begin));}catch(InterruptedException e){e.printStackTrace();}
					}
					
					if(System.currentTimeMillis() - trueBegin > 5000)
					{
						break;
					}
				}
				
				count += keepGoings(matches);
				for(int m = 0; m < matches.size(); m++)
				{
					Network n1 = matches.get(m).p1;
					Network n2 = matches.get(m).p2;
					n1.setResults(n1.control.collide, n1.control.lost);
					n2.setResults(n2.control.collide, n2.control.lost);
					matches.get(m).dispose();
				}
			}
			
			tournament();
			
			System.out.println("Ready: " + count);
			System.out.println("\n\n\n");
			
			if(count > networks.size()/2)
				training = false;
			
			gen++;
		}
		
		training = true;
		trainWithEachOther();
	}
	
	public void trainWithEachOther()
	{
		long gen = 1;
		ArrayList<Match> matches = new ArrayList<Match>();
		int MATCHES_PER_BATCH = 1;
		
		
		while(training)
		{
			System.out.println("Generation: " + gen);
			for(int x = 0; x < networks.size(); x += MATCHES_PER_BATCH*2)
			{
				System.out.println("Match: " + x/2);
				matches.clear();
				
				for(int m = 0; m < MATCHES_PER_BATCH; m++)
				{
					matches.add(new Match(new Pong(m), networks.get(x + m * 2), networks.get(x + m * 2 + 1)));
				}
				
				while(keepGoing(matches))
				{
					long begin = System.currentTimeMillis();
					for(int m = 0; m < matches.size(); m++)
						if(!matches.get(m).over())
							matches.get(m).update();
					long end = System.currentTimeMillis();
					if(end - begin < 10 && gen > 20)
					{
						try{Thread.sleep(10 - (int)(end - begin));}catch(InterruptedException e){e.printStackTrace();}
					}
				}
				
				for(int m = 0; m < matches.size(); m++)
				{
					Network n1 = matches.get(m).p1;
					Network n2 = matches.get(m).p2;
					n1.setResults(n1.control.collide, n1.control.lost);
					n2.setResults(n2.control.collide, n2.control.lost);
					matches.get(m).dispose();
				}
			}
			
			tournament();
			System.out.println("\n\n\n");
			gen++;
		}
	}
	
	public int keepGoings(ArrayList<Match> matches)
	{
		int count = 0;
		for(int m = 0; m < matches.size(); m++)
		{
			if(!matches.get(m).over())
				count++;
		}
		return count;
	}
	
	public boolean keepGoing(ArrayList<Match> matches)
	{
		for(int m = 0; m < matches.size(); m++)
		{
			if(!matches.get(m).over())
				return true;
		}
		return false;
	}
	
	public void tournament()
	{
		ArrayList<Network> children = new ArrayList<Network>();
		for(int x = 0; x < networks.size(); x++)
		{
			Network a = networks.get((int)(Math.random() * networks.size()));
			Network b = networks.get((int)(Math.random() * networks.size()));
			Network c = networks.get((int)(Math.random() * networks.size()));
			Network d = networks.get((int)(Math.random() * networks.size()));
			
			Network n1 = (a.collide > b.collide)?a:(a.collide<b.collide)?b:(!a.lost)?a:b;
			Network n2 = (c.collide > d.collide)?c:(c.collide<d.collide)?d:(!c.lost)?c:d;
			
			children.add(Network.merge(n1, n2));
		}
		
		networks = children;
	}
	
	public void initializeNetworks()
	{
		networks = new ArrayList<Network>();
		for(int x = 0; x < 120; x++)
		{
			networks.add(new Network(new int[]{6,3}));
		}
	}
	
	
	
	public static void printArr(double[] arr)
	{
		for(int x = 0; x < arr.length; x++)
			System.out.print(arr[x] + " ");
		System.out.println();
	}
	
	public static void printArr(double[][] arr)
	{
		for(int x = 0; x < arr.length; x++)
		{
			for(int y = 0; y < arr[x].length; y++)
				System.out.print(arr[x][y] + " ");
			System.out.println();
		}
	}
	
	public static void main(String[] args)
	{
		new Trainer();
	}
}
