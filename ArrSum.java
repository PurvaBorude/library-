import mpi.MPI;
import java.util.Arrays;
import java.util.Scanner;

public class ArrSum {
    public static void main(String[] args) throws Exception {
        MPI.Init(args);
        int rank = MPI.COMM_WORLD.Rank();
        int size = MPI.COMM_WORLD.Size();
        int unitSize = 5;
        int root = 0;
        int[] sendBuffer = null;
        int[] recvBuffer = new int[unitSize];
        int[] gatherBuffer = new int[size];

        if (rank == root) {
            sendBuffer = new int[unitSize * size];
            System.out.println("Enter " + (unitSize * size) + " elements:");
            for (int i = 0; i < sendBuffer.length; i++) {
                sendBuffer[i] = i; // Or use a Scanner here if you want user input
                System.out.println("Element " + i + "\t = " + i);
            }
        }

        MPI.COMM_WORLD.Scatter(sendBuffer, 0, unitSize, MPI.INT,
                recvBuffer, 0, unitSize, MPI.INT, root);

        int localSum = Arrays.stream(recvBuffer).sum();
        System.out.println("Intermediate sum at process " + rank + " is " + localSum);

        MPI.COMM_WORLD.Gather(new int[]{localSum}, 0, 1, MPI.INT,
                gatherBuffer, 0, 1, MPI.INT, root);

        if (rank == root) {
            int totalSum = Arrays.stream(gatherBuffer).sum();
            System.out.println("Final sum: " + totalSum);
        }
        MPI.Finalize();
    }
}
