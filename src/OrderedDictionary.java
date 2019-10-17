import com.sun.org.apache.regexp.internal.RE;





public class OrderedDictionary implements OrderedDictionaryADT {

    //declare instance variables
    private Node newDict;

    //create an empty ordredDictionary aka the binary search tree use
    public OrderedDictionary() {
        newDict = new Node(null);
    }


    //finder method to find node with pair k
    private Node nodeFinder(Pair k) {

        Node n = newDict;
        int compVal;

        while (n.getRoot() != null) {
            compVal = n.getRoot().getKey().compareTo(k);

            if (compVal == 0) {
                return n;
            } else if (compVal < 0) {
                n = n.getLeftC();
            } else if (compVal > 0) {
                n = n.getRightC();
            }
        }
        return n;
    }


    //find and get the pair equal to k or return null it if does not exist
    public Record get(Pair k) {

        Node nextNode = nodeFinder(k);
       // return nextNode.getRoot();
        Record returnVal = null;
        while (nextNode.getRoot() != null) {
            if (nextNode.getRoot().getKey().compareTo(k) == 0) {
                returnVal = nextNode.getRoot();
                break;
            } else if (nextNode.getRoot().getKey().compareTo(k) == -1) {
                nextNode = nextNode.getLeftC();
            } else if (nextNode.getRoot().getKey().compareTo(k) == 1) {
                nextNode = nextNode.getRightC();
            }
        }


        return returnVal;


    }


    //put the specified record in the correct position
    public void put(Record r) throws DictionaryException {

        Pair rPair = r.getKey();
        Node foundedNode = nodeFinder(rPair);
        if (foundedNode.getRoot() != null) {
            throw new DictionaryException();
        }
        foundedNode.setRoot(r);
        //newDict.hCounter();
    }

    public void remove(Pair k) throws DictionaryException {

        //first see if the word is in the tree or not
        if (get(k) == null) {
            throw new DictionaryException();
        }
        //if the word is in the tree
        else {
            //get the left and right nodes of the node we are trying to remove
            Node node = nodeFinder(k);
            Node rNode = node.getRightC();
            Node lNode = node.getLeftC();
            Record tempHolder;

            //if leaf node
            if (rNode.getRoot() == null && lNode.getRoot() == null) {
                node.setRoot(null);
            }
            //if right is not empty
            else if (rNode.getRoot() != null && lNode.getRoot() == null) {
                tempHolder = rNode.getRoot();
                remove(tempHolder.getKey());
                //rNode.setRoot(null);
                node.setRoot(tempHolder);


            }
            //if the left is not empty
            else if (rNode.getRoot() == null && lNode.getRoot() != null) {
                tempHolder = lNode.getRoot();
                remove(tempHolder.getKey());
                //lNode.setRoot(null);
                node.setRoot(tempHolder);

            }
            //if both are full set set the current node we are trying to remove to be the smallest value in the tree
            else if (rNode.getRoot() != null && lNode.getRoot() != null) {
                tempHolder = predecessor(k);
                //node.setRoot(tempHolder);
                remove(tempHolder.getKey());
                node.setRoot(tempHolder);

            }
        }
    }

    /*private Record smallestTree(Record tree){

        Node nextNode = nodeFinder(tree.getKey());
        while (nextNode.getLeftC().getRoot() != null) {
            nextNode = nextNode.getLeftC();
        }
        return nextNode.getRoot();

    }*/

    // finds the successor
    public Record successor(Pair k) {

        Node n = newDict;
        Node tempNode = new Node(null);
        int compVal;

        while (n.getRoot() != null) {
            compVal = n.getRoot().getKey().compareTo(k);
            //if node is greater than pair go left
            if (compVal == -1) {
                tempNode = n;
                n = n.getLeftC();
            } else {
                n = n.getRightC();
            }
        }
        return tempNode.getRoot();
    }

    //finds the predecessor
    public Record predecessor(Pair k) {
        Node n = newDict;
        Node tempNode = new Node(null);
        int compVal;

        while (n.getRoot() != null) {
            compVal = n.getRoot().getKey().compareTo(k);
            //if node is less than the pair go right
            if (compVal == 1) {
                tempNode = n;
                n = n.getRightC();
            } else {
                n = n.getLeftC();
            }
        }
        return tempNode.getRoot();

    }

    //finds the smallest value in the binary tree
    public Record smallest() {
        Node nextNode = newDict;
        while (nextNode.getLeftC().getRoot() != null) {
            nextNode = nextNode.getLeftC();
        }
        return nextNode.getRoot();

    }

    //finds the largest value in the binary search tree
    public Record largest() {

        Node nextNode = newDict;
        while (nextNode.getRightC().getRoot() != null) {
            nextNode = nextNode.getRightC();
        }
        return nextNode.getRoot();
    }


}


