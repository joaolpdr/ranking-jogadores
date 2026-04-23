public class Node {
    Player player;
    Node left;
    Node right;

    public Node(Player player) {
        this.player = player;
        this.left = null;
        this.right = null;
    }

    private Node removeRec(Node current, int ranking) {

        if (current == null) return null;

        if (ranking < current.player.getRanking()) {
            current.left = removeRec(current.left, ranking);

        } else if (ranking > current.player.getRanking()) {
            current.right = removeRec(current.right, ranking);

        } else {
            // ENCONTROU O NÓ

            // 🟢 caso 1: sem filhos
            if (current.left == null && current.right == null) {
                return null;
            }

            // 🟡 caso 2: um filho
            if (current.left == null) return current.right;
            if (current.right == null) return current.left;

            // 🔴 caso 3: dois filhos
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