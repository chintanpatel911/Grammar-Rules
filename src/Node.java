
public class Node {
	public String value;
    private Node left;
    private Node right;


    public Node(String value, Node left, Node right) {
        this.value = value;
        this.left = left;
        this.right = right;
    }

    public Object getValue() {
        return value;
    }
    
    public Node getLeftChildNode() {
        return left;
    }
    
    public Node getRightChildNode() {
        return right;
    }

    public void setLeftChildNode(Node node) {
        left = node;
    }

    public void setRightChildNode(Node node) {
        right = node;
    }
    
	}

