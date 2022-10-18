import java.util.ArrayList;

public class FlatRoomHillClimbing{
    // Design variable
        // Array of 6, representing room assignment, poistion 1 being one person and so on with each element being a room number for that person
        // search space, all possible room assignment combinations

    // Objective function
        //to minimise: compare current solution to the 2d array that keeps peoples room preferences ( in decreasing order i.e. first element is most wanted)
        //first array being linked to the person that the first element represents, and so on
        //take each element in current solution and see where it is in corresponding preference array, if it's first position add 1, 2nd: 10, 3rd: 100, 4th:1000
        //5th: 10000, 6th: 100000
        //Sum the values gotten from this process 
    
    // Constraints 
        // No repeating values    
    
    
    // Representation
        // Integer Array

    // Initialisation
        // Initialsie each element randomly unifromily without replacement from 1-6

    // Neighbourhood operators
        // reverse the elements between 2 randomly picked elements (deals with constraint handling)

    
    private final static int[][] preferenceList = {
        {3,4,5,6,2,1}, //radek's preferences
        {4,3,5,6,2,1}, //henry's preferences
        {2,6,3,4,5,1}, //rafe's preferences
        {5,6,4,3,2,1}, //matthew's preferences
        {6,5,3,4,2,1}, //abhi's preferences
        {2,1,3,4,5,6}  //alex's preferences
    };
    private static int[]currentSolution = new int[6];
    private static boolean solutionfound = false;

    public static void main(String args[]){
        currentSolution = generateInitialSolution();
        for (int room : currentSolution) {
            System.out.println(room);
        }

        while (!solutionfound){
            ArrayList<int[]> neighbours = generateNeighbourSolutions(currentSolution);
            
            if (objectiveFunction(findBestNeighbour(neighbours)) >= objectiveFunction(currentSolution)){
                System.out.println("Solution found");
                solutionfound = true;
                for (int room : currentSolution) {
                    System.out.println(room);
                }
            }else{
                currentSolution = findBestNeighbour(neighbours);
            }
        }

    

    }


    public static int[] findBestNeighbour(ArrayList<int[]> neighbours){
        int i = 0;
        int[] bestneighbour={};
        for (int[] neighbour : neighbours){
            if (i==0){
                bestneighbour = neighbour;
            }else{
                if (objectiveFunction(bestneighbour)>objectiveFunction(neighbour)){
                    bestneighbour = neighbour;
                }
            }
            i++;
        }
        return bestneighbour;
    }

    public static ArrayList<int[]> generateNeighbourSolutions(int[]solution){
        ArrayList<int[]> neighbours = new ArrayList<>();

        for(int i=0; i<solution.length; i++){
            for (int j=i+1; j<solution.length; j++){
                int[] neighbour = new int[solution.length];
                for (int x=0; x<solution.length; x++) {
                    neighbour[x]=solution[x];
                }
                neighbour[i] = solution[j];
                neighbour[j] = solution[i];
                neighbours.add(neighbour);
            }
        }
        return neighbours;        
        
    }

    public static int objectiveFunction(int[] solution){
        int i=0;
        int total=0;
        for (int[] preferences: preferenceList){
            int index = findIndex(solution[i],preferences); 
            switch (index) {
                case 0:
                    total+=1;
                    break;
                case 1:
                    total+=10;
                    break;
                case 2:
                    total+=100;
                    break;
                case 3:
                    total+=1000;
                    break;
                case 4:
                    total+=10000;
                    break;
                case 5:
                    total+=100000;
                    break;
            }
            i++;
        }
        return total;
    }

    public static int findIndex(int item, int[] array){
        for (int i=0;i<array.length; i++){
            if (item==array[i]){
                return i;
            }
        }
        return -1;
    }

    public static int[] generateInitialSolution(){
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        roomNumbers.add(1);
        roomNumbers.add(2);
        roomNumbers.add(3);
        roomNumbers.add(4);
        roomNumbers.add(5);
        roomNumbers.add(6);
        int[] output = new int[6];
        for (int i=0; i<6; i++){
            int element = roomNumbers.get((int)(Math.random() * roomNumbers.size()));
            roomNumbers.remove(roomNumbers.indexOf(element));
            output[i]=element;
        }
        return output;
    }

}