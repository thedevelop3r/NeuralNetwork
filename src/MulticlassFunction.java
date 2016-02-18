import neuralnetwork.Network;

public class MulticlassFunction
{
	public static void main(String[] args)
	{
		Network nn = new Network(2, 4, 2);
		
		printArray(nn.feedForward(new float[] { 0, 0 }));
		printArray(nn.feedForward(new float[] { 0, 1 }));
		printArray(nn.feedForward(new float[] { 1, 0 }));
		printArray(nn.feedForward(new float[] { 1, 1 }));
		
		for(int i = 0; i < 5000; i++)
		{
			nn.train(new float[] {0,  0}, new float[] {1, 1});
			nn.train(new float[] {0,  1}, new float[] {1, 0});
			nn.train(new float[] {1,  0}, new float[] {0, 0});
			nn.train(new float[] {1,  1}, new float[] {0, 1});
		}
		
		System.out.println();
		
		printArray(nn.feedForward(new float[] { 0, 0 }));
		printArray(nn.feedForward(new float[] { 0, 1 }));
		printArray(nn.feedForward(new float[] { 1, 0 }));
		printArray(nn.feedForward(new float[] { 1, 1 }));	
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
