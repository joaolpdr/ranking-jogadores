public class Main {

    public static void main(String[] args) {

        BinarySearchTree tree = new BinarySearchTree();

        loadFromCSV(tree, "players.csv");

        new TreeUI(tree).setVisible(true);
    }

    public static void loadFromCSV(BinarySearchTree tree, String filePath) {

        try (java.io.BufferedReader br = new java.io.BufferedReader(new java.io.FileReader(filePath))) {

            String line;
            boolean firstLine = true;

            while ((line = br.readLine()) != null) {
                line = line.trim();

                if (firstLine) {
                    firstLine = false;
                    if (line.toLowerCase().startsWith("nickname")) {
                        continue;
                    }
                }

                if (line.isEmpty()) continue;

                String[] parts = line.split(",");
                String name = parts[0].trim();
                int ranking = Integer.parseInt(parts[1].trim());

                tree.insert(new Player(name, ranking));
            }

        } catch (Exception e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
    }
}