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

    public double[][] propagate (double[][] inputs)
    {
	double[][] result = inputs;
	
	for (int i = 0; i < transformations.size(); i++) {
	    result = transformations.get(i).transSigBias(result);
	}

	return result;
    }

    public double[][] propagateWithoutBias(double[][] inputs)
    {
	double[][] result = inputs;

	for (int i = 0; i < transformations.size(); i++) {
	    result = transformations.get(i).transSig(result);
	}

	return result;
    }

    public static void main(String[] args)
    {
	
	Network n = new Network(new int[]{2,3,2}, true);

	System.out.println(Arrays.deepToString(n.propagate(new double[][]{{1,2,-1}})));

	/*
	ArrayList<double[][]> arr = new ArrayList<>();

	arr.add(new double[][]{{.1,.5},{.9,.8}});

	Network n = new Network(arr);

	System.out.println(Arrays.deepToString(n.propagateWithoutBias(new double[][]{{.2,.4}})));*/

	
    }
}
