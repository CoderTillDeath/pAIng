import java.util.Arrays;

public class Matrix
{
    double[][] arr;

    public Matrix(double[][] arr)
    {
	this.arr = arr;
    }

    public Matrix(int rows, int columns)
    {
	arr = new double[rows][columns];
    }

    public Matrix(Matrix m1, Matrix m2, boolean side)
    {	
	if(side)
	{
	    arr = new double[m1.rows()][m1.columns() + m2.columns()];
	    
	    if(m1.rows() != m2.rows()) throw new ArithmeticException(dimensionCompare(m1,m2));

	    for (int r = 0; r < m1.rows(); r++) {
		for (int c = 0; c < m1.columns(); c++) {
		    arr[r][c] = m1.get(r,c);
		}
	    }

	    for (int r = 0; r < m2.rows(); r++) {
		for (int c = 0; c < m2.columns(); c++) {
		    arr[r][c + m1.columns()] = m2.get(r,c); 
		}
	    }
	}
	else
	{
	    arr = new double[m1.rows() + m2.rows()][m1.columns()];
	    if(m1.columns() != m2.columns()) throw new ArithmeticException(dimensionCompare(m1,m2));

	    for (int r = 0; r < m1.rows(); r++) {
		for (int c = 0; c < m1.columns(); c++) {
		    arr[r][c] = m1.get(r,c);
		}
	    }

	    for (int r = 0; r < m2.rows(); r++) {
		for (int c = 0; c < m2.columns(); c++) {
		    arr[r + m1.rows()][c] = m2.get(r,c); 
		}
	    }
	}
    }

    public double get(int r, int c)
    {
	return arr[r][c];
    }

    public void set(int r, int c, double v)
    {
	arr[r][c] = v;
    }

    public int rows()
    {
	return arr.length;
    }

    public int columns()
    {
	return arr[0].length;
    }

    public Matrix row(int x)
    {
	double[][] mat = new double[1][arr[0].length];

	for (int i = 0; i < arr[0].length; i++) {
	    mat[0][i] = arr[x][i];
	}
	
	return new Matrix(mat);
    }

    public Matrix column(int x)
    {
	double[][] mat = new double[arr.length][1];

	for (int i = 0; i < arr.length; i++) {
	    mat[i][0] = arr[i][x];
	}

	return new Matrix(mat);
    }

    public Matrix removeRow(int x)
    {
	double[][] mat = new double[arr.length-1][arr[0].length];

	for (int r = 0; r < mat.length; r++) {
	    for (int c = 0; c < mat[0].length; c++) {
		mat[r][c] = (r>=x)?arr[r+1][c]:arr[r][c];
	    }
	}

	return new Matrix(mat);
    }

    public Matrix removeColumn(int x)
    {
	double[][] mat = new double[arr.length][arr[0].length-1];

	for (int r = 0; r < mat.length; r++) {
	    for (int c = 0; c < mat[0].length; c++) {
		mat[r][c] = (c>=x)?arr[r][c+1]:arr[r][c];
	    }
	}

	return new Matrix(mat);
    }

    public Matrix transpose()
    {
	Matrix m = new Matrix(columns(),rows());

	for (int r = 0; r < m.rows(); r++) {
	    for (int c = 0; c < m.columns(); c++) {
		m.set(r,c,get(c,r));
	    }
	}

	return m;
    }

    public static Matrix ones(int rows, int columns)
    {
	Matrix res = new Matrix(rows,columns);

	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		res.set(r,c,1);
	    }
	}

	return res;
    }

    public static Matrix crossProduct(Matrix a1, Matrix a2)
    {
	if(a1.columns() != a2.rows()) throw new ArithmeticException(dimensionCompare(a1,a2));

	Matrix res = new Matrix(a1.rows(), a2.columns());

	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		double sum = 0;

		for (int i = 0; i < a1.columns(); i++) {
		    sum += a1.get(r,i) * a2.get(i,c);
		}

		res.set(r,c,sum);
	    }
	}

	return res;
    }

    public static Matrix add(Matrix a1, Matrix a2)
    {
	if(a1.columns() != a2.columns() || a1.rows() != a2.rows()) throw new ArithmeticException(dimensionCompare(a1,a2));

	Matrix res = new Matrix(a1.rows(),a1.columns());

	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		res.set(r,c,a1.get(r,c) + a2.get(r,c));
	    }
	}

	return res;
    }

    public static Matrix sub(int a, Matrix b)
    {
	Matrix res = new Matrix(b.rows(),b.columns());

	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		res.set(r,c,a - b.get(r,c));
	    }
	}

	return res;
    }
    
    public static Matrix scale(Matrix a1, double s)
    {
	Matrix res = new Matrix(a1.rows(), a1.columns());

	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		res.set(r,c, a1.get(r,c) * s);
	    }
	}

	return res;
    }

    public static Matrix subtract(Matrix a1, Matrix a2)
    {
	return add(a1,scale(a2,-1));
    }

    public static Matrix mult(Matrix a1, Matrix a2)
    {
	if(a1.columns() != a2.columns() || a1.rows() != a2.rows()) throw new ArithmeticException(dimensionCompare(a1,a2));

	Matrix res = new Matrix(a1.rows(),a1.columns());
	
	for (int r = 0; r < res.rows(); r++) {
	    for (int c = 0; c < res.columns(); c++) {
		res.set(r,c,a1.get(r,c)*a2.get(r,c));
	    }
	}

	return res;
    }

    public static String dimensionCompare(Matrix a1, Matrix a2)
    {
	return "Arg 1: " + a1.rows() + "x" + a1.columns() + ", Arg 2: " + a2.rows() + "x" + a2.columns();
    }

    public String toString() {
	String all = "";

	for (int r = 0; r < rows(); r++) {
	    for (int c = 0; c < columns(); c++) {
		all += arr[r][c] + " ";
	    }
	    all += "\n";
	}
	
	return all;
    }

    public static void main(String[] args) {
	Matrix a1 = new Matrix(new double[][]{{.35,.9},{0,0}});
	Matrix a2 = new Matrix(new double[][]{{.1,.4},{.8,.6}});

	System.out.println(new Matrix(a1,a2,true));

	System.out.println(a1.row(0));
	System.out.println(a1.column(1));

	System.out.println(a1.removeColumn(0));
    }
}
