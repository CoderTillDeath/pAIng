class NodeLayer
{
	double[][] theta;
	
	public NodeLayer(int inputs, int outputs)
	{
		theta = new double[inputs][outputs];
		
		for(int x = 0; x < theta.length; x++)
			for(int y = 0; y < theta[x].length; y++)
				theta[x][y] = this.randomWeight();
	}
	
	public NodeLayer(double[][] arr)
	{
		theta = new double[arr.length][arr[0].length];
		
		for(int x = 0; x < theta.length; x++)
			for(int y = 0; y < theta[x].length; y++)
				theta[x][y] = arr[x][y];
	}
	
	public static double randomWeight()
	{
		return Math.random();
	}
	
	public double[][] propagateSigBias(double[][] input)
	{
		return addBias(sigmoid(multiply(input,theta)));
	}
	
	public static double[][] multiply(double[][] arr1, double[][] arr2)
	{
		if(arr1[0].length != arr2.length)
			return null;
		
		double[][] output = new double[arr1.length][arr2[0].length];
		
		for(int x = 0; x < output.length; x++)
		{
			for(int y = 0; y < output[x].length; y++)
			{
				output[x][y] = 0;
				for(int z = 0; z < arr1[x].length; z++)
				{
					output[x][y] += arr1[x][z] * arr2[z][y];
				}
			}
		}
		
		return output;
	}
	
	public double[][] sigmoid(double[][] in)
	{
		for(int x = 0; x < in.length; x++)
		{
			for(int y = 0; y < in[x].length; y++)
			{
				in[x][y] = 1 / (1 + Math.pow(Math.E,-1 * in[x][y]));
			}
		}
		
		return in;
	}
	
	public double[][] addBias(double[][] in)
	{
		double[][] output = new double[in.length][in[0].length + 1];
		
		for(int x = 0; x < in.length; x++)
		{
			output[x][0] = 1;
			for(int y = 0; y < in[x].length; y++)
			{
				output[x][y+1] = in[x][y];
			}
		}
		
		return output;
	}
	
	public static NodeLayer merge(NodeLayer l1, NodeLayer l2)
	{
		double[][] newLayer = new double[l1.theta.length][l1.theta[0].length];
		
		for(int x = 0; x < newLayer.length; x++)
		{
			for(int y = 0; y < newLayer[x].length; y++)
			{
				double rand = Math.random();
				if(rand < .02)
					newLayer[x][y] = randomWeight();
				else
					if(rand < .51)
						newLayer[x][y] = l1.theta[x][y];
					else
						newLayer[x][y] = l2.theta[x][y];
			}
		}
		
		return new NodeLayer(newLayer);
	}
	
	public String toString()
	{
		String all = "";
		
		for(int x = 0; x < theta.length; x++)
		{
			for(int y = 0; y < theta[x].length; y++)
			{
				all += theta[x][y] + " ";
			}
			all += "\n";
		}
		
		return all;
	}
	
	public static void main(String[] args)
	{
		System.out.println(new NodeLayer(2,3));
	}
}
