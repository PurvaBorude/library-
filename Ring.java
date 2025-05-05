import java.util.*;

public class Ring {
    int n, coordinator;
    boolean[] alive;

    public Ring(int n) {
        this.n = n;
        alive = new boolean[n];
        Arrays.fill(alive, true);
        coordinator = n;
        System.out.println("P" + coordinator + " is the coordinator.");
    }

    void display() {
        for (int i = 0; i < n; i++)
            System.out.println("P" + (i + 1) + " is " + (alive[i] ? "up" : "down"));
        if (alive[coordinator - 1])
            System.out.println("P" + coordinator + " is the coordinator.");
        else
            System.out.println("Coordinator (P" + coordinator + ") is down. Run election.");
    }

    void changeState(int id, boolean state) {
        if (alive[id - 1] == state)
            System.out.println("P" + id + " is already " + (state ? "up." : "down."));
        else {
            alive[id - 1] = state;
            System.out.println("P" + id + " is now " + (state ? "up." : "down."));
            if (!alive[coordinator - 1])
                System.out.println("Coordinator is down. Run election.");
        }
    }

    void election(int id) {
        if (!alive[id - 1]) {
            System.out.println("P" + id + " is down. Cannot start election.");
            return;
        }
        int newCoord = id;
        for (int i = (id % n); i != (id - 1); i = (i + 1) % n)
            if (alive[i] && (i + 1) > newCoord) newCoord = i + 1;
        if (alive[(id - 1 + n) % n] && ((id - 1 + n) % n + 1) > newCoord) 
            newCoord = (id - 1 + n) % n + 1;

        coordinator = newCoord;
        System.out.println("Election done. New coordinator is P" + coordinator + ".");
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Ring ring = null;

        while (true) {
            System.out.println("\nRing Algorithm\n1. Create processes\n2. Display processes\n3. Up a process\n4. Down a process\n5. Run election algorithm\n6. Exit");
            System.out.print("Enter your choice: ");
            int ch = sc.nextInt(), id;
            switch (ch) {
                case 1 -> {
                    System.out.print("Enter number of processes: ");
                    ring = new Ring(sc.nextInt());
                }
                case 2 -> ring.display();
                case 3 -> {
                    System.out.print("Enter process to up: ");
                    id = sc.nextInt();
                    ring.changeState(id, true);
                }
                case 4 -> {
                    System.out.print("Enter process to down: ");
                    id = sc.nextInt();
                    ring.changeState(id, false);
                }
                case 5 -> {
                    System.out.print("Enter process to start election: ");
                    id = sc.nextInt();
                    ring.election(id);
                }
                case 6 -> System.exit(0);
                default -> System.out.println("Invalid choice.");
            }
        }
    }
}
