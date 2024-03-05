class AnagramTree
{
  private class TreeNode
  {
    //A node in the AnagramTree. It must have four private slots and a private constructor. The slot summary points to an array of 26 byte’s: it’s the key. The slot words points to a linear singly linked list of WordNode’s: it’s the value. The slots left and right point to TreeNode’s, or to null: they’re subtrees
    private byte[] summary;
    private WordNode words;
    private TreeNode left;
    private TreeNode right;
    private TreeNode(byte[] summary, WordNode word)
    {
      this.summary = summary;
      this.words = word;
      this.left = null;
      this.right = null;
    }
  }
  private class WordNode
  {
    // A node that contains a word. It must have two private slots and a private constructor. The slot word must point to a String that represents the word. The slot next must point to a WordNode, or to null: it’s the next node in a singly linked linear list.
    private String word;
    private WordNode next;
    private WordNode(String word)
    {
      this.word = word;
      this.next = null;
    }
  }
  private TreeNode head;
  public AnagramTree()
  {
    //Constructor. Initialize an empty instance of AnagramTree. It must have a head node.
    head = new TreeNode(new byte[26], null);
  }
  public void add(String word)
  {
    //Add word to the anagram tree, as described in the previous section. This String isn’t null, it isn’t empty, and it has only lower case letters. You must use compareSummaries to control the search through the tree. You must use the tree’s head node to avoid a special case when you add word to an empty tree.
    if (word == null || word.isEmpty())
    {
      return;
    }
    byte[] summary = stringToSummary(word);
    TreeNode current = head;
    TreeNode parent = null;
    int cmp = 0;
    while (current != null)
    {
      cmp = compareSummaries(summary, current.summary);
      if (cmp == 0)
      {
        break;
      }
      parent = current;
      if (cmp < 0)
      {
        current = current.left;
      }
      else
      {
        current = current.right;
      }
    }
    if (current == null)
    {
      current = new TreeNode(summary, null);
      if (parent == null)
      {
        head = current;
      }
      else if (cmp < 0)
      {
        parent.left = current;
      }
      else
      {
        parent.right = current;
      }
    }
    current.words = addWord(current.words, word);
  }
  private WordNode addWord(WordNode node, String word)
  {
    if (node == null)
    {
      return new WordNode(word);
    }
    if (node.word.equals(word))
    {
      return node;
    }
    node.next = addWord(node.next, word);
    return node;
  }
  public void anagrams()
  {
    //Traverse the anagram tree, visiting each of its TreeNode’s exactly once. You must skip the tree’s head node. Every time you visit a TreeNode, you must print all the words from its linked list of WordNode’s, separated by blanks. However, if the linked list has only one node, then you must ignore it.
    if (head == null)
    {
      return;
    }
    traversalAnagram(head);
  }
  private void traversalAnagram(TreeNode node)
  {
    //takes a TreeNode object as an argument and recursively traverses the tree in an in-order fashion. It starts by checking if the current node is null or not, and if it is null, it returns. If the node is not null, it then calls traversalAnagram recursively on the left child of the current node, then calls printWords on the current node to print the words stored in that node, and finally calls traversalAnagram recursively on the right child of the current node.
    if (node == null)
    {
      return;
    }
    traversalAnagram(node.left);
    printWords(node.words);
    traversalAnagram(node.right);
  }
  private void printWords(WordNode node)
  {
    //Takes a WordNode object as an argument and prints the words stored in that node and its linked list. It starts by checking if the current node is null or if its next pointer is null. If either condition is true, it returns. If both conditions are false, it then uses a while loop to traverse the linked list of word nodes, printing each word in the process. Finally, it prints a newline character to move to the next line after all the words have been printed.
    if (node == null || node.next == null)
    {
      return;
    }
    while (node != null)
    {
      System.out.print(node.word + " ");
      node = node.next;
    }
    System.out.println();
  }
  private int compareSummaries(byte[] left, byte[] right)
  {
    //Here left and right are summaries: arrays of 26 byte’s. Compare left to right using the comparison algorithm. If left is less than right, then return an int less than 0. If left equals right, then return 0. If left is greater than right, then return an int greater than 0.
    for (int index = 0; index < 26; index += 1)
    {
      if (left[index] < right[index])
      {
        return -1;
      }
      else if (left[index] > right[index])
      {
        return 1;
      }
    }
    return 0;
  }
  private byte[] stringToSummary(String word)
  {
    //Return a summary for word. This String isn’t null, it isn’t empty, and it has only lower case letters. The summary must be represented as an array of 26 byte’s. If c is a character from word, then you must use the Java expression (c − 'a') to compute c’s index in that array. You must not use if’s or switch’es to compute c’s index.
    byte[] summary = new byte[26];
    for (int index = 0; index < word.length(); index += 1)
    {
      char c = word.charAt(index);
      summary[c - 'a'] += 1;
    }
    return summary;
  }
}
