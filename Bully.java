import java.util.Scanner;

public class Bully {
    boolean[] processes;
    int coordinator;

    public Bully(int n) {
        processes = new boolean[n];
        java.util.Arrays.fill(processes, true);
        coordinator = n - 1;
    }

    void down(int pid) {
        int index = pid - 1;
        if (index < 0 || index >= processes.length) return;
        processes[index] = false;
        System.out.println("P" + pid + " is down.");
        if (index == coordinator) {
            System.out.println("Coordinator P" + pid + " is down. Running election.");
            startElection(pid);
        }
    }

    void up(int pid) {
        int index = pid - 1;
        if (index < 0 || index >= processes.length) return;
        processes[index] = true;
        System.out.println("P" + pid + " is up.");
    }

    void startElection(int pid) {
        int startIndex = pid - 1;
        if (startIndex < 0 || startIndex >= processes.length || !processes[startIndex]) {
            System.out.println("P" + pid + " is down or invalid. Cannot start election.");
            return;
        }

        System.out.println("P" + pid + " starts election.");
        for (int i = startIndex + 1; i < processes.length; i++) {
            if (processes[i]) {
                System.out.println("P" + pid + " sends election to P" + (i + 1));
            }
        }
        findCoordinator();
        System.out.println("New Coordinator: P" + (coordinator + 1));
    }

    void findCoordinator() {
        for (int i = processes.length - 1; i >= 0; i--) {
            if (processes[i]) {
                coordinator = i;
                return;
            }
        }
        coordinator = -1; // All down
    }

    void showStatus() {
        for (int i = 0; i < processes.length; i++) {
            System.out.println("P" + (i + 1) + " is " + (processes[i] ? "UP" : "DOWN"));
        }
        System.out.println("Current Coordinator: P" + (coordinator + 1));
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter the number of processes: ");
        Bully bully = new Bully(sc.nextInt());

        while (true) {
            System.out.println("\n1. Down 2. Up 3. Start Election 4. Show Status 5. Exit");
            switch (sc.nextInt()) {
                case 1:
                    System.out.print("Enter process to bring down: ");
                    bully.down(sc.nextInt());
                    break;
                case 2:
                    System.out.print("Enter process to bring up: ");
                    bully.up(sc.nextInt());
                    break;
                case 3:
                    System.out.print("Enter process to start election: ");
                    bully.startElection(sc.nextInt());
                    break;
                case 4:
                    bully.showStatus();
                    break;
                case 5:
                    return;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}