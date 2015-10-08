import java.util.ArrayList;

import org.apache.commons.math3.optim.MaxIter;
import org.apache.commons.math3.optim.PointValuePair;
import org.apache.commons.math3.optim.linear.*;
import org.apache.commons.math3.optim.nonlinear.scalar.GoalType;

public class LPApprox_1005046 {
	int n;
	int m;
	ArrayList<int[]> subsets;
	int [] weight;
	int [] size;
	int minCost;
	int solutionSets;
	double f;
	
	
	public LPApprox_1005046(int n, int m, ArrayList<int[]> subsets, int[] weight, int[] size) {
		// TODO Auto-generated constructor stub
		this.n = n;
		this.m = m;
		this.subsets = subsets;
		this.weight = weight;
		this.size = size;
		
		minCost = 0;
		solutionSets = 0;
		f = 0;
		
	}
	
	private void testPrint() {
		// test print block
		System.out.println("Elements N: " + n + " Subsets M: " + m);
		System.out.println("--- Subsets ---");
		for (int i=0;i<m;i++) {
			int [] subset = subsets.get(i);
			for (int j=0;j<size[i];j++) System.out.print(subset[j] + " ");
			System.out.println("Weight : " + weight[i]);
		}
			
		
	}
	
	private boolean elementExists(int [] subset, int size, int x) {
		for (int j=0;j<size;j++) {
			if (subset[j] == x) return true;
		}
		return false;
	}
	
	private double calculatef() {
		double max = 0;
		for (int i=0;i<n;i++) {
			double count = 0;
			for (int j=0;j<m;j++) {
				int [] subset = subsets.get(j);
				if (elementExists(subset, size[j], i)) count++;
			}
			if (count > max) {
				//System.out.println(i + " " + count);
				max = count;
			}
		}
		return max;
	}
	
	private void setCoverLP() {
		// n # of subsets
		// m # of elements in main set
		double [] coeff = new double[m];
		for (int i=0;i<m;i++) coeff[i] = weight[i];
		//for (int i=0;i<n;i++) System.out.print(coeff[i] + " ");
		//System.out.println();
		LinearObjectiveFunction func = new LinearObjectiveFunction(coeff, 0);
		ArrayList<LinearConstraint> constraints = new ArrayList<>();
		for (int i=0;i<n;i++) {
			double [] constraintCoeff = new double[m];
			for (int j=0;j<m;j++) {
				int [] subset = subsets.get(j);
				if (elementExists(subset, size[j], i)) constraintCoeff[j] = 1;
			}
			constraints.add(new LinearConstraint(constraintCoeff, Relationship.GEQ, 1));
			//for (int j=0;j<n;j++) System.out.print(constraintCoeff[j] + " ");
			//System.out.println();
		}
		SimplexSolver solver = new SimplexSolver();
		PointValuePair solution = solver.optimize(new MaxIter(100), func, new LinearConstraintSet(constraints),GoalType.MINIMIZE, new NonNegativeConstraint(true));
		double [] xopt = solution.getPoint();
		f = calculatef();
		//for (int i=0;i<m;i++) System.out.print(xopt[i] + " ");
		//System.out.println(" f : " + f);
		for (int i=0;i<m;i++) {
			if (xopt[i] >= 1/f) {
				solutionSets |= (1<<i);
				minCost += weight[i];
			}
		}
	}
	
	public void solutionPrint() {
		System.out.println("--- LP ---");
		//testPrint();
		setCoverLP();
		System.out.println("Min Cost: " + minCost);
		//System.out.println("Subsets: " + Integer.toBinaryString(solution));
		System.out.print("Subsets: ");
		for (int i=0;i<m;i++) {
			if ((solutionSets & 1) == 1) System.out.print(i + " ");
			solutionSets >>= 1;
		}
		System.out.println();
	}
	
	public int getMinCost() {
		return minCost;
	}
	
	public double getF() {
		return f;
	}
	
}
