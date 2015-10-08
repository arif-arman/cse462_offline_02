import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class Main_1005046 {
	
	public static final int INF = 100000;

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		DecimalFormat two = new DecimalFormat("#0.00");
		int testCase = 0;
		int n = 0;		// no. of elements in main set
		int m = 0;		// no. of subsets
		
		File file = new File("io/1005046_input.txt");
		Scanner input = new Scanner(file);
		PrintWriter dpout = new PrintWriter(new BufferedWriter(new FileWriter("io/1005046_dp_time.txt")));
		PrintWriter lpout = new PrintWriter(new BufferedWriter(new FileWriter("io/1005046_lp_time.txt")));
		PrintWriter approx = new PrintWriter(new BufferedWriter(new FileWriter("io/1005046_approx_ratio.txt")));
		
		if (input.hasNext()) testCase = input.nextInt();
		
		
		for (int i=0;i<testCase;i++) {					// for each testcase
			if (input.hasNext()) n = input.nextInt();	// no. of elements
			if (input.hasNext()) m = input.nextInt();	// no. of subsets
			int [] weight = new int[m];					// to hold weight
			int [] size = new int[m];					// to hold size of each subset
			ArrayList<int[]> subsets = new ArrayList<>();	// to hold all subsets for this testcase
			for (int j=0;j<m;j++) {		// for each subset
				if (input.hasNext()) weight[j] = input.nextInt();	// get weight		// size of subset
				if (input.hasNext()) size[j] = input.nextInt();
				int [] subset = new int[size[j]];
				for (int k=0;k<size[j];k++) {
					if (input.hasNext()) subset[k] = input.nextInt();
				}
				subsets.add(subset);
			}
			
			double elapsedTime1 = 0, elapsedTime2 = 0;
			double startTime = System.nanoTime();
			BitmaskDP_1005046 bdp = new BitmaskDP_1005046(n, m, subsets, weight, size);
			bdp.solutionPrint();
			double stopTime = System.nanoTime();
			elapsedTime1 += (stopTime - startTime) / 1000000;
			startTime = System.nanoTime();
			LPApprox_1005046 lpa = new LPApprox_1005046(n, m, subsets, weight, size);
			lpa.solutionPrint();
			stopTime = System.nanoTime();
			elapsedTime2 = (stopTime - startTime) / 1000000;
		
			dpout.println(n + "\t" + two.format(elapsedTime1));
			lpout.println(n + "\t" + two.format(elapsedTime2));
			double bdpmin = bdp.getMinCost();
			double lpamin = lpa.getMinCost();
			approx.println(n + "\t" + bdpmin + "\t" + lpamin + "\t" + two.format(lpamin/bdpmin) + "\t" + lpa.getF());
					
		}
		input.close();
		dpout.close();
		lpout.close();
		approx.close();
		

	}

}
