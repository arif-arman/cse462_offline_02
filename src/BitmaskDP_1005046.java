import java.util.ArrayList;

public class BitmaskDP_1005046 {
	int n;
	int nPow;
	int m;
	int [][] dp;
	int [][] direction;
	ArrayList<int[]> subsets;
	int [] mask;		// to store corresponding bitmask of a subset
	int [] weight;
	int [] size;
	int solution;
	
	public BitmaskDP_1005046(int n, int m, ArrayList<int[]> subsets, int[] weight, int[] size) {
		// TODO Auto-generated constructor stub
		this.n = n;
		this.m = m;
		this.subsets = subsets;
		this.weight = weight;
		this.size = size;
		
		solution = 0;
		nPow = (int) (Math.pow(2, n));
		
		// initialize dp array
		dp = new int[m][nPow];
		direction = new int[m][nPow];
		for (int i=0;i<m;i++) {
			for (int j=0;j<nPow;j++) {
				dp[i][j] = -1;
				direction[i][j] = -1;
			}
		}
		
		createMasks();
		//testPrint();
	}
	
	private void testPrint() {
		// test print block
		System.out.println("Elements N: " + n + " Subsets M: " + m);
		System.out.println("--- Subsets ---");
		for (int i=0;i<m;i++) {
			int [] subset = subsets.get(i);
			for (int j=0;j<size[i];j++) System.out.print(subset[j] + " ");
			System.out.println(Integer.toBinaryString(mask[i]) + " " + weight[i]);
		}
		
		/*
		System.out.println("--- DP ---");
		for (int i=0;i<m;i++) {
			for (int j=0;j<nPow;j++) {
				System.out.print(dp[i][j] + " ");
			}
			System.out.println();
		}
		
		System.out.println("--- Direction ---");
		for (int i=0;i<m;i++) {
			for (int j=0;j<nPow;j++) {
				System.out.print(direction[i][j] + " ");
			}
			System.out.println();
		}
		*/
		
	}
	
	private void createMasks() {
		mask = new int[m];
		for (int i=0;i<m;i++) {
			int [] subset = subsets.get(i);
			int c = 0;
			for (int j=0;j<size[i];j++) {
				c |= 1<<subset[j];
			}
			mask[i] = c;
		}
	}
	
	public int setCover(int current, int bitmask) {
		if (current == m && bitmask != (1<<n)-1) return Main_1005046.INF;
		else if (current == m && bitmask == (1<<n)-1) return 0;
		else {
			int valChoose = setCover(current+1, bitmask | mask[current]) + weight[current];
			int valNotChoose = setCover(current+1, bitmask);
			int ans;
			if (valChoose < valNotChoose) {
				ans = valChoose;
				direction[current][bitmask] = 1;
			}
			else {
				ans = valNotChoose;
				direction[current][bitmask] = 0;
			}
			dp[current][bitmask] = ans;
			return ans;
		}
				
	}
	
	public void solutionSets() {
		int current = 0;
		int bitmask = 0;
		while(current < m) {
			if (direction[current][bitmask] == 1) {
				solution |= (1<<current);
				bitmask |= mask[current];
			}
			current++;
		}
	}
	
	public void solutionPrint() {
		System.out.println("--- DP ---");
		setCover(0, 0);
		solutionSets();
		//testPrint();
		if (dp[0][0] == Main_1005046.INF) 
			System.out.println("No solution found");
		else {
			System.out.println("Min Cost: " + dp[0][0]);
			//System.out.println("Subsets: " + Integer.toBinaryString(solution));
			System.out.print("Subsets: ");
			for (int i=0;i<m;i++) {
				if ((solution & 1) == 1) System.out.print(i + " ");
				solution >>= 1;
			}
		}
		System.out.println();
	}
	
	public int getMinCost() {
		return dp[0][0];
	}
	

}
