import java.util.Arrays;

public class LinTrans
{
    Matrix theta;

    // Test
    public LinTrans(int inputs, int outputs, boolean b)
    {
	theta = new Matrix(inputs,outputs);

	for(int r = 0; r < theta.rows(); r++) {
	    for(int c = 0; c < theta.columns(); c++) {
		theta.set(r,c,((r+c)%2 == 0)?1:-1);
	    }
	}
    }

    // Test build
    public LinTrans(double[][] theta)
    {
	this.theta = new Matrix(theta);
    }

    public LinTrans(int inputs, int outputs)
    {
	theta = new Matrix(inputs,outputs);

	for (int r = 0; r < theta.rows(); r++) {
	    for (int c = 0; c < theta.columns(); c++) {
		theta.set(r,c,randomWeight());
	    }
	}
    }
    
    public Matrix transSigBias (Matrix input)
    {
	return addBias(sigmoid(transform(input)));
    }

    public Matrix transSig(Matrix input)
    {
	return sigmoid(transform(input));
    }

    public Matrix transform(Matrix input)
    {
	if(input.columns() != theta.rows())
	    throw new ArithmeticException();

	Matrix result = Matrix.crossProduct(input,theta);

	return result;
    }

    public static Matrix sigmoid (Matrix input)
    {
	Matrix res = new Matrix(input.rows(),input.columns());

	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		res.set(r,c,1 / (1 + Math.pow(Math.E, -1 * input.get(r,c))));
	    }
	}

	return res;
    }

    public static Matrix addBias (Matrix input)
    {
	Matrix result = new Matrix(Matrix.ones(input.rows(),1),input,true);

	return result;
    }

    public double randomWeight()
    {
	return Math.random();
    }
    
    public String toString()
    {
	String all = "";

	for(int r = 0; r < theta.rows(); r++) {
	    for(int c = 0; c < theta.columns(); c++) {
		all += theta.get(r,c) + " ";
	    }
	    all += "\n";
	}

	return all;
    }

    public static void main(String[] args)
    {
	/*
	LinTrans lin = new LinTrans(4,4);
	Matrix d = new Matrix(new double[][]{{1, 2, 3, 4}});
	System.out.println(lin);
	System.out.println(lin.transform(d));

	System.out.println(sigmoid(new Matrix(new double[][]{{0,0}})));

	System.out.println(addBias(new Matrix(new double[][]{{0,0},{1,2}})));

	System.out.println(lin.transSigBias(d));
	*/
	LinTrans lin2 = new LinTrans(3,2,true);
	Matrix d2 = new Matrix(new double[][]{{1,-1,2}});
	System.out.println(lin2.theta.rows());
	System.out.println(d2.columns());
	System.out.println(lin2.transSigBias(d2));
    }
}
