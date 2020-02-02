public class ListNode {


    /*holds the node's data*/
    private String nodeData;

    /*holds the pointer to the next node*/
    private String nextNode;

    /**
     * Creates a linked list Node object.
     * @param data The data the node holds
     */
   public ListNode(String data) {
       nodeData = data;
       nextNode = null;
   }

    /**
     * @return Current node data
     */
   public String getNodeData(){
       return nodeData;
   }

    /**
     * @return Following node
     */
    public String getNextNode() {
        return nextNode;
    }

    /**
     * Sets pointer to next node.
     * @param nextNode The node that should follow.
     */
    public void setNextNode(String nextNode) {
        this.nextNode = nextNode;
    }

}
