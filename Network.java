
class Network
{
	double[][] theta1, theta2;
	Pong p;
	Paddle control;
	Paddle notControl;
	boolean lost;
	int collide;
	int numFeatures = 6;
	
	public Network()
	{
		theta1 = new double[numFeatures][numFeatures];
		
		for(int x = 0; x < theta1.length; x++)
		{
			for(int y = 0; y < theta1[x].length; y++)
			{
				theta1[x][y] = randomWeight();
			}
		}
		
		theta2 = new double[numFeatures][3];
		
		for(int x = 0; x < theta2.length; x++)
		{
			for(int y = 0; y < theta2[x].length; y++)
			{
				theta2[x][y] = randomWeight();
			}
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
		double[] X = new double[numFeatures];
			X[0] = p.b.x/p.length;
			X[1] = (p.b.y - control.y)/ p.height;
			X[2] = (p.b.y - notControl.y)/ p.height;
			X[3] = (p.b.speed - 3)/2;
			X[4] = Math.cos(p.b.angle);
			X[5] = Math.sin(p.b.angle);
		double[] result = null;
		
		if(layer2)
			result = sigmoid(multiply(sigmoid(multiply(X,theta1)), theta2));
		else
			result = sigmoid(multiply(X,theta2));
			
		int greatest = 0;
		
		for(int x = 0; x < result.length; x++)
		{
			if(result[greatest] < result[x])
				greatest = x;
		}
		
		switch(greatest)
		{
			case 0:	control.moveUp();
					break;
			case 1:	control.moveDown();
					break;
			case 2:	control.notMoving();
					break;
		}
	}
	
	public double[] sigmoid(double[] z)
	{
		for(int x = 0; x < z.length; x++)
		{
			z[x] = 1 / (1 + Math.pow(Math.E, -z[x]));
		}
		
		return z;
	}
	
	public double[] multiply(double[] X, double[][] theta)
	{
		double[] product = new double[theta[0].length];
		
		for(int i = 0; i < product.length; i++)
		{
			double sum = 0;
			for(int j = 0; j < theta[i].length; j++)
			{
				sum += X[i] * theta[i][j];
			}
			product[i] = sum;
		}
		
		return product;
	}
	
	public static Network merge(Network n1, Network n2)
	{
		Network child = new Network();
		
		for(int x = 0; x < child.theta1.length; x++)
		{
			for(int y = 0; y < child.theta1[x].length; y++)
			{
				double mutate = Math.random();
				if(mutate > .05)
					child.theta1[x][y] = ((int)(Math.random() * 2) == 0)?n1.theta1[x][y]:n2.theta1[x][y];
				else
					child.theta1[x][y] = randomWeight();
			}
		}
		
		for(int x = 0; x < child.theta2.length; x++)
		{
			for(int y = 0; y < child.theta2[x].length; y++)
			{
				double mutate = Math.random();
				if(mutate > .05)
					child.theta2[x][y] = ((int)(Math.random() * 2) == 0)?n1.theta2[x][y]:n2.theta2[x][y];
				else
					child.theta2[x][y] = randomWeight();
			}
		}
		
		return child;
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
		return Math.random() * 2 - 1;
	}
}
