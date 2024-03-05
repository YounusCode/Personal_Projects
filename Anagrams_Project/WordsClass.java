class Words
{
  private int           ch;      //  Last CHAR from READER, as an INT.
  private FileReader    reader;  //  Read CHARs from here.
  private StringBuilder word;    //  Last word read from READER.

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
