package Search1;

import java.util.ArrayList;

public class Search1 {
    private WorldMap map;
    private char[] points = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
    private ArrayList connected, frontier, explored;

    public void selectMap(int n) {
        map = new WorldMap(n);
        System.out.println("Map selected:");
        System.out.println("===============================");
        System.out.println(map);
        System.out.println("===============================");
    }

    //Main tree search algorithm.
    public void findRoute(boolean depthSearch) {
        if (!depthSearch) {
            System.out.println("BREADTH FIRST SEARCH");
        } else {
            System.out.println("DEPTH FIRST SEARCH");
        }

        int start = map.getStart();
        int goal = map.getGoal();
        System.out.println("Start: " + points[start]);

        connected = map.getConnected(start);

        if (connected.size() == 0) {
            System.out.println("Start is isolated. No available route.");
            return;
        }

        printConnected();

        frontier = new ArrayList();

        for (int i = 0; i < connected.size(); i++) {
            frontier.add(connected.get(i));
        }

        printFrontier();

        explored = new ArrayList();
        explored.add(start);

        printExplored();

        System.out.println();

        boolean goalFound = false;

        while (!goalFound) {
            int current;

            if (!depthSearch) {
                current = (Integer) frontier.get(0);
                frontier.remove(0);
            } else {
                current = (Integer) frontier.get(frontier.size() - 1);
                frontier.remove(frontier.size() - 1);
            }

            System.out.println("Current: " + points[current]);

            if (current == goal) {
                goalFound = true;
                System.out.println("Is Goal: YES");
            } else {
                System.out.println("Is Goal: NO");
            }


            connected = map.getConnected(current);
            printConnected();

            for (int i = 0; i < connected.size(); i++) {
                int toBeExplored = (Integer) connected.get(i);
                if (!explored.contains(toBeExplored) && !frontier.contains(toBeExplored)) {
                    frontier.add(toBeExplored);
                }
            }

            printFrontier();
            explored.add(current);
            printExplored();
            System.out.println();

            if (frontier.size() == 0 && !goalFound) {
                System.out.println("No available route found. Goal not reachable.");
                System.out.println("===============================");
                return;
            }
        }

        System.out.println("GOAL FOUND.");
        System.out.println("===============================");
    }

    public void printConnected() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connected: ");

        for (int i = 0; i < connected.size(); i++) {
            sb.append(points[(Integer) connected.get(i)]);
            if (i < connected.size() - 1) {
                sb.append(", ");
            }
        }

        System.out.println(sb);
    }

    public void printExplored() {
        StringBuilder sb = new StringBuilder();
        sb.append("Explored: ");

        for (int i = 0; i < explored.size(); i++) {
            sb.append(points[(Integer) explored.get(i)]);
            if (i < explored.size() - 1) {
                sb.append(", ");
            }
        }

        System.out.println(sb);
    }

    public void printFrontier() {
        StringBuilder sb = new StringBuilder();
        sb.append("Frontier: ");

        for (int i = 0; i < frontier.size(); i++) {
            sb.append(points[(Integer) frontier.get(i)]);
            if (i < frontier.size() - 1) {
                sb.append(", ");
            }
        }

        System.out.println(sb);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please pass map number to use as argument.");
            System.exit(1);
        }

        Search1 s1 = new Search1();
        s1.selectMap(Integer.parseInt(args[0]));
        s1.findRoute(false);
        s1.findRoute(true);
    }
}
