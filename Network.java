import java.util.ArrayList;

class Network
{
	Pong p;
	Paddle control;
	Paddle notControl;
	boolean lost;
	int collide;
	int numFeatures = 6;
	ArrayList<NodeLayer> layers;
	
	public Network()
	{
		layers = new ArrayList<NodeLayer>();
	}
	
	public Network(int[] nodes)
	{
		layers = new ArrayList<NodeLayer>();
		for(int x = 0; x < nodes.length - 1; x++)
		{
			layers.add(new NodeLayer(nodes[x] + 1, nodes[x+1]));
		}
	}
	
	public void setResults(int c, boolean l)
	{
		collide = c;
		lost = l;
	}
	
	public int getCollide()
	{
		return collide;
	}
	
	public boolean lost()
	{
		return lost;
	}
	
	public void setPong(Pong p, boolean p1)
	{
		this.p = p;
		control = p1?p.p1:p.p2;
		notControl = p1?p.p2:p.p1;
	}
	
	public void propagate(boolean layer2)
	{
		double[][] X = new double[1][numFeatures+1];
			X[0][0] = 1;
			X[0][1] = p.b.x/p.length;
			X[0][2] = (p.b.y - control.y)/ p.height;
			X[0][3] = (p.b.y - notControl.y)/ p.height;
			X[0][4] = (p.b.speed - 3)/2;
			X[0][5] = Math.cos(p.b.angle);
			X[0][6] = Math.sin(p.b.angle);
		double[][] result = X;
		
		for(int x = 0; x < layers.size(); x++)
		{
			result = layers.get(x).propagateSigBias(result);
		}
			
		int greatest = 1;
		
		for(int x = 1; x < result[0].length; x++)
		{
			if(result[0][greatest] < result[0][x])
				greatest = x;
		}
		
		switch(greatest)
		{
			case 1:	control.moveUp();
					break;
			case 2:	control.moveDown();
					break;
			case 3:	control.notMoving();
					break;
		}
	}
	
	public static Network merge(Network n1, Network n2)
	{
		Network child = new Network();
		
		for(int x = 0; x < n1.numLayers(); x++)
			child.addLayer(NodeLayer.merge(n1.getLayer(x), n2.getLayer(x)));
		
		return child;
	}
	
	public void addLayer(NodeLayer n)
	{
		layers.add(n);
	}
	
	public int numLayers()
	{
		return layers.size();
	}
	
	public NodeLayer getLayer(int x)
	{
		return layers.get(x);
	}
	
	public void printArr(double[] arr)
	{
		for(int x = 0; x < arr.length; x++)
		{
			System.out.print(arr[x] + " ");
		}
		System.out.println();
	}
	
	public static double randomWeight()
	{
		return Math.random();
	}
}
