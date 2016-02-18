

import neuralnetwork.Network;

public class XOR
{
	public static void main(String[] args)
	{
		Network nn = new Network(2, 4, 1);
		System.out.println(nn);

		// output results
		// ------------------------------------------
		
		float outputs[];

		outputs = nn.feedForward(new float[] { 0, 0 });
		System.out.print("f(0,0) = ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
		float[] asdf = outputs;
		
		outputs = nn.feedForward(new float[] { 0, 1 });
		System.out.print("f(0,1)= ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();

		outputs = nn.feedForward(new float[] { 1, 0 });
		System.out.print("f(1,0) = ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();

		outputs = nn.feedForward(new float[] { 1, 1 });
		System.out.print("f(1,1) = ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println("\n");

		// train (non-stochastically - doesn't seem to make a difference here)
		// -----------------------------------------
		
		for(int j = 0; j < 5000; j++)
		{
			outputs = nn.train(new float[] { 0, 0 }, new float[] { 0 });
			outputs = nn.train(new float[] { 0, 1 }, new float[] { 1 });
			outputs = nn.train(new float[] { 1, 0 }, new float[] { 1 });	
			outputs = nn.train(new float[] { 1, 1 }, new float[] { 0 });
		}
		
		// output results
		// -------------------------------------------
		
		System.out.println(nn);
		
		outputs = nn.feedForward(new float[] { 0, 0 });
		System.out.print("f(0,0) = ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();

		outputs = nn.feedForward(new float[] { 0, 1 });
		System.out.print("f(0,1)= ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
		outputs = nn.feedForward(new float[] { 1, 0 });
		System.out.print("f(1,0) = ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
		outputs = nn.feedForward(new float[] { 1, 1 });
		System.out.print("f(1,1) = ");
		for (float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
		System.out.println();
		
		
	}
	
	public static void printArray(float[] arr)
	{
		String output = "[";
		for (float n : arr)
		{
			output += n + ",";
		}
		System.out.println(output.substring(0, output.length() - 1) + "]");
	}
}
