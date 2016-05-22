import java.util.ArrayList;
import java.util.Arrays;

public class Network {
    ArrayList<LinTrans> transformations;
    
    public Network(int[] layers)
    {
	transformations = new ArrayList<LinTrans>();
	
	for (int i = 0; i < layers.length-1; i++) {
	    transformations.add(new LinTrans(layers[i]+1, layers[i+1]));
	}
    }

    public Network(int[] layers, boolean b)
    {
	transformations = new ArrayList<LinTrans>();
	
	for (int i = 0; i < layers.length-1; i++) {
	    transformations.add(new LinTrans(layers[i]+1, layers[i+1], b));
	}
    }

    public Network(ArrayList<double[][]> thetas)
    {
	transformations = new ArrayList<LinTrans>();
	
	for (int i = 0; i < thetas.size(); i++) {
	    transformations.add(new LinTrans(thetas.get(i)));
	}
    }

    public Matrix propagate (Matrix inputs)
    {
	Matrix result = inputs;
	
	for (int i = 0; i < transformations.size() - 1; i++) {
	    result = transformations.get(i).transSigBias(result);
	}

	result = transformations.get(transformations.size()-1).transSig(result);

	return result;
    }

    public Matrix propagateWithoutBias(Matrix inputs)
    {
	Matrix result = inputs;

	for (int i = 0; i < transformations.size(); i++) {
	    result = transformations.get(i).transSig(result);
	}

	return result;
    }

    public Matrix backpropagationWithoutBias(Matrix inputs, Matrix y, int layer)
    {
	if(layer == transformations.size())
	{
	    Matrix diff = Matrix.subtract(y,inputs);
	    return diff;
	}
	
	LinTrans trans = transformations.get(layer);

	Matrix result = trans.transSig(inputs);

	Matrix sigPrime = Matrix.mult(result,Matrix.sub(1,result));

	Matrix error = Matrix.mult(backpropagationWithoutBias(result,y,layer+1),sigPrime);

	trans.theta = Matrix.add(trans.theta,Matrix.crossProduct(inputs.transpose(),error));

	Matrix m = Matrix.crossProduct(error,trans.theta.transpose());
	
	return m;
    }

    public void backpropagation(Matrix inputs, Matrix y, double lr, int times)
    {
	if(inputs.rows() != y.rows()) throw new ArithmeticException();

	for (int t = 0; t < times; t++) {
	    for (int r = 0; r < inputs.rows(); r++) {
		backpropInput(inputs.row(r),y.row(r),lr,0);
	    }
	}
    }

    public Matrix backpropInput (Matrix inputs, Matrix y, double lr, int layer)
    {
	if(layer == transformations.size())
	{
	    Matrix diff = Matrix.subtract(y,inputs.removeColumn(0));
	    return diff;
	}
	
	LinTrans trans = transformations.get(layer);

	Matrix result = trans.transSig(inputs);

	Matrix sigPrime = Matrix.mult(result,Matrix.sub(1,result));

	Matrix error = Matrix.mult(backpropInput(LinTrans.addBias(result),y,lr,layer+1),sigPrime);

	trans.theta = Matrix.add(trans.theta,Matrix.scale(Matrix.crossProduct(inputs.transpose(),error), lr));

	Matrix m = Matrix.crossProduct(error,trans.theta.removeRow(0).transpose());
	
	return m;
    }

    public double cost(Matrix inputs, Matrix y)
    {
	Matrix res = propagate(inputs);
	double c = 0;
	
	for (int i = 0; i < res.rows(); i++) {
	    for (int k = 0; k < res.columns(); k++) {
		c += y.get(i,k) * Math.log(res.get(i,k)) + (1-y.get(i,k))*(Math.log(1 - res.get(i,k)));
	    }
	}

	return c;
    }

    public static void main(String[] args)
    {
	Network n = new Network(new int[]{2,2,1});

	Matrix X = new Matrix(new double[][]{{1,0,0},{1,0,1},{1,1,0},{1,1,1}});
	Matrix y = new Matrix(new double[][]{{1},{0},{0},{1}});

	System.out.println(n.cost(X,y));
	n.backpropagation(X, y, 2.5, 200);
	System.out.println(n.cost(X,y));
	System.out.println(n.propagate(new Matrix(new double[][]{{1,0,0}})));
	
	/*
	ArrayList<double[][]> arr = new ArrayList<>();

	arr.add(new double[][]{{.1,.4},{.8,.6}});
	arr.add(new double[][]{{.3},{.9}});
	
	Network n = new Network(arr);
	
	Matrix m = new Matrix(new double[][]{{.35,.9}});

	n.backpropagationWithoutBias(m, new Matrix(new double[][]{{.5}}), 0);
	*/
	
	/*
	ArrayList<double[][]> arr = new ArrayList<>();

	arr.add(new double[][]{{.1,.5},{.9,.8}});

	Network n = new Network(arr);

	System.out.println(Arrays.deepToString(n.propagateWithoutBias(new double[][]{{.2,.4}})));*/

	
    }
}
