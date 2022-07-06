import java.io.FileInputStream;
import java.io.IOException;
import java.util.regex.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.channels.FileChannel;

public class tryout {

    public static CharSequence fromFile(String filename) throws IOException {
        try (FileInputStream fis = new FileInputStream(filename)) {

            FileChannel fc = fis.getChannel();
            // Create a read-only CharBuffer on the file --> CharSequence is a read only
            // sequence of chars
            // fc.Map maps the FileChannel (specified through the args 0: beginning of file
            // to fc.size() (to the end)) into memory = Buffer
            ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc.size());
            // Since we need a Charsequence for p.matcher(input) we define a Charset (a
            // named mapping between chars and bytes) which is decoded from the bytebuffer
            CharBuffer cbuf = Charset.forName("UTF-8").newDecoder().decode(bbuf);
            // cbuf is now a sequence of chars which we created from the byteBuffer, which
            // we created from the FileChannel
            return cbuf;

        } catch (Exception e) {
            System.out.println("File to CharSequence failed:\n");
            e.printStackTrace();
        }
        return "";
    }

    public static String patternFinder(String userInput) throws IOException{
        Pattern USER_INPUT = Pattern.compile(userInput + "\\s+(.+)");
        Matcher mui = USER_INPUT.matcher(fromFile("Pattern.txt"));
        String p = "";

        try{
            if(mui.find()){
                p = mui.group(1);
            }

        }catch(IllegalStateException e){
            System.out.println("No matches Found!");
            e.printStackTrace();

        }catch(Exception e){
            e.printStackTrace();
        }

        return p;
    }

    public static void countComments(String fileName) throws IOException {
        Pattern COUNT_COMMENTS_PATTERN = Pattern.compile("//.");
        Pattern COUNT_MULTI_COMMENTS_PATTERN = Pattern.compile("/[*](.|[\r\n])*?[*]/");

        Matcher mcomments = COUNT_COMMENTS_PATTERN.matcher(fromFile(fileName));
        Matcher mMulti_comments = COUNT_MULTI_COMMENTS_PATTERN.matcher(fromFile(fileName));
        long comments = mcomments.results().count();
        long multicomments = mMulti_comments.results().count();

        System.out.println("Number of Comments: " + (comments + multicomments) + "\nNumber of one-line-comments: "
                + comments + "\nNumber of Multiline comments: " + multicomments);
    }

    public static void countLines(String fileName) throws IOException {
        Pattern COUNT_LINES_PATTERN = Pattern.compile("\n");

        Matcher mlines = COUNT_LINES_PATTERN.matcher(fromFile(fileName));

        long lines = mlines.results().count();
        System.out.println("Number of lines: " + (lines + 1)); // first line doesn't get counted since its only counting
                                                               // the occurences of "\n"
    }

    public static void countIf(String fileName) throws IOException {
        Pattern COUNT_IF_STATEMENTS_PATTERN = Pattern.compile("if[(].*?[)]");

        Matcher mif = COUNT_IF_STATEMENTS_PATTERN.matcher(fromFile(fileName));

        long ifStatements = mif.results().count();
        System.out.println("Number of if-statements: " + ifStatements);
    }

    public static void countForLoops(String fileName) throws IOException {
        Pattern COUNT_FOR_LOOPS_PATTERN = Pattern.compile(
                "for" +
                        "\\s*?" + // checks for any number of whitespaces between for and the brackets
                        "\\(.*?;.*?;.*?\\)" + // checks for the format of a for-loop (.. ; .. ; .. )
                        "\\s*?" + // checks for any number of whitespaces after for-loop-initiation
                        "\\{?" // checks for opening bracket after the for-loop-initiation (0 or 1, since it
                               // can be left out if the executing statement is a single-line-statement)
        );
        Pattern COUNT_FOR_EACH_PATTERN = Pattern.compile(
                "for" +
                        "\\s*?" +
                        "\\(.*?:.*?\\)" + // checks for foreach-loop format (.. : ..)
                        "\\s*?" + // checks for any number of whitespaces after for-loop-initiation
                        "\\{?" // checks for opening bracket after the for-loop-initiation (0 or 1, since it
                               // can be left out if the executing statement is a single-line-statement)
        );

        Pattern COUNT_FOR_PATTERN_PYTHON = Pattern.compile(
            "for\\s*(.*?)in\\s*(.*?):"
        );
        
        Matcher matchedForLoops = COUNT_FOR_LOOPS_PATTERN.matcher(fromFile(fileName));
        Matcher matchedForEachLoops = COUNT_FOR_EACH_PATTERN.matcher(fromFile(fileName));
        System.out.println(COUNT_FOR_PATTERN_PYTHON.matcher(fromFile(fileName)).results().count());

        System.out.println("Number of For-Loops: " + matchedForLoops.results().count());
        System.out.println("Number of For-Each-Loops: " + matchedForEachLoops.results().count());
    }

    public static long countWhileLoopsJava(String fileName) throws IOException {
        Pattern COUNT_WHILE_LOOPS_PATTERN = Pattern.compile("while" +
                "\\s*?" + // checks for any number of Whitespace after the while statement
                "\\(.*?\\)" + // checks if there is any or no content in the brackets after the while
                              // statement
                "\\s*?" + // checks for any number of Whitespace after the brackets
                "\\{?" + // checks for 0 or 1 brackets
                "(?!;)" // checks that ";" is not included to exclude do-while loops
        );

        Matcher matchedWhileLoops = COUNT_WHILE_LOOPS_PATTERN.matcher(fromFile(fileName));

        return matchedWhileLoops.results().count();
    }

    public static void countDoWhileLoopsJava(String fileName) throws IOException {
        Pattern COUNT_DO_WHILE_LOOPS_PATTERN = Pattern.compile("do" +
                "\\s*?" +
                "\\{?" +
                ".*?" +
                "}?"
        );

        Matcher matchedDoWhileLoops = COUNT_DO_WHILE_LOOPS_PATTERN.matcher(fromFile(fileName));
        System.out.println("Number of Do-While-Loops java: " + matchedDoWhileLoops.results().count());
    }

    public static long countWhileLoopsPython(String fileName) throws IOException {
        Pattern COUNT_WHILE_LOOPS_PATTERN = Pattern.compile("while" +
                "\\s*?" +
                ".*?" +
                ":"
        );

        Matcher matchedWhileLoops = COUNT_WHILE_LOOPS_PATTERN.matcher(fromFile(fileName));
        return matchedWhileLoops.results().count();
    }

    public static void run() throws IOException {
        String javaCode = "sampleCode.java";
        String pythonCode = "sampleCode.py";

        countLines(javaCode);
        countComments(javaCode);
        countIf(javaCode);
        countForLoops(javaCode);
        countWhileLoopsJava(javaCode);
        countDoWhileLoopsJava(javaCode);
        countWhileLoopsPython(pythonCode);

        System.out.println(patternFinder("hallo"));

    }
}