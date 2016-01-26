package neuralnetwork;
import java.util.*;


public class Network
{
	
	protected ArrayList<Neuron[]> network = new ArrayList<Neuron[]>();
	public static final float LEARNING_CONSTANT = 1f;
	
	public Network(int... layers)
	{
		// Generate Neurons
		
		// create a layer for each passed in integer
		// give all of them a bias neuron except the output layer
		for(int i = 0; i < layers.length - 1; i++)
		{
			network.add(new Neuron[layers[i] + 1]);
			// create normal neurons
			for(int j = 0; j < network.get(i).length - 1; j++)
			{
				network.get(i)[j] = new Neuron();
			}
			// create bias neuron
			network.get(i)[network.get(i).length - 1] = new Neuron(1);
		}
		// create outputs (last row in network)
		network.add(new Neuron[layers[layers.length - 1]]);
		for(int i = 0; i < network.get(network.size() - 1).length; i++)
			network.get(network.size()-1)[i] = new Neuron();
			
		
		// Generate Connections
		for(int i = 0; i < network.size() - 1; i++)
		{
			for(int j = 0; j < network.get(i).length; j++)
			{
				for(int k = 0; k < network.get(i+1).length; k++)
				{
					network.get(i)[j].addConnection(new Connection(network.get(i)[j], network.get(i+1)[k]));
					network.get(i+1)[k].addConnection(new Connection(network.get(i)[j], network.get(i+1)[k]));
				}
				
				// remove input connectoins to bias neuron
				if(j == network.get(i).length - 1)
				{
					for(int k = network.get(i)[j].connections.size() - 1; k >= 0; k--)
					{
						if(network.get(i)[j].connections.get(k).to.equals(network.get(i)[j]))
						{
							network.get(i)[j].connections.remove(k);
						}
					}
				}
			}
		}
	}
	
	// Functional methods
	// --------------------------------------------------------
	
	// probs works
	public float[] feedForward(float...inputs)
	{
		// set input neurons
		for(int i = 0; i < network.get(0).length - 1; i++)
		{
			network.get(0)[i].setOutput(inputs[i]);
		}
		
		
		// calculate the output for each hidden layer
		for(int i = 1; i < network.size() - 1; i++)
		{
			for(int j = 0; j < network.get(i).length - 1; j++)
			{
				network.get(i)[j].calcOutput();
			}
		}
		
		// calculate the output for the output layer
		for(int j = 0; j < network.get(network.size() - 1).length; j++)
		{
			network.get(network.size() - 1)[j].calcOutput();
		}
		
		// get and return the outputs of the network
		float[] outputs = new float[network.get(network.size() - 1).length];
		
		for(int i = 0; i < network.get(network.size() - 1).length; i++)
		{
			outputs[i] = network.get(network.size() - 1)[i].getOutput();
		}
		
		return outputs;
	}
	
	// some sorcery of calculus
	public float[] train(float[] inputs, float[] answers)
	{
		float[] outputs = feedForward(inputs);
		
		// compute error at output level
		for(int i = 0; i < outputs.length; i++)
		{
			// sigmoid derivative * diff between known and guess
			// S_k = O_k * (1 - O_k) *  (t_k - O_k) 
			network.get(network.size() - 1)[i].
					setDeltaOutput(  
					outputs[i] * (1 - outputs[i]) * (answers[i] - outputs[i])
					);
		}
		
		// compute error at all subsequent levels
		
		// iterate backwards over the remaining layers excluding the input layer
		for(int i = network.size() - 2; i > 0; i--)
		{
			// iterate over the given layer
			for(int j = 0; j < network.get(i).length; j++)
			{		
				float sum = 0.0f;
				// iterate over connections in a neuron of layer j
				for(Connection c : network.get(i)[j].getConnections())
				{
					if(c.from.equals(network.get(i)[j]))
					{
						for(int k = 0; k < network.get(i+1).length; k++)
						{
							sum += c.weight * network.get(i+1)[k].deltaOutput;
						}
					}			
				}
				
				
				float result = network.get(i)[j].getOutput();
				// sum of weighted outputs * sigmoid derivative
				network.get(i)[j].setDeltaOutput(sum *  result * (1 - result));
			}
		}
		
		// update all weights
		for(int i = 0; i < network.size(); i++)
		{
			for(int j = 0; j < network.get(i).length; j++)
			{
				for(Connection c : network.get(i)[j].connections)
				{
					c.adjustWeight(LEARNING_CONSTANT * c.to.deltaOutput * c.from.output);
				}
			}
		}
		
		return outputs;
	}
	
	// Overriden methods
	// ---------------------------------------------------------
	
	@Override
	public String toString()
	{
		String output = "";
		int layerNum = 0;
		for(Neuron[] layer : network)
		{
			output += "Layer: " + layerNum++ + "\n";
			for(Neuron n : layer)
			{
				output += n + "\n";
			}
			output += "\n";
		}
		return output;
	}
}
