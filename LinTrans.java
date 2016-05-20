import java.util.Arrays;

public class LinTrans
{
    double[][] theta;

    // Test
    public LinTrans(int inputs, int outputs, boolean b)
    {
	theta = new double[inputs][outputs];

	for(int r = 0; r < theta.length; r++) {
	    for(int c = 0; c < theta[r].length; c++) {
		theta[r][c] = ((r+c)%2 == 0)?1:-1;
	    }
	}
    }

    // Test build
    public LinTrans(double[][] theta)
    {
	this.theta = theta;
    }

    public LinTrans(int inputs, int outputs)
    {
	theta = new double[inputs][outputs];

	for (int r = 0; r < theta.length; r++) {
	    for (int c = 0; c < theta[r].length; c++) {
		theta[r][c] = randomWeight();
	    }
	}
    }
    
    public double[][] transSigBias (double[][] input)
    {
	return addBias(sigmoid(transform(input)));
    }

    public double[][] transSig(double[][] input)
    {
	return sigmoid(transform(input));
    }

    public double[][] transform(double[][] input)
    {
	if(input[0].length != theta.length)
	    throw new ArithmeticException();


	double[][] result = new double[input.length][theta[0].length];

	for(int r = 0; r < result.length; r++) {
	    for(int c = 0; c < result[r].length; c++) {
		double sum = 0;
		
		for(int s = 0; s < input[0].length; s++) {
		    sum += input[r][s] * theta[s][c];
		}

		result[r][c] = sum;
	    }
	}

	return result;
    }

    public static double[][] sigmoid (double[][] input)
    {
	double[][] result = new double[input.length][input[0].length];

	for (int r = 0; r < input.length; r++) {
	    for (int c = 0; c < input[r].length; c++) {
		result[r][c] = 1 / (1 + Math.pow(Math.E, -1 * input[r][c]));
	    }
	}

	return result;
    }

    public static double[][] addBias (double[][] input)
    {
	double[][] result = new double[input.length][input[0].length+1];
	
	for (int r = 0; r < input.length; r++) {
	    for (int c = 0; c < input[0].length; c++) {
		result[r][c+1] = input[r][c];
	    }
	}

	for (int i = 0; i < input.length; i++) {
	    result[i][0] = 1;
	}


	return result;
    }

    public double randomWeight()
    {
	return Math.random();
    }
    
    public String toString()
    {
	String all = "";

	for(int r = 0; r < theta.length; r++) {
	    for(int c = 0; c < theta[r].length; c++) {
		all += theta[r][c] + " ";
	    }
	    all += "\n";
	}

	return all;
    }

    public static void main(String[] args)
    {
	/*
	LinTrans lin = new LinTrans(4,4);
	double[][] d = new double[][]{{1, 2, 3, 4}};
	System.out.println(Arrays.deepToString(lin.transform(d)));

	System.out.println(Arrays.deepToString(sigmoid(new double[][]{{0,0}})));

	System.out.println(Arrays.deepToString(addBias(new double[][]{{0,0},{1,2}})));

	System.out.println(Arrays.deepToString(lin.transSigBias(d)));
	*/
	LinTrans lin2 = new LinTrans(3,2,true);
	double[][] d2 = new double[][]{{1,-1,2}};
	System.out.println(lin2.theta.length);
	System.out.println(d2[0].length);
	System.out.println(Arrays.deepToString(lin2.transSigBias(d2)));
    }
}
