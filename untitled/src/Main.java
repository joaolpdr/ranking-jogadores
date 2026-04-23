public class Main {

    public static void main(String[] args) {

        BinarySearchTree tree = new BinarySearchTree();

        loadFromCSV(tree, "players.csv");

        new TreeUI(tree).setVisible(true);
    }

    public static void loadFromCSV(BinarySearchTree tree, String filePath) {

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {

            String line;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                String name = parts[0];
                int ranking = Integer.parseInt(parts[1]);

                tree.insert(new Player(name, ranking));
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}