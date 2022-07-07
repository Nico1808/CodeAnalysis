import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Analyzer {

    Analyzer(String fileName) {
        this.fileContent = getCharSequence(fileName);
        this.patterns = getCharSequence("Pattern.txt");
    }

    private CharSequence fileContent;
    private CharSequence patterns;

    public void setFileContent(String fileName) {
        this.fileContent = getCharSequence(fileName);
    }

    public CharSequence getCharSequence(String fileName) {
        try (FileInputStream fis = new FileInputStream(fileName)) {

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
        return null;
    }

    public long resultMatcher(String userInput) {
        Pattern USER_INPUT = Pattern.compile(userInput + "\\s+(.+)");
        Matcher mUi = USER_INPUT.matcher(patterns);
        if (!mUi.find()) {
            return 0;
        }
        Pattern FINAL_PATTERN = Pattern
                .compile(mUi.group(1));
        System.out.println(FINAL_PATTERN.toString());
        Matcher mui = FINAL_PATTERN.matcher(fileContent);
        return mui.results().count();
    }

}
