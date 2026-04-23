import javax.swing.*;
import java.awt.*;

public class TreeUI extends JFrame {

    private BinarySearchTree tree;
    private TreePanel treePanel;

    public TreeUI(BinarySearchTree tree) {
        this.tree = tree;

        setTitle("Ranking de Jogadores");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // painel da árvore
        treePanel = new TreePanel(tree.root);
        add(treePanel, BorderLayout.CENTER);

        // botão
        JButton showButton = new JButton("Mostrar Árvore");
        showButton.addActionListener(e -> {
            treePanel.setRoot(tree.root);
        });

        add(showButton, BorderLayout.SOUTH);
    }
}