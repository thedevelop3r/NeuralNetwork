package neuralnetwork;

import java.io.Serializable;
import java.util.*;

public class Network implements Serializable
{
	private static final long serialVersionUID = 8761956216073345540L;
	protected ArrayList<Neuron[]> network = new ArrayList<Neuron[]>();
	
	public float learningRate = 2.0f;
	
	public Network(int... layers)
	{
		// Generate Neurons

		// create a layer for each passed in integer
		// give all of them a bias neuron except the output layer
		for (int i = 0; i < layers.length - 1; i++)
		{
			network.add(new Neuron[layers[i] + 1]);
			// create normal neurons
			for (int j = 0; j < network.get(i).length - 1; j++)
			{
				network.get(i)[j] = new Neuron();
			}
			// create bias neuron
			network.get(i)[network.get(i).length - 1] = new Neuron(1);
		}
		// create outputs (last row in network)
		network.add(new Neuron[layers[layers.length - 1]]);
		for (int i = 0; i < network.get(network.size() - 1).length; i++)
			network.get(network.size() - 1)[i] = new Neuron();

		// Generate Connections
		for (int i = 0; i < network.size() - 1; i++)
		{
			for (int j = 0; j < network.get(i).length; j++)
			{
				for (int k = 0; k < network.get(i + 1).length; k++)
				{
					if (i + 1 == network.size() - 1 || k < network.get(i + 1).length - 1)
					{
						Connection c = new Connection(network.get(i)[j], network.get(i + 1)[k]);
						network.get(i)[j].connections.add(c);
						network.get(i + 1)[k].connections.add(c);
					}
				}
			}
		}
	}

	// Functional methods
	// --------------------------------------------------------

	// http://www.hostmath.com/Show.aspx?Code=%20\delta_{o}%20%3D%20%28out_o%20-%20target_o%29%20\space%20out_{o}%20\space%20%281%20-%20out_{o}%29\\%0A%20\delta_h%20%3D%20%28\sum_o{\delta_o%20\space%20w_{ho}}%29%20\space%20out_h%20\space%20%281%20-%20out_h%29\\%0A\Delta%20w_i%20%3D%20\eta%20\space%20\delta_o%20\space%20w_i\\
	public float[] feedForward(float... inputs)
	{
		// set input neurons
		for (int i = 0; i < network.get(0).length - 1; i++)
		{
			network.get(0)[i].output = inputs[i];
		}

		// calculate the output for each hidden layer
		for (int i = 1; i < network.size() - 1; i++)
		{
			for (int j = 0; j < network.get(i).length - 1; j++)
			{
				network.get(i)[j].calcOutput();
			}
		}

		// calculate the output for the output layer
		for (int j = 0; j < network.get(network.size() - 1).length; j++)
		{
			network.get(network.size() - 1)[j].calcOutput();
		}

		// get and return the outputs of the network
		float[] outputs = new float[network.get(network.size() - 1).length];

		for (int i = 0; i < network.get(network.size() - 1).length; i++)
		{
			outputs[i] = network.get(network.size() - 1)[i].output;
		}

		return outputs;
	}

	// some sorcery of calculus
	public float[] train(float[] inputs, float[] answers)
	{
		float[] outputs = feedForward(inputs);

		// compute error at output level
		
		for(int i = 0; i < network.get(network.size() - 1).length; i++)
		{
			// formula in latex
			//\delta_{o} = (out_o - target_o) \space out_{o} \space (1 - out_{o})\\
			network.get(network.size() - 1)[i].deltaOutput = (outputs[i] - answers[i]) * outputs[i] * (1 - outputs[i]);
		}
		
		// compute error at all subsequent levels

		// iterate over all layers excluding output backwards
		for(int i = network.size() - 2; i >= 0; i--)
		{
			// iterate over given layer excluding the bias neuron
			for(int j = 0; j < network.get(i).length - 1; j++)
			{
				float sum = 0.0f;
				for(Connection c : network.get(i)[j].connections)
				{
					if(c.from == network.get(i)[j])
					{
						sum += c.to.deltaOutput * c.weight;
					}
				}
				// formula in latex
				//  \delta_h = (\sum_o{\delta_o \space w_{ho}}) \space out_h \space (1 - out_h)\\
				network.get(i)[j].deltaOutput = sum * network.get(i)[j].output * (1 - network.get(i)[j].output);
			}
		}
		
		// update all weights

		// iterate forwards over all layers
		for(int i = 0; i < network.size(); i++)
		{
			// iterate over the given layer excluding the bias
			for(int j = 0; j < network.get(i).length; j++)
			{
				for(Connection c : network.get(i)[j].connections)
				{
					if(c.from == network.get(i)[j])
					{
						// formula in latex
						// \Delta w_i = \eta \space \delta_o \space w_i\\
						c.weight -= c.to.deltaOutput * c.from.output * learningRate;
					}
				}
			}
		}
		
		return outputs;
	}

	// Overridden methods
	// ---------------------------------------------------------

	@Override
	public String toString()
	{
		String output = "";
		int layerNum = 0;
		for (Neuron[] layer : network)
		{
			output += "Layer: " + layerNum++ + "\n";
			for (Neuron n : layer)
			{
				output += n + "\n";
			}
			output += "\n";
		}
		return output;
	}
}
