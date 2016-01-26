package neuralnetwork;

/**
 * 
 * @author rjm27
 *
 */
public class Connection
{
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
		this.weight = (float) (Math.random() * 2 - 1);
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
		return  "" + from.getId() + "->" + to.getId() + " : " + weight;
	}

	// Getters and Setters
	// --------------------------------

	public Neuron getFrom()
	{
		return new Neuron(this.from);
	}

	public void setFrom(Neuron from)
	{
		this.from = new Neuron(from);
	}

	public Neuron getTo()
	{
		return new Neuron(this.to);
	}

	public void setTo(Neuron to)
	{
		this.to = new Neuron(to);
	}

	public float getWeight()
	{
		return weight;
	}

	public void setWeight(float weight)
	{
		this.weight = weight;
	}

}