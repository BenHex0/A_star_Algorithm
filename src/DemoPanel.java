import javax.swing.JPanel;
import java.awt.*;
import java.util.ArrayList;

public class DemoPanel extends JPanel {
    final int maxCol = 10;
    final int maxRow = 10;
    final int nodeSize = 70;
    final int screenWidth = nodeSize * maxCol;
    final int screenHeight = nodeSize * maxRow;

    Node[][] node = new Node[maxCol][maxRow];
    Node startNode, goalNode, currentNode;
    ArrayList<Node> openList = new ArrayList<>();
    ArrayList<Node> checkedList = new ArrayList<>();

    boolean goalReached = false;

    public DemoPanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenWidth));
        this.setBackground(Color.BLACK);
        this.setLayout(new GridLayout(maxRow, maxCol));
        this.addKeyListener(new KeyHandler(this));

        int col = 0;
        int row = 0;

        while (col < maxCol && row < maxRow) {
            node[col][row] = new Node(col, row);
            this.add(node[col][row]);

            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }

        setStartNode(0, 0);
        setGoalNode(4, 2);

        setSoildNode(2, 0);
        setSoildNode(2, 1);
        setSoildNode(2, 2);
        setSoildNode(3, 3);
        setSoildNode(4, 4);
        setSoildNode(5, 5);
        setSoildNode(6, 6);

        setCostOnNodes();
    }

    private void setStartNode(int col, int row) {
        node[col][row].setAsStart();
        startNode = node[col][row];
        currentNode = startNode;
    }

    private void setGoalNode(int col, int row) {
        node[col][row].setAsGoal();
        goalNode = node[col][row];
    }

    private void setSoildNode(int col, int row) {
        node[col][row].setAsSolid();
    }

    private void setCostOnNodes() {
        int col = 0;
        int row = 0;

        while (col  < maxCol && row < maxRow) {
            getCost(node[col][row]);
            col++;
            if (col == maxCol) {
                col = 0;
                row++;
            }
        }
    }

    private void getCost(Node node) {
        // get the G cost (The distance from the start node)
        int xDistance = Math.abs(node.col - startNode.col);
        int yDistance = Math.abs(node.row - startNode.row);
        node.gCost = xDistance + yDistance;

        // get the H cost (The distance from the goal node)
        xDistance = Math.abs(node.col - goalNode.col);
        yDistance = Math.abs(node.row - goalNode.row);
        node.hCost = xDistance + yDistance;

        // F cost is the total cost
        node.fCost = node.gCost + node.hCost;

        if (node != startNode && node != goalNode) {
            // node.setText("<html>H: " + node.hCost + "<br>G: " + node.gCost + "</html>");
            node.setText("<html>F: " + node.fCost + "</html>");
        }
    }

    public void search() {
        if (goalReached == false) {
            int col = currentNode.col;
            int row = currentNode.row;

            currentNode.setAsChecked();
            checkedList.add(currentNode);
            openList.remove(currentNode);

            // open The up node
            if (row - 1 >= 0)
            opneNode(node[col][row - 1]);

            // open the left node
            if (col -1 >= 0)
            opneNode(node[col - 1][row]);

            // open the right node
            if (col + 1 < maxCol)
            opneNode(node[col + 1][row]);

            if (row + 1 < maxRow)
            // open the down node
            opneNode(node[col][row + 1]);

            int bestNodeIndex = 0;
            int bestNodefCost = 999;

            for (int i = 0; i < openList.size(); i++) {
                // check if this node's F cost is better
                if (openList.get(i).fCost < bestNodefCost) {
                    bestNodeIndex = i;
                    bestNodefCost = openList.get(i).fCost;
                }
                // if F cost is equal, check the G cost
                else if (openList.get(i).fCost == bestNodefCost) {
                    if (openList.get(i).gCost < openList.get(bestNodeIndex).gCost) {
                        bestNodeIndex = i;
                    }
                }
            }
            // after the loop, we get the best node which is out next step
            currentNode = openList.get(bestNodeIndex);

            if (currentNode == goalNode) {
                goalReached = true;
                trackThePath();
            }
        }
    }

    private void opneNode(Node node) {
        if (node.open == false && node.checked == false && node.solid == false) {
            node.setAsOpen();
            node.parent = currentNode;
            openList.add(node);
        }
    }

    private void trackThePath() {
        Node current  = goalNode;

        while (current != startNode) {
            current = current.parent;

            if (current != startNode) {
                current.setAsPath();
            }
        }
    }
}