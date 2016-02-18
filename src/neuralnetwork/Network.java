package neuralnetwork;

import java.util.*;

public class Network
{

	protected ArrayList<Neuron[]> network = new ArrayList<Neuron[]>();
	public static final float LEARNING_CONSTANT = 2.0f;

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
						network.get(i)[j].addConnection(c);
						network.get(i + 1)[k].addConnection(c);
					}
				}
			}
		}

	}

	// Functional methods
	// --------------------------------------------------------

	// probs works
	public float[] feedForward(float... inputs)
	{
		// set input neurons
		for (int i = 0; i < network.get(0).length - 1; i++)
		{
			network.get(0)[i].setOutput(inputs[i]);
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
			outputs[i] = network.get(network.size() - 1)[i].getOutput();
		}
		
//		for(Neuron[] layer : network)
//		{
//			for(Neuron n : layer)
//			{
//				System.out.println(n + " | " + n.output);
//			}
//		}

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
			//\delta_{o1} = -(target_{o1} - out_{o1}) * out_{o1}(1 - out_{o1})
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
//						System.out.println(c);
						sum += c.to.deltaOutput * c.weight;
					}
				}
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
//						System.out.println(c);
						c.weight -= c.to.deltaOutput * c.from.output;
					}
				}
			}
		}
		
		
//		for(Neuron[] layer : network)
//		{
//			for(Neuron n : layer)
//			{
//				System.out.println(n + " | " + n.deltaOutput);
//			}
//		}
		
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
