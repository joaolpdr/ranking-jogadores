import javax.swing.*;
import java.awt.*;

public class TreePainel extends JPanel {

    private Node root;

    private static final int NODE_RADIUS  = 30;
    private static final int LEVEL_HEIGHT = 70;
    private static final int H_GAP        = 12;

    private static final Color[] LEVEL_COLORS = {
            new Color(0x1A6FBB),
            new Color(0x1D9E75),
            new Color(0xC0570E),
            new Color(0x8B3DBF),
            new Color(0xB33030),
    };

    private static final Color EDGE_COLOR = new Color(0x888780);
    private static final Color TEXT_NAME  = Color.WHITE;
    private static final Color TEXT_RANK  = new Color(255, 255, 255, 180);
    private static final Color BG_COLOR   = new Color(0xF7F6F2);

    public TreePainel(Node root) {
        this.root = root;
        setBackground(BG_COLOR);
        updatePreferredSize();
    }

    public void setRoot(Node root) {
        this.root = root;
        updatePreferredSize();
        repaint();
    }

    private void updatePreferredSize() {
        int leaves = countLeaves(root);
        int h      = height(root);
        int width  = Math.max(leaves * (NODE_RADIUS * 2 + H_GAP) + 80, 600);
        int height = Math.max(h * LEVEL_HEIGHT + NODE_RADIUS * 2 + 40, 300);
        setPreferredSize(new Dimension(width, height));
        revalidate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (root == null) {
            g2.setColor(new Color(0x888780));
            g2.setFont(new Font("SansSerif", Font.PLAIN, 14));
            String msg = "Arvore vazia";
            FontMetrics fm = g2.getFontMetrics();
            g2.drawString(msg, (getWidth() - fm.stringWidth(msg)) / 2, getHeight() / 2);
            return;
        }

        int leafCount   = countLeaves(root);
        int nodeSlot    = NODE_RADIUS * 2 + H_GAP;
        int totalWidth  = leafCount * nodeSlot;
        int startX      = (Math.max(getWidth(), totalWidth) - totalWidth) / 2 + totalWidth / 2;
        int startY      = NODE_RADIUS + 14;
        int spread      = totalWidth / 2;

        drawEdges(g2, root, startX, startY, spread);
        drawNodes(g2, root, startX, startY, spread, 0);
    }

    private void drawEdges(Graphics2D g2, Node node, int x, int y, int spread) {
        if (node == null) return;

        int leftLeaves  = countLeaves(node.left);
        int rightLeaves = countLeaves(node.right);
        int totalLeaves = Math.max(leftLeaves + rightLeaves, 1);
        int childY      = y + LEVEL_HEIGHT;

        g2.setStroke(new BasicStroke(1.5f, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g2.setColor(EDGE_COLOR);

        if (node.left != null) {
            int ls = (spread * leftLeaves) / totalLeaves;
            int cx = x - (spread - ls);
            drawEdgeLine(g2, x, y, cx, childY);
            drawEdges(g2, node.left, cx, childY, ls);
        }

        if (node.right != null) {
            int rs = (spread * rightLeaves) / totalLeaves;
            int cx = x + (spread - rs);
            drawEdgeLine(g2, x, y, cx, childY);
            drawEdges(g2, node.right, cx, childY, rs);
        }
    }

    private void drawEdgeLine(Graphics2D g2, int x1, int y1, int x2, int y2) {
        double dx = x2 - x1, dy = y2 - y1;
        double dist = Math.sqrt(dx * dx + dy * dy);
        if (dist == 0) return;
        double nx = dx / dist, ny = dy / dist;
        g2.drawLine(
                (int)(x1 + nx * NODE_RADIUS), (int)(y1 + ny * NODE_RADIUS),
                (int)(x2 - nx * NODE_RADIUS), (int)(y2 - ny * NODE_RADIUS)
        );
    }

    private void drawNodes(Graphics2D g2, Node node, int x, int y, int spread, int level) {
        if (node == null) return;

        int leftLeaves  = countLeaves(node.left);
        int rightLeaves = countLeaves(node.right);
        int totalLeaves = Math.max(leftLeaves + rightLeaves, 1);
        int childY      = y + LEVEL_HEIGHT;

        g2.setColor(new Color(0, 0, 0, 25));
        g2.fillOval(x - NODE_RADIUS + 2, y - NODE_RADIUS + 3, NODE_RADIUS * 2, NODE_RADIUS * 2);

        Color base = LEVEL_COLORS[Math.min(level, LEVEL_COLORS.length - 1)];
        g2.setColor(base);
        g2.fillOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        g2.setColor(base.darker());
        g2.setStroke(new BasicStroke(1.5f));
        g2.drawOval(x - NODE_RADIUS, y - NODE_RADIUS, NODE_RADIUS * 2, NODE_RADIUS * 2);

        String name = node.player.getNickname();
        if (name.length() > 8) name = name.substring(0, 7) + "...";
        g2.setFont(new Font("SansSerif", Font.BOLD, 13));
        FontMetrics fm = g2.getFontMetrics();
        g2.setColor(TEXT_NAME);
        g2.drawString(name, x - fm.stringWidth(name) / 2, y - 2);

        String rank = "#" + node.player.getRanking();
        g2.setFont(new Font("SansSerif", Font.PLAIN, 11));
        fm = g2.getFontMetrics();
        g2.setColor(TEXT_RANK);
        g2.drawString(rank, x - fm.stringWidth(rank) / 2, y + 11);

        if (node.left != null) {
            int ls = (spread * leftLeaves) / totalLeaves;
            drawNodes(g2, node.left, x - (spread - ls), childY, ls, level + 1);
        }
        if (node.right != null) {
            int rs = (spread * rightLeaves) / totalLeaves;
            drawNodes(g2, node.right, x + (spread - rs), childY, rs, level + 1);
        }
    }

    private int countLeaves(Node node) {
        if (node == null) return 0;
        if (node.left == null && node.right == null) return 1;
        return countLeaves(node.left) + countLeaves(node.right);
    }

    private int height(Node node) {
        if (node == null) return 0;
        return 1 + Math.max(height(node.left), height(node.right));
    }
}