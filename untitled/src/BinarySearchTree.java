public class BinarySearchTree {

    Node root;

    public BinarySearchTree() {
        root = null;
    }

    public void insert(Player player) {
        root = insertRec(root, player);
    }

    private Node insertRec(Node current, Player player) {

        if (current == null) {
            return new Node(player);
        }

        if (player.getRanking() < current.player.getRanking()) {
            current.left = insertRec(current.left, player);
        } else {
            current.right = insertRec(current.right, player);
        }

        return current;
    }

    public void inOrder() {
        inOrderRec(root);
    }

    private void inOrderRec(Node current) {
        if (current != null) {
            inOrderRec(current.left);
            System.out.println(current.player);
            inOrderRec(current.right);
        }
    }

    public Player search(String name) {
        return searchRec(root, name);
    }

    private Player searchRec(Node current, String name) {

        if (current == null) {
            return null;
        }

        // encontrou
        if (current.player.getNickname().equalsIgnoreCase(name)) {
            return current.player;
        }

        // busca na esquerda
        Player leftResult = searchRec(current.left, name);
        if (leftResult != null) {
            return leftResult;
        }

        // busca na direita
        return searchRec(current.right, name);
    }

    public Player remove(String name) {
        Player player = search(name);

        if (player != null) {
            root = removeRec(root, player.getRanking());
        }

        return player;
    }

    private Node removeRec(Node current, int ranking) {

        if (current == null) return null;

        if (ranking < current.player.getRanking()) {
            current.left = removeRec(current.left, ranking);

        } else if (ranking > current.player.getRanking()) {
            current.right = removeRec(current.right, ranking);

        } else {
            // encontrou o nó

            // caso 1: sem filhos
            if (current.left == null && current.right == null) {
                return null;
            }

            // caso 2: um filho
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // caso 3: dois filhos
            Node smallest = findMin(current.right);
            current.player = smallest.player;
            current.right = removeRec(current.right, smallest.player.getRanking());
        }

        return current;


    }

    private Node findMin(Node node) {
        while (node.left != null) {
            node = node.left;
        }
        return node;
    }
}