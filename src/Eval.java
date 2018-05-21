import java.util.Stack;
    
  public class Eval{

	int idx;
	String[] S;
	String token;
    String lparen = "(";
    Node root;
    String tmp;
	
	public static void main(String[] args){
		Eval e = new Eval();
		e.go(args[0]);
	}
	
	private void go(String in){
        S = in.split(" ");
		root = new Node();
        stmt(root);
        tmp = "";
        nodesPreOrder(root);
        System.out.print(tmp.trim() + " = ");
        tmp = "";
        nodesPostOrder(root);
        System.out.println(compute());
    }
	
	
	public void stmt(Node node){
		expr(node);
	}

	public void expr(Node node){
		lex();
		if(token.equals(lparen)){
			lex(); //match lparen
			term(node);
			lex(); //match rparen
		}
		else{
			term(node);
		}
		if(lookAhead().equals("+") || lookAhead().equals("-") || 
                lookAhead().equals("*") || lookAhead().equals("/")){
            lex();
			Node newnode = new Node(); 
            newnode.value = token;
            newnode.left = node;
            node.parent = newnode;
            root = newnode;
            lex(); //match lparen
            lex();
			term(newnode.left);
			lex(); //match rparen
		}
	}

	public void term(Node node){
		if(isNumeric(token)){
			factor(node.left);
		}
		lex();
		if(token.equals("+") || token.equals("-")){
			node.value = token;
            lex();
			factor(node.right);
		}
		if(lookAhead().equals("+") || lookAhead().equals("-")){
            lex();
            Node newnode = new Node();
            newnode.left = node;
            node = newnode;
            node.value = token;
            lex();
			term(node.right);
		}
	}

	public void factor(Node node){
        if (isNumeric(token)) {
            node.value = token;
        }
        if(lookAhead().equals("*") || lookAhead().equals("/")){
            lex();
            Node newnode = new Node();
            newnode.value = token;
            newnode.left.value = node.value;
            node.parent.right = newnode;
            lex();
			factor(newnode.right);
		}
    }

	public void lex(){
		if(idx < S.length){
			token = S[idx++];
		}
		else{
			token = "";
		}
	}

	public String lookAhead(){
		if((idx) < S.length){
            return S[idx];
		}
		else{
			return "";
		}
	}

    public boolean isNumeric(String arg){
        try{
            Integer.parseInt(arg);
        }
        catch(Exception e){
            return false;
        }
        return true;
  }
	
	public void nodesPreOrder(Node node){
		if(node.value != null){
			tmp += node.value + " ";
			if(node.left != null){
				nodesPreOrder(node.left);
			}
			if(node.right != null){
				nodesPreOrder(node.right);
			}
		}
    }

	public void nodesPostOrder(Node node){
		if(node.value != null){
			if(node.left != null){
                nodesPostOrder(node.left);
			}
			if(node.right != null){
                nodesPostOrder(node.right);
			}
            tmp += node.value + " ";
		}
    }

	public void nodesInOrder(Node node){
		if(node.value != null){
			if(node.left != null){
                nodesInOrder(node.left);
			}
            tmp += node.value + " ";
            if (node.right != null) {
                nodesInOrder(node.right);
			}
		}
	}

    public String compute() {
        
    	//Stack Machine to Compute Result
        Stack<String> stack = new Stack<String>();
        int ret = 0;
        tmp = "";
        nodesPostOrder(root);
        String[] arr = tmp.trim().split(" ");
        for(int i = 0; i < arr.length; i++){
            if(isNumeric(arr[i])){
                stack.push(arr[i]);
            }
            else{
                ret = op(arr[i], stack.pop(), stack.pop());
                stack.push(String.valueOf(ret));
            }
        }
        return stack.pop();
    }

    public int op(String symb, String opnd2, String opnd1) {
        if (symb.equals("+"))
            return Integer.parseInt(opnd1) + Integer.parseInt(opnd2);
        else if (symb.equals("-"))
            return Integer.parseInt(opnd1) - Integer.parseInt(opnd2);
        else if (symb.equals("*"))
            return Integer.parseInt(opnd1) * Integer.parseInt(opnd2);
        else if (symb.equals("/"))
            return Integer.parseInt(opnd1) / Integer.parseInt(opnd2);
        else
            return 0;
    }
	
	public class Node{

		public String value;
		public Node left;
		public Node right;
        public Node parent;
		
		public Node(String value, Node left, Node right, Node parent){
			this.value = value;
			this.left = left;
			this.right = right;
            this.parent = parent;
		}
		public Node(){
			this.value = "+";
			this.left = new Node("0", null, null, this);
			this.right = new Node("0", null, null,this);
		}

	} //end class Node

} //end class Eval
