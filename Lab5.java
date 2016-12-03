import java.util.*; 
import java.io.*;

public class Lab5{
	int city; // # of cities
	int [][] adjacency;
	int bestcost = Integer.MAX_VALUE;
	ArrayList<Integer> bestpath;
	Stack<Integer> pathStack = new Stack<>();

	public Lab5(int n) {
		city = n;
		adjacency = new int[city][city];
		bestpath = new ArrayList<>();
	}
	public void populateMatrix(String fname){
		File f = new File(fname);
		try{
			Scanner input = new Scanner(f);
			int i,j;
			for(i = 0; i<city && input.hasNext(); i++) {
				for(j = i; j<city & input.hasNext(); j++) {
					if(i==j){
						adjacency[i][j] = 0;
					}
					else{

						int value = input.nextInt();
						adjacency[i][j] = value;
						adjacency[j][i] = value;
					}
				}
			}
			input.close();
		}
		catch(IOException e) {
			System.out.println("Couldn't read file. ");
		}
	}
	public int cost(ArrayList<Integer> path){
		int cost = 0;
		for(int i = 0; i<path.size()-1; i++){ //path.size cuz its an array list
			cost+=adjacency[path.get(i)][path.get(i+1)];
		}
		if(path.size() == city){
			cost+=adjacency[path.get(path.size()-1)][0];
		}
		return cost;
	}
	public void stackTSP(){
		int [] visitedCities = new int[city];
		visitedCities[0] = 1;
		pathStack.push(0);
		int closestCity = 0;
		boolean minFlag = false;
		System.out.println(closestCity+ "\t");
		while(!pathStack.empty()){
			int currentCity = pathStack.pop();
			int min = Integer.MAX_VALUE;
			for(int i = 1; i<city; i++){
				if(adjacency[currentCity][i] !=0 && visitedCities[i] == 0){
					if(adjacency[currentCity][i] < min){
						min = adjacency[currentCity][i];
						closestCity=i;
						minFlag = true;
					}
				}
			}//for
			if(minFlag){
				visitedCities[closestCity] = 1;
				pathStack.push(closestCity);
				System.out.println(closestCity + "\t");
				minFlag = false;
			}

		}
	}
	public void tspdfs(ArrayList<Integer> partialTour, ArrayList<Integer> remainingCities){
			if(remainingCities.size() == 0){
				int tourCost = cost(partialTour);
				if(tourCost<bestcost){
					bestcost = tourCost;
					System.out.println("the best current cost is " + bestcost + " using the following routes " + partialTour);
				}
			}
			else{
				for(int i = 0; i< remainingCities.size(); i++){
					ArrayList<Integer> newPartialTour = new ArrayList(partialTour);
					newPartialTour.add(remainingCities.get(i));
					int tempCost = cost(newPartialTour);
					if(tempCost < bestcost) {
						ArrayList<Integer> newRemainingCities = new ArrayList(remainingCities);
						newRemainingCities.remove(i);
						tspdfs(newPartialTour, newRemainingCities);
					}
				}
			}
	}
	
	public void output(){
		System.out.println("Best path and cost");
		for(int i = 0; i<bestpath.size(); i++){
			System.out.print(bestpath.get(i) + " ");
			
		}
		System.out.println("cost = " +bestcost);
	}	
		
	public static void main(String[] args){
		Lab5 tsp = new Lab5(Integer.parseInt(args[0]));
		tsp.populateMatrix(args[1]);
		
		long start = System.nanoTime();
		tsp.stackTSP();
		long stop = System.nanoTime();

		System.out.println("the time it took " + (double)(stop-start)/Math.pow(10,9));
		/*
		System.out.println("Best Path and the cost is " );
		tsp.output();*/
	}	
}