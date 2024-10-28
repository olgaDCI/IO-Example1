import java.io.*;
import java.net.URL;
import java.util.Objects;
import java.util.Scanner;

/**
 * Allows its users to encode a message stored in a file using the Caesar Cipher. 
 *
 * <p>The decoded message is stored in a file.
 *
 * <p>Specification:
 *
 * <p>input(input file): a sequence of characters.
 *
 * <p>output(output file): the sequence of encoded input characters.
 */
public class Encode {
  public static void main(String[] args) {
    BufferedReader inStream = null;
    BufferedWriter outStream = null;

    try {

      // 1. Create theKeyboard
      try (Scanner theKeyboard = new Scanner(System.in)) {

        // 2. Display introductory message
        System.out.println(
                "\nThis program uses the Caesar cipher to encode the contents of a"
                        + "\nfile and writes the encoded characters to another file.");

        // 3. Prompt for and read name of the input file.
        System.out.print("\nEnter the name of the input file: ");

        String inFileName = theKeyboard.nextLine();

        URL resource = Encode.class.getClassLoader().getResource("");
        Objects.requireNonNull(resource);

        // 4. Open a BufferedReader named inStream for input from inFileName
        // 5. If inStream failed to open, display an error message and quit
        inStream = new BufferedReader(new FileReader(resource.getPath() + inFileName));

        // 6. Prompt for and read name of the input file.
        System.out.print("\nEnter the name of the output file: ");
        String outFileName = theKeyboard.nextLine();

        // 7. Open an BufferedWriter named outStream for output to outFileName
        // 8. If outStream failed to open, display an error message and quit
         outStream = new BufferedWriter(new FileWriter(resource.getPath() + outFileName));

        int inValue = 0;
        char outChar = ' ';

        // 9. Loop
        while (true) {
          // i. read a character from the input file via inStream into inValue
          inValue = inStream.read();
          // ii. if end-of-file was reached, terminate repetition
          if (inValue == -1) {
            break; // End the loop if EOF is reached
          }
          // iii. encode the character using the Caesar cipher
          outChar = caesarEncode2((char) inValue, 3);

          // iv. write the encoded character to the output file via outStream
          outStream.write(outChar);
        }

      // 11. display a 'successful completion' message
      System.out.println("\nProcessing complete.\n Encoded message is in " + resource.getPath() + outFileName);
    }
    } catch (IOException | NullPointerException e) {
      System.err.println("Failed to open file name: " + e.getMessage());
      System.exit(1);
    }
    finally {
      try {
        // 10.i. close the connection to the input file
        if (inStream != null) inStream.close();
        // 10.i. close the connection to the output file
        if (outStream != null) outStream.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
    }
  }

  /*********************************************************************
   * caesarEncode implements the Caesar cipher encoding scheme.        *
   *                                                                   *
   * Receive: ch, a character.                                         *
   *          key, the amount by which to rotate ch.                   *
   * Return:  The character that is key positions after ch,            *
   *          with "wrap-around" to the beginning of the sequence.     *
   *********************************************************************/

  public static char caesarEncode(char ch, int key) {
    final int FIRST_UPPER = 65, FIRST_LOWER = 97, NUM_CHARS = 26;

    if (key <= 0 || key >= NUM_CHARS) {
      System.err.println("\n*** CaesarEncode: key must be between 1 and 25\n");
      System.exit(1);
    }

    if (Character.isUpperCase(ch))
      return (char) ((ch - FIRST_UPPER + key) % NUM_CHARS + FIRST_UPPER);
    else if (Character.isLowerCase(ch))
      return (char) ((ch - FIRST_LOWER + key) % NUM_CHARS + FIRST_LOWER);
    else return ch;
  }

  public static char caesarEncode2(char character, int shift) {
    if (Character.isLetter(character)) {
      char base = Character.isUpperCase(character) ? 'A' : 'a';
      return (char) ((character - base + shift) % 26 + base);
    }
    return character; // Non-alphabetic characters remain unchanged
  }
} // end class
