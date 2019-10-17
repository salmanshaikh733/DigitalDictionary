public class Node {

    //declare instance variables
    private Record root;
    private Node leftC, rightC;
    int height;


    //create a node with a root and left and right children and initialize the left and right children
    public Node(Record newRecord) {
        root = newRecord;
        this.rightC = null;
        this.leftC = null;
        //this.parent=null;
        //this.height=0;

    }

    public void setRoot(Record root) {
        this.root = root;
    }


    public Node getLeftC() {
        //if the node is empty make the node a empty node
        if(leftC==null){
            leftC=new Node(null);
        }
        return leftC;
    }

    public Node getRightC() {
        //if the node is empty make the node a null node
        if(rightC==null) {
             rightC= new Node(null);
        }
        return rightC;
    }


    public Record getRoot() {
        return root;
    }

    /*public void hCounter(){
        height++;
    }*/
    /*public int getHeight(){
        return height;
    }*/


}

