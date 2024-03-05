class Anagrammer
{
  // Make an instance of Words that reads words from a text file. Make an empty AnagramTree. Read all the words from the text file and add them to the tree. Finally, traverse the tree to print all its anagrams.
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
