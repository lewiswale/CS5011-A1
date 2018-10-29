package Search2;

import Search1.WorldMap;

import java.util.ArrayList;

public class Search2 {
    WorldMap map;
    private char[] points = new char[] {'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P'};
    private ArrayList<Integer> connected;
    private ArrayList<String> frontier, explored;
    private double[][] distances;

    public Search2(int n) {
        map = new WorldMap(n);
        System.out.println("Map selected:");
        System.out.println("===============================");
        System.out.println(map);
        System.out.println("===============================");
    }

    public void bestFirstSearch(boolean aStar) {
        int start = map.getStart();
        int goal = map.getGoal();

        if (!aStar) {
            System.out.println("BEST-FIRST SEARCH");
        } else {
            System.out.println("A* SEARCH");
        }

        System.out.println("Start: " + points[start]);

        connected = map.getConnected(start);
        printConnected();

        frontier = new ArrayList<String>();
        explored = new ArrayList<String>();
        distances = map.getDistances();
        sortConnected(start, goal, aStar);
        printFrontier();

        printExplored();

        System.out.println();

        boolean goalFound = false;

        while (!goalFound) {
            String front = frontier.get(0);
            String[] nodes = front.split(",");
            int current = Integer.parseInt(nodes[1]);
            explored.add(frontier.get(0));
            frontier.remove(0);
            System.out.println("Current: " + points[current]);

            if (current == goal) {
                System.out.println("IS GOAL: YES");
                goalFound = true;
            } else {
                System.out.println("IS GOAL: NO");
            }

            connected = map.getConnected(current);
            printConnected();
            sortConnected(current, goal, aStar);
            printFrontier();

            printExplored();
            System.out.println();

            if (frontier.size() == 0 && !goalFound) {
                System.out.println("No available route found. Goal not reachable.");
                System.out.println("===============================");
                return;
            }
        }

        System.out.println("GOAL FOUND.");
        printRoute(start, goal);
        System.out.println("===============================");
    }

    public void sortConnected(int current, int goal, boolean aStar) {
        StringBuilder sb;
        for (int i = 0; i < connected.size(); i++) {
            sb = new StringBuilder();
            sb.append(current);
            sb.append(",");
            sb.append(connected.get(i));
            String connection = sb.toString();

            sb = new StringBuilder();
            sb.append(connected.get(i));
            sb.append(",");
            sb.append(current);
            String alternate = sb.toString();

            if (!frontier.contains(connection) && !explored.contains(connection) && !frontier.contains(alternate)
                && !explored.contains(alternate)) {
                frontier.add(connection);
            }
        }

        for (int i = 1; i < frontier.size(); i++) {
            for (int j = 0; j < i; j++) {
                String first = frontier.get(j);
                String second = frontier.get(j + 1);
                String[] firstNodes = first.split(",");
                String[] secondNodes = second.split(",");
                int node1 = Integer.parseInt(firstNodes[0]);
                int node2 = Integer.parseInt(firstNodes[1]);
                int node3 = Integer.parseInt(secondNodes[0]);
                int node4 = Integer.parseInt(secondNodes[1]);

                if (!aStar) {
                    if (distances[node1][node2] > distances[node3][node4]) {
                        String temp = frontier.get(j);
                        frontier.set(j, frontier.get(j + 1));
                        frontier.set(j + 1, temp);
                    }
                } else {
                    if (distances[node1][node2] + map.calculateDistance(node2, goal) >
                        distances[node3][node4] + map.calculateDistance(node4, goal)) {
                        String temp = frontier.get(j);
                        frontier.set(j, frontier.get(j + 1));
                        frontier.set(j + 1, temp);
                    }
                }
            }
        }
    }

    public void printConnected() {
        StringBuilder sb = new StringBuilder();
        sb.append("Connected: ");

        for (int i = 0; i < connected.size(); i++) {
            sb.append(points[connected.get(i)]);
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
            String[] sp = explored.get(i).split(",");
            sb.append(points[Integer.parseInt(sp[0])]);
            sb.append(",");
            sb.append(points[Integer.parseInt(sp[1])]);
            sb.append(" ");
        }
        sb.append("| Total steps: ");
        sb.append(explored.size());
        System.out.println(sb);

    }

    public void printFrontier() {
        StringBuilder sb = new StringBuilder();
        sb.append("Frontier: ");

        for (int i = 0; i < frontier.size(); i++) {
            String[] sp = frontier.get(i).split(",");
            sb.append(points[Integer.parseInt(sp[0])]);
            sb.append(",");
            sb.append(points[Integer.parseInt(sp[1])]);
            sb.append(" ");
        }

        System.out.println(sb);
    }

    public void printRoute(int start, int goal) {
        StringBuilder sb = new StringBuilder();
        sb.append("Final route: ");

        ArrayList<String> route = new ArrayList<String>();
        route.add(Character.toString(points[goal]));

        boolean routeComplete = false;
        int currentNode = goal;
        double routeCost = 0;

        while (!routeComplete) {
            for (int i = 0; i < explored.size(); i++) {
                String[] connection = explored.get(i).split(",");
                if (Integer.parseInt(connection[1]) == currentNode) {
                    int nextNode = Integer.parseInt(connection[0]);
                    routeCost += distances[nextNode][Integer.parseInt(connection[1])];
                    route.add(Character.toString(points[nextNode]));
                    currentNode = nextNode;
                }
            }

            if (currentNode == start) {
                routeComplete = true;
            }
        }

        for (int i = route.size() - 1; i >= 0; i--) {
            sb.append(route.get(i));
            sb.append(" ");
        }

        sb.append("\nRoute cost: ");
        sb.append(routeCost);

        System.out.println(sb);
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Please give map number as argument.");
            System.exit(1);
        }

        Search2 s2 = new Search2(Integer.parseInt(args[0]));
        s2.bestFirstSearch(false);
        s2.bestFirstSearch(true);
    }
}
