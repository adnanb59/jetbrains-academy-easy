import coders.*;
import java.io.*;
import java.util.*;

public class Runner {
    public static void main(String[] args) {
        // -- INITIALIZE VARIABLES --
        Coder m = null;
        boolean doDecrypt = false, hasError = false;
        int shift = 0;
        String data = null, inFile = null, outFile = null;

        // -- PARSE COMMAND LINE ARGUMENTS --
        // Go through command line arguments and update variables
        // Check every other (for checking flags specifically)
        for (int i = 0; i < args.length; i += 2) {
            switch(args[i]) {
                case "-mode":
                    // Error occurs if there is no value for the argument or the value is not enc or dec
                    hasError = i >= args.length-1 || !("enc".equals(args[i+1]) || "dec".equals(args[i+1]));
                    if (!hasError) doDecrypt = "dec".equals(args[i+1]); // set decrypt flag
                    break;
                case "-key":
                    hasError = i >= args.length-1;
                    if (!hasError) {
                        // Read input and try to convert to an integer for a valid shift (negative works too)
                        try {
                            shift = Integer.parseInt(args[i+1]);
                        } catch (NumberFormatException e) {
                            hasError = true;
                        }
                    }
                    break;
                case "-data":
                    hasError = i >= args.length-1;
                    if (!hasError) data = args[i+1];
                    break;
                case "-in":
                    hasError = i >= args.length-1;
                    if (!hasError && data == null) inFile = args[i+1];
                    break;
                case "-out":
                    hasError = i >= args.length-1;
                    if (!hasError && data == null) outFile = args[i+1];
                    break;
                case "-alg":
                    // Error occurs if there is no value for the argument or the value is not enc or dec
                    hasError = i >= args.length-1 || !("shift".equals(args[i+1]) || "unicode".equals(args[i+1]));
                    if (!hasError) m = ("unicode".equals(args[i+1])) ? new Unicode() : new Shift();
                    break;
                default:
                    hasError = true;
                    break;
            }
        }

        // -- COMPLETE ACTION BASED ON ARGUMENTS --
        // Either do an encryption/decryption or return an error string
        String ret;
        if (hasError) {
            ret = "Error. Correct input: java Runner [-mode enc|dec] [-key <int>] [-data \"<String>\"]" +
                   "[-out <String>] [-in <String>] [-alg shift|unicode]";
        } else {
            if (m == null) m = new Shift(); // if -alg not set, use shift algorithm

            // Only use file from -in flag if no -data is provided, if both are not set then use empty string
            if (data == null && inFile != null) {
                // Read through file and do encryption/decryption per line
                try (Scanner in = new Scanner(new File(inFile))) {
                    StringBuilder sb = new StringBuilder();
                    while (in.hasNextLine()) {
                        String tmp = in.nextLine();
                        // Keeping format of file, add newline to append()
                        sb.append(doDecrypt ? m.decrypt(tmp, shift) : m.encrypt(tmp, shift) + "\n");
                    }
                    ret = sb.toString().trim(); // Remove excess whitespace at end of result
                } catch (FileNotFoundException e) {
                    ret = "Error. File not found: " + inFile;
                }
            } else {
                ret = doDecrypt ? m.decrypt(data == null ? "" : data, shift) :
                                  m.encrypt(data == null ? "" : data, shift);
            }
        }

        // -- OUTPUT RESULT --
        // After processing the text, take the result and provide it to format desired by user
        if (outFile == null) {
            System.out.println(ret);
        } else { // Write to file specified in -out flag
            try (FileWriter fw = new FileWriter(outFile)) {
                fw.write(ret);
            } catch (IOException e) { // If writing fails, just print result
                System.err.println(e.getMessage());
            }
        }
    }
}