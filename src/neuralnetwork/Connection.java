package neuralnetwork;

import java.io.Serializable;

/**
 * 
 * @author rjm27
 *
 */
public class Connection implements Serializable
{
	private static final long serialVersionUID = -4425671170051982905L;
	
	protected static int idCount = 0;
	private int id;
	protected Neuron from;
	protected Neuron to;
	protected float weight;

	// Constructors
	// -----------------------------
	
	public Connection(Connection c)
	{
		this.from = c.from;
		this.to = c.to;
		this.weight = c.weight;
	}

	public Connection(Neuron from, Neuron to)
	{
		// shallow copies
		this.from = from;
		this.to = to;
		this.weight = (float) Math.random() * 2 - 1;
		this.id = idCount++;
	}

	public Connection(Neuron from, Neuron to, float weight)
	{
		// shallow copies
		this.from = from;
		this.to = to;
		this.weight = weight;
	}

	// Functional methods
	// ------------------------------

	public void adjustWeight(float deltaWeight)
	{
		weight += deltaWeight;
	}
	
	// Overriden methods
	// -------------------------------
	
	@Override
	public String toString()
	{
		return  "" + from.id + "->" + to.id+ " : " + weight  + "(" + this.id + ")";
	}
}