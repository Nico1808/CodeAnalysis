import java.io.FileInputStream;
import java.io.IOException;
import java.util.concurrent.CyclicBarrier;
import java.util.regex.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.channels.FileChannel;

public class tryout{

    public static CharSequence fromFile(String filename) throws IOException {
        FileInputStream fis = new FileInputStream(filename);
        FileChannel fc = fis.getChannel();
        
        // Create a read-only CharBuffer on the file --> CharSequence is a read only sequence of chars
        // fc.Map maps the FileChannel (specified through the args 0: beginning of file to fc.size() (to the end)) into memory = Buffer
        ByteBuffer bbuf = fc.map(FileChannel.MapMode.READ_ONLY, 0, (int) fc.size());
        //Since we need a Charsequence for p.matcher(input) we define a Charset (a named mapping between chars and bytes) which is decoded from the bytebuffer
        CharBuffer cbuf = Charset.forName("UTF-8").newDecoder().decode(bbuf);
        // cbuf is now a sequence of chars which we created from the byteBuffer, which we created from the FileChannel
        return cbuf;
    }



    public static void countComments(String fileName) throws IOException{
        Pattern COUNT_COMMENTS_PATTERN = Pattern.compile("//."); 
        Pattern COUNT_MULTI_COMMENTS_PATTERN = Pattern.compile("/[*](.|[\r\n])*?[*]/");
        
        Matcher mcomments = COUNT_COMMENTS_PATTERN.matcher(fromFile(fileName));
        Matcher mMulti_comments = COUNT_MULTI_COMMENTS_PATTERN.matcher(fromFile(fileName));
        long comments = mcomments.results().count();
        long multicomments = mMulti_comments.results().count();
        
        System.out.println("Number of Comments: " + (comments + multicomments) + "\nNumber of one-line-comments: " + comments + "\nNumber of Multiline comments: " + multicomments);
    }

    public static void countLines(String fileName) throws IOException{
        Pattern COUNT_LINES_PATTERN = Pattern.compile("\n");

        Matcher mlines = COUNT_LINES_PATTERN.matcher(fromFile(fileName));
    
        long lines = mlines.results().count();
        System.out.println("Number of lines: " + (lines+1)); // first line doesn't get counted since its only counting the occurences of "\n"
    }

    public static void countIf(String fileName) throws IOException{
        Pattern COUNT_IF_STATEMENTS_PATTERN = Pattern.compile("if[(].*?[)]");

        Matcher mif = COUNT_IF_STATEMENTS_PATTERN.matcher(fromFile(fileName));
        
        long ifStatements = mif.results().count();
        System.out.println("Number of if-statements: " + ifStatements);
    }

    public static void main(String[] args) throws IOException{
        String s = "sampleCode.java";

        countLines(s);
        countComments(s);
        countIf(s);
    }
}