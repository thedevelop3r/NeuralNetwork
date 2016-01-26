import neuralnetwork.Network;

public class XOR
{
	public static void main(String[] args)
	{
		Network nn = new Network(2, 3, 1);
		System.out.println(nn);
		
		float outputs[];
		for(int i = 0; i < 1000; i++)
		{
				outputs = nn.train(new float[]{0,0}, new float[]{0});
				outputs = nn.train(new float[]{0,1}, new float[]{1});
				outputs = nn.train(new float[]{1,0}, new float[]{1});
				outputs = nn.train(new float[]{1,1}, new float[]{0});
		}
		
		outputs = nn.feedForward(new float[]{0,0});
		System.out.print("f(0,0) = ");
		for(float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();

		outputs = nn.feedForward(new float[]{0,1});
		System.out.print("f(0,1)= ");
		for(float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
		outputs = nn.feedForward(new float[]{1,0});
		System.out.print("f(1,0) = ");
		for(float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
		outputs = nn.feedForward(new float[]{1,1});
		System.out.print("f(1,1) = ");
		for(float f : outputs)
		{
			System.out.print(f + ", ");
		}
		System.out.println();
	}
}
