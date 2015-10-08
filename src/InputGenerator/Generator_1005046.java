package InputGenerator;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class Generator_1005046 {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("io/1005046_input.txt", false)));		
		Random rand = new Random();
		out.println(17);
		out.println();
		for (int n=4; n<=20;) {
			int m = 5;
	
			
			boolean allIn = true;
			int [] index = new int[m];
			int [][] subsets = new int[m][n];	
			for (int i=0;i<m;i++) 
				for (int j=0;j<n;j++)
					subsets[i][j] = -1;
			
			for (int i=0;i<n;) {
				int counter = 0;
				for (int j=0;j<m;j++) {
					if (rand.nextInt(10) > 3) {
						subsets[j][index[j]++] = i;
						counter++;
					}
				}
				if (counter > 0) i++;
			}
			for (int i=0;i<m;i++) {
				if(index[i] == 0) {
					allIn = false;
					break;
				}
			}
				
			if (!allIn) continue;
			out.println(n);
			out.println(m);
			for (int i=0;i<m;i++) {
				out.printf("%d %d ", rand.nextInt(10) + 1, index[i]);
				for (int j=0;j<index[i];j++) {
					out.printf(subsets[i][j] + " ");
				}
				out.println();
			}
			out.println();

			out.println();
			n++;
			
		}
		
		out.close();
	}

}
