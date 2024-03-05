package Anagrams_Project;

import java.io.FileReader;
import java.io.IOException;

//  WORDS. Iterator. Read words, represented as STRINGs, from a text file. Each
//  word is the longest possible contiguous series of alphabetic ASCII CHARs.

class Words
{
  private int ch ;    //  Last CHAR from READER, as an INT.
  private FileReader reader ;    //  Read CHARs from here.
  private StringBuilder word ;   //  Last word read from READER.

//  Constructor. Initialize an instance of WORDS, so it reads words from a file
//  whose pathname is PATH. Throw an exception if we can't open PATH.
  public Words(String path)
  {
    try
    {
      reader = new FileReader(path);
      ch = reader.read();
    }
    catch (IOException ignore)
    {
      throw new IllegalArgumentException("Cannot open '" + path + "'.");
    }
  }
  
//  HAS NEXT. Try to read a WORD from READER, converting it to lower case as we
//  go. Test if we were successful.
  public boolean hasNext()
  {
    word = new StringBuilder();
    while (ch > 0 && ! isAlphabetic((char) ch))
    {
      read();
    }
    while (ch > 0 && isAlphabetic((char) ch))
    {
      word.append(toLower((char) ch));
      read();
    }
    return word.length() > 0;
  }
  
//  IS ALPHABETIC. Test if CH is an ASCII letter.
  private boolean isAlphabetic(char ch)
  {
    return 'a' <= ch && ch <= 'z' || 'A' <= ch && ch <= 'Z';
  }
  
//  NEXT. If HAS NEXT is true, then return a WORD read from READER as a STRING.
//  Otherwise, return an undefined STRING.
  public String next()
  {
    return word.toString();
  }
  
//  READ. Read the next CHAR from READER. Set CH to the CHAR, represented as an
//  INT. If there are no more CHARs to be read from READER, then set CH to -1.
  private void read()
  {
    try
    {
      ch = reader.read();
    }
    catch (IOException ignore)
    {
      ch = -1;
    }
  }
  
//  TO LOWER. Return the lower case ASCII letter which corresponds to the ASCII
//  letter CH.
  private char toLower(char ch)
  {
    if ('a' <= ch && ch <= 'z')
    {
      return ch;
    }
    else
    {
      return (char) (ch - 'A' + 'a');
    }
  }
  
//  MAIN. For testing. Open a text file whose pathname is the 0th argument from
//  the command line. Read words from the file, and print them one per line.
  public static void main(String [] args)
  {
    Words words = new Words(args[0]);
    while (words.hasNext())
    {
      System.out.println("'" + words.next() + "'");
    }
  }
}


class AnagramTree
{
  private class TreeNode
  {
    // A node in the AnagramTree. 
    // It must have four private slots and a private constructor. 
    // The slot summary points to an array of 26 byte’s: it’s the key. T
    // he slot words points to a linear singly linked list of WordNode’s: it’s the value. 
    // The slots left and right point to TreeNode’s, or to null: they’re subtrees
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
    // A node that contains a word. It must have two private slots and a private constructor.
    // The slot word must point to a String that represents the word. 
    // The slot next must point to a WordNode, or to null: it’s the next node in a singly linked linear list.
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
    // Add word to the anagram tree, as described in the previous section. 
    // This String isn’t null, it isn’t empty, and it has only lower case letters. 
    // You must use compareSummaries to control the search through the tree. 
    // You must use the tree’s head node to avoid a special case when you add word to an empty tree.
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
    //Traverse the anagram tree, visiting each of its TreeNode’s exactly once. 
    // You must skip the tree’s head node. Every time you visit a TreeNode, you must print all the words from its linked list of WordNode’s, separated by blanks. 
    // However, if the linked list has only one node, then you must ignore it.
    if (head == null)
    {
      return;
    }
    traversalAnagram(head);
  }
  private void traversalAnagram(TreeNode node)
  {
    //takes a TreeNode object as an argument and recursively traverses the tree in an in-order fashion. 
    // It starts by checking if the current node is null or not, and if it is null, it returns. 
    // If the node is not null, it then calls traversalAnagram recursively on the left child of the current node, then calls printWords on the current node to print the words stored in that node, 
    // and finally calls traversalAnagram recursively on the right child of the current node.
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
    //Here left and right are summaries: arrays of 26 byte’s. 
    // Compare left to right using the comparison algorithm. If left is less than right, then return an int less than 0. 
    // If left equals right, then return 0. 
    // If left is greater than right, then return an int greater than 0.
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
    //Return a summary for word. This String isn’t null, it isn’t empty, and it has only lower case letters. 
    // The summary must be represented as an array of 26 byte’s. If c is a character from word, then you must use the Java expression (c − 'a') to compute c’s index in that array. 
    // You must not use if’s or switch’es to compute c’s index.
    byte[] summary = new byte[26];
    for (int index = 0; index < word.length(); index += 1)
    {
      char c = word.charAt(index);
      summary[c - 'a'] += 1;
    }
    return summary;
  }
}

class Anagrammer
{
  //Make an instance of Words that reads words from a text file. Make an empty AnagramTree. 
  // Read all the words from the text file and add them to the tree. 
  // Finally, traverse the tree to print all its anagrams.
  public static void main(String[] args)
  {
    Words words = new Words("warAndPeace.txt");
    AnagramTree tree = new AnagramTree();
    while (words.hasNext())
    {
      String word = words.next();
      tree.add(word);
    }
    tree.anagrams();
  }
}
