

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import neuralnetwork.Network;

public class XOR
{
	public static void main(String[] args)
	{
		Network nn = new Network(2, 4, 1);
		//nn = loadNetwork("./res/xor.network");
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
		
		saveNetwork(nn, "./res/xor.network");
	}
	
	public static void saveNetwork(Network n, String filePath)
	{
		try
		{
			FileOutputStream fos = new FileOutputStream(new File(filePath));
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(n);
			oos.close();
			fos.close();
		}
		catch (IOException e)
		{
			System.out.println("Could not write file");
		}
	}
	
	public static Network loadNetwork(String filePath)
	{
		try
		{
			FileInputStream fis = new FileInputStream(new File(filePath));
			ObjectInputStream ois = new ObjectInputStream(fis);
			Network n = (Network) ois.readObject();
			ois.close();
			fis.close();
			return n;
		}
		catch (IOException e)
		{
			System.out.println("File could not be loaded");
		}
		catch (ClassNotFoundException e)
		{
			System.out.println("File data is corrupt");
		}
		return null;
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
