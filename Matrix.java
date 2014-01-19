/**
 * Andrew Duryea
 * September 30, 2013
 * Matrix.java
 *
 * A 2D Matrix, used for transformations of Objects.
 *
 * September 30, 2013: moved into a separate file and Graphics3D package
 */

package Graphics3D;

public class Matrix
{
	double[][] matrix;

	int rows;
	int cols;

	Matrix(int rows, int cols)
	{
		this.rows = rows;
		this.cols = cols;

		matrix = new double[rows][cols];

		//initilize everything to 0
		for(int i = 0; i < this.rows; i++)
		{
			for(int j = 0; j < this.cols; j++)
			{
				this.setValue(i, j, 0);
			}
		}
	}

	public void setValue(int row, int col, double value)
	{
		matrix[row][col] = value;
	}

	public double getValue(int row, int col)
	{
		return matrix[row][col];
	}

	public Matrix multiply(Matrix m)
	{
		if(this.cols != m.rows)
			return null;

		Matrix ret = new Matrix(this.rows, m.cols);

		for(int i = 0; i < ret.rows; i++)
		{
			for(int j = 0; j < ret.cols; j++)
			{
				double val = 0;

				for(int k = 0; k < this.cols; k++)
				{
					val += this.getValue(i,k) * m.getValue(k,j);
				}

				ret.setValue(i, j, val);
			}
		}

		return ret;
	}

	public String toString()
	{
		String ret = "";

		for(int i = 0; i < this.rows; i++)
		{
			for(int j = 0; j < this.cols; j++)
			{
				ret = ret + this.getValue(i,j) + " ";
			}
			ret = ret + "\n";
		}

		return ret;
	}
}