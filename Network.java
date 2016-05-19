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

    public double[][] propagate (double[][] inputs)
    {
	double[][] result = inputs;
	
	for (int i = 0; i < transformations.size(); i++) {
	    result = transformations.get(i).transSigBias(result);
	}

	return result;
    }

    public static void main(String[] args)
    {
	Network n = new Network(new int[]{2,3,4}, true);

	System.out.println(Arrays.deepToString(n.propagate(new double[][]{{1,2,-1}})));
    }
}
