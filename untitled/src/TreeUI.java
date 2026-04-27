import javax.swing.*;
import java.awt.*;

public class TreeUI extends JFrame {

    private BinarySearchTree tree;
    private TreePainel treePainel;
    private JTextField fieldName;
    private JTextField fieldRanking;
    private JLabel labelResult;

    public TreeUI(BinarySearchTree tree) {
        this.tree = tree;

        setTitle("Ranking de Jogadores");
        setSize(900, 620);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        treePainel = new TreePainel(tree.root);

        JScrollPane scrollPane = new JScrollPane(treePainel);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.getHorizontalScrollBar().setUnitIncrement(20);
        scrollPane.getVerticalScrollBar().setUnitIncrement(20);
        add(scrollPane, BorderLayout.CENTER);

        JPanel bottom = new JPanel();
        bottom.setLayout(new BoxLayout(bottom, BoxLayout.Y_AXIS));

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        controls.add(new JLabel("Nickname:"));
        fieldName = new JTextField(10);
        controls.add(fieldName);
        controls.add(new JLabel("Ranking:"));
        fieldRanking = new JTextField(5);
        controls.add(fieldRanking);

        JButton btnInsert = new JButton("Inserir");
        JButton btnSearch = new JButton("Buscar");
        JButton btnRemove = new JButton("Remover");
        controls.add(btnInsert);
        controls.add(btnSearch);
        controls.add(btnRemove);

        JPanel resultPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 12, 4));
        labelResult = new JLabel(" ");
        labelResult.setFont(new Font("SansSerif", Font.BOLD, 13));
        resultPanel.add(labelResult);

        bottom.add(controls);
        bottom.add(resultPanel);
        add(bottom, BorderLayout.SOUTH);

        btnInsert.addActionListener(e -> {
            String name = fieldName.getText().trim();
            String rankText = fieldRanking.getText().trim();
            if (name.isEmpty() || rankText.isEmpty()) {
                labelResult.setForeground(Color.RED);
                labelResult.setText("Preencha nickname e ranking.");
                return;
            }
            try {
                int ranking = Integer.parseInt(rankText);
                tree.insert(new Player(name, ranking));
                treePainel.setRoot(tree.root);
                labelResult.setForeground(new Color(0x1D9E75));
                labelResult.setText("Inserido: " + name + " (#" + ranking + ")");
            } catch (NumberFormatException ex) {
                labelResult.setForeground(Color.RED);
                labelResult.setText("Ranking deve ser um numero inteiro.");
            }
        });

        btnSearch.addActionListener(e -> {
            String name = fieldName.getText().trim();
            if (name.isEmpty()) {
                labelResult.setForeground(Color.RED);
                labelResult.setText("Informe o nickname para buscar.");
                return;
            }
            Player found = tree.search(name);
            if (found != null) {
                labelResult.setForeground(new Color(0x1A6FBB));
                labelResult.setText("Encontrado: " + found.getNickname() + " (#" + found.getRanking() + ")");
            } else {
                labelResult.setForeground(Color.RED);
                labelResult.setText("Jogador nao encontrado: " + name);
            }
        });

        btnRemove.addActionListener(e -> {
            String name = fieldName.getText().trim();
            if (name.isEmpty()) {
                labelResult.setForeground(Color.RED);
                labelResult.setText("Informe o nickname para remover.");
                return;
            }
            Player removed = tree.remove(name);
            treePainel.setRoot(tree.root);
            if (removed != null) {
                labelResult.setForeground(new Color(0xC0570E));
                labelResult.setText("Removido: " + removed.getNickname() + " (#" + removed.getRanking() + ")");
            } else {
                labelResult.setForeground(Color.RED);
                labelResult.setText("Jogador nao encontrado: " + name);
            }
        });
    }
}