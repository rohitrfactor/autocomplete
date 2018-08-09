class Solution{

public static void main(String[] args){
     Node root = new Node();
     root.insert("Gurgaon");
     root.insert("Gurdaspur");
     root.insert("Hisar");
     root.fetchSuggestions("Gu");
}

}


class Node{
    private int MAX_CHILD_COUNT = 34;
    private Node[] children;
    private boolean isWordEnd;
    private String endWord;


    public Node() {
        this.children = new Node[MAX_CHILD_COUNT];
    }

    public int charToIndex(char c){
        switch(c) {
        case ' ':
        	return 0;
        case '(':
        		return 27;
        case ')':
        		return 28;
        case '&':
        		return 29;
        case '-':
        		return 30;
        case '.':
        		return 31;
        case '2':
        		return 32;
        case '4':
        		return 33;
        	default :
        		return (int)c - (int)'a'+1;
        }
        
    }
    public char intToChar(int index){
        switch(index) {
        case 0:
        	return ' ';
        case 27:
        		return '(';
        case 28:
        		return ')';
        case 29:
    		return '&';
        case 30:
        	return '-';
        case 31:
        	return '.';
        case 32:
    		return '2';
        case 33:
    		return '4';
        	default :
        		return (char)(index + (int)'a' - 1);
        }
    }

    public void insert(String inputString) {
        Node currentNode = this;
        String input = inputString.toLowerCase();
        for(int i=0;i<input.length();i++) {
            currentNode = insertNode(currentNode,input.charAt(i));
        }
        currentNode.isWordEnd = true;
        currentNode.endWord = inputString;
    }

    public Node insertNode(Node node,char c) {
        Node currentNode = node;
        int index = charToIndex(c);
        if(currentNode.getChild(index) == null) {
            currentNode.children[index] = new Node();
        }
        currentNode = currentNode.getChild(index);
        return currentNode;
    }
    public Node getChild(int index){
        if(this.children[index]!=null){
            return this.children[index];
        }
        return null;
    }

    public ArrayList<String> possibleString(ArrayList<String> results,Node node,String prefix) {
        Node currentNode = node;
        for(int i=0;i<MAX_CHILD_COUNT;i++) {
            if(currentNode !=null) {
                    StringBuilder stringBuilder = new StringBuilder(prefix);
                    stringBuilder.append(intToChar(i));
                    if(currentNode.getChild(i) !=null) {
                        possibleString(results,currentNode.getChild(i),stringBuilder.toString());
                    }
            }
        }
        if(currentNode.isWordEnd) {
            results.add(currentNode.endWord);
        }
        return results;
    }

    public Node suggestion(Node node,char c) {
        Node currentNode = node;
        int index = charToIndex(c);
        if(currentNode.getChild(index) != null) {
            currentNode = currentNode.getChild(index);
        }
        return currentNode;
    }

    public ArrayList<String> fetchSuggestions(String query) {
        Node currentNode = this;
        query = query.toLowerCase();
        StringBuilder found = new StringBuilder();
        for(int i=0;i<query.length();i++) {
            if(currentNode != null && !currentNode.isWordEnd) {
            	Node prevNode = currentNode;
                currentNode = suggestion(currentNode,query.charAt(i));
                if(!currentNode.equals(prevNode)) {
                	found.append(query.charAt(i));
                }
            }
        }
        ArrayList<String> results = new ArrayList<String>();
        if(currentNode != null && !currentNode.isWordEnd && currentNode !=this) {   
            results = possibleString(results,currentNode,"");
        }
        return results;
    }
}
