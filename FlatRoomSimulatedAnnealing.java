import java.util.ArrayList;
import java.util.Collections;
import java.util.Arrays;

public class FlatRoomSimulatedAnnealing{

    private static final double e = Math.E;
    private static double P;
    private static double T;

    private final static int[][] preferenceList = {
        {3,4,5,6,2,1}, //radek's preferences
        {3,5,6,4,2,1}, //henry's preferences
        {2,1,3,4,5,6}, //rafe's preferences
        {4,6,1,3,2,5}, //matthew's preferences
        {6,5,3,4,2,1}, //abhi's preferences
        {2,1,3,4,5,6}  //alex's preferences
    };


    // window = 1 , kitchen = 2, next tp bathroom = 3, other = 4, room with stuff = 6, empty room = 5
     private final static int[][] NewpreferenceList = { 
        {4,3,5,6}, //radek's preferences
        {3,6,5}, //henry's preferences
        {4,5,2,3}, //rafe's preferences
        {4,3,2,5}, //matthew's preferences
        {5,3}, //abhi's preferences
        //{2,1,3,4}  //alex's preferences
    };

    private static int[][] frequencies = {
        {0,0,0,0,0,0}, //rad
        {0,0,0,0,0,0}, //h
        {0,0,0,0,0,0}, //raf
        {0,0,0,0,0,0}, //m
        {0,0,0,0,0,0} //ab
        //{0,0,0,0,0,0} //al
    };


    private static int maxArrayIndex(int[] X) {
        int currentMaxIndex = 0;
        for (int i = 0; i < X.length; i++) {
            if(X[i] > X[currentMaxIndex]){
                currentMaxIndex = i;
            }
        }
        return currentMaxIndex;
    }

    private static int[] currentSolution = new int[6];
    public static void main(String[] args){
        for (int z=0; z<10; z++){
            T=100;
            currentSolution = generateInitialSolution();
            for (int i=0;i<1000;i++){
                ArrayList<int[]> neighbours = generateNeighbourSolutions(currentSolution);
                int[] randomNeighbour = neighbours.get((int)(Math.random()*neighbours.size()));
                if (objectiveFunction(randomNeighbour) >= objectiveFunction(currentSolution)){
                    //with some probability P of acccepting bad neighbour
                    double rand = Math.random();
                    P = calculateP(T,objectiveFunction(randomNeighbour),objectiveFunction(currentSolution));
                    if (rand <= P){
                        currentSolution = randomNeighbour;
                    }
                }else{
                    currentSolution = randomNeighbour;
                }
                reduceT();
            }

            for (int x=0;x<5;x++){
                int y = currentSolution[x];
                frequencies[x][y-1]+=1;
            }

        // for (int[] x: frequencies){
        //     for (int y : x){
        //         System.out.print(y+" ");
        //     }
        //     System.out.println();
        // }
        

        }


        System.out.println("Solution with order (Radek,Henry,Rafe,Matthew,Abhi)");

        for (int[] x: frequencies){
            int room = maxArrayIndex(x);
            room+=1;
            System.out.println("Room "+room+" ");


        }


    }

    public static double calculateP(double T, int neighbourQuality, int currentSolQuality){
        int deltaE = currentSolQuality - neighbourQuality;
        double deltaEOverT = deltaE / T;
        double probability = Math.pow(e,deltaEOverT);
        return probability;
    }

    public static void reduceT(){
        T = 0.95*T;
    }

    public static ArrayList<int[]> generateNeighbourSolutions(int[] solution){
        ArrayList<int[]> neighbours = new ArrayList<>();

        for (int i=0; i<solution.length; i++){
            for (int j=i+1; j<solution.length; j++){
                int[] neighbour = new int[solution.length];
                for (int x=0; x<solution.length; x++){
                    neighbour[x]=solution[x];
                }
                neighbour[i]=solution[j];
                neighbour[j]=solution[i];
                neighbours.add(neighbour);
            }
        }
        return neighbours;
    }

    public static int objectiveFunction(int[] solution){
        int i = 0;
        int total = 0;
        for (int[] preferences: NewpreferenceList){
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
                case -1:
                    total+=1000000000;//Integer.MAX_VALUE; //they don't want /can't have the room
                    break;
            }
            i++;
        }
        return total;
    }


    public static int findIndex(int item, int[] array){
        for (int i=0; i<array.length; i++){
            if (item==array[i]){
                return i;
            }
        }
        return -1;
    }

    public static int[] generateInitialSolution(){
        ArrayList<Integer> roomNumbers = new ArrayList<>();
        //roomNumbers.add(1);
        roomNumbers.add(2);
        roomNumbers.add(3);
        roomNumbers.add(4);
        roomNumbers.add(5);
        roomNumbers.add(6);

        int[] output = new int[5];
        for (int i=0; i<5; i++){
            int element = roomNumbers.get((int)(Math.random()*roomNumbers.size()));
            roomNumbers.remove(roomNumbers.indexOf(element));
            output[i]=element;
        }
        return output;
    }
}
