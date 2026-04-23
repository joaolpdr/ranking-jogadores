import javax.swing.*;
import java.awt.*;

public class TreePanel extends JPanel {

    private Node root;

    public TreePanel(Node root) {
        this.root = root;
    }

    public void setRoot(Node root) {
        this.root = root;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawTree(g, root, getWidth() / 2, 40, getWidth() / 4);
    }

    private void drawTree(Graphics g, Node node, int x, int y, int offset) {
        if (node == null) return;

        // círculo do nó
        g.setColor(Color.BLUE);
        g.fillOval(x - 20, y - 20, 40, 40);

        // texto
        g.setColor(Color.WHITE);
        String text = node.player.getNickname(); // pode trocar por ranking se quiser
        g.drawString(text, x - 15, y + 5);

        // esquerda
        if (node.left != null) {
            g.setColor(Color.BLACK);
            g.drawLine(x, y, x - offset, y + 60);
            drawTree(g, node.left, x - offset, y + 60, offset / 2);
        }

        // direita
        if (node.right != null) {
            g.drawLine(x, y, x + offset, y + 60);
            drawTree(g, node.right, x + offset, y + 60, offset / 2);
        }
    }
}