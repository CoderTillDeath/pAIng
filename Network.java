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
	
	for (int i = 0; i < transformations.size(); i++) {
	    result = transformations.get(i).transSigBias(result);
	}

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

    public void backpropagation(Matrix inputs, Matrix y, int times)
    {
	if(inputs.rows() != y.rows()) throw new ArithmeticException();

	for (int t = 0; t < times; t++) {
	    for (int r = 0; r < inputs.rows(); r++) {
		backpropInput(inputs.row(r),y.row(r),0);
	    }
	}
    }

    public Matrix backpropInput (Matrix inputs, Matrix y, int layer)
    {
	if(layer == transformations.size())
	{
	    System.out.println(inputs.removeColumn(0));
	    return Matrix.subtract(y,inputs.removeColumn(0));
	}

	LinTrans trans = transformations.get(layer);

	Matrix result = trans.transSigBias(inputs); 

	Matrix error = backpropInput(result,y,layer+1);

	
	
	return null;
    }

    public double cost(Matrix inputs, Matrix y)
    {
	return 0;
    }

    public static void main(String[] args)
    {
	
	Network n = new Network(new int[]{2,2,1}, true);
	
	Matrix m = new Matrix(new double[][]{{1,.35,.9}});

	n.backpropagation(m, new Matrix(new double[][]{{2}}), 1);

	/*
	ArrayList<double[][]> arr = new ArrayList<>();

	arr.add(new double[][]{{.1,.5},{.9,.8}});

	Network n = new Network(arr);

	System.out.println(Arrays.deepToString(n.propagateWithoutBias(new double[][]{{.2,.4}})));*/

	
    }
}
