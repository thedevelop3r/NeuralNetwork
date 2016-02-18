package neuralnetwork;

import java.io.Serializable;
import java.util.*;

public class Neuron implements Serializable
{
	private static final long serialVersionUID = -3178838640951519288L;

	public static int idCount = 0;

	protected float output = 0;
	protected float deltaOutput = 0;
	protected ArrayList<Connection> connections = new ArrayList<Connection>();
	protected boolean bias = false;
	protected final int id;

	// Constructors
	// ------------------------------------

	public Neuron(Neuron n)
	{
		this.output = n.getOutput();
		this.connections = n.getConnections();
		this.bias = n.isBias();
		this.id = n.getId();
	}

	public Neuron()
	{
		this.id = idCount++;
	}

	public Neuron(int output)
	{
		this.output = output;
		this.bias = true;
		this.id = idCount++;
	}

	// Functional methods
	// --------------------------------------

	public void addConnection(Connection c)
	{
		connections.add(c);
	}
	
	public float sumInputs()
	{
		float sum = 0.0f;
		for(Connection c : connections)
		{
			if(c.to == this)
			{
				sum += c.from.output * c.weight;
			}
		}
		return sum;
	}
	
	public float calcOutput()
	{
		output = sigmoid(sumInputs());
		return output;
	}
	
	public static float sigmoid(float x)
	{
		return 1.0f / (1.0f + (float) Math.exp(-x));
	}
	
	// Overridden methods
	// ------------------------------------

	@Override
	public String toString()
	{
		String output = "" + id + " [";
		for (Connection c : connections)
		{
			output += c + ", ";
		}
		return output.substring(0, output.length() - 2) + "]";
	}

	@Override
	public boolean equals(Object o)
	{
		// short circuit evaluation and in same class allow this to compile and
		// function properly
		return o instanceof Neuron && this.id == ((Neuron) o).id;
	}

	// Getters and Setters
	// -------------------------------------

	public float getOutput()
	{
		return output;
	}

	public void setOutput(float output)
	{
		this.output = output;
	}

	public ArrayList<Connection> getConnections()
	{
		return new ArrayList<Connection>(this.connections);
	}

	public void setConnections(List<Connection> connections)
	{
		this.connections = new ArrayList<Connection>(connections);
	}

	public boolean isBias()
	{
		return bias;
	}

	public void setBias(boolean bias)
	{
		this.bias = bias;
	}

	public int getId()
	{
		return this.id;
	}

	public float getDeltaOutput()
	{
		return deltaOutput;
	}

	public void setDeltaOutput(float deltaOutput)
	{
		this.deltaOutput = deltaOutput;
	}
}