package java.io;

import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import sun.nio.cs.StreamEncoder;


/**
 * @see BufferedWriter
 * @see OutputStream
 * @see java.nio.charset.Charset
 *
 * @author      Mark Reinhold
 * @since       JDK1.1
 *
 * @author wangheng
 * @date 2019/08/14
 */

public class OutputStreamWriter extends Writer {

    private final StreamEncoder se;

    public OutputStreamWriter(OutputStream out, String charsetName)
        throws UnsupportedEncodingException
    {
        super(out);
        if (charsetName == null)
            throw new NullPointerException("charsetName");
        se = StreamEncoder.forOutputStreamWriter(out, this, charsetName);
    }

    public OutputStreamWriter(OutputStream out) {
        super(out);
        try {
            se = StreamEncoder.forOutputStreamWriter(out, this, (String)null);
        } catch (UnsupportedEncodingException e) {
            throw new Error(e);
        }
    }

    public OutputStreamWriter(OutputStream out, Charset cs) {
        super(out);
        if (cs == null)
            throw new NullPointerException("charset");
        se = StreamEncoder.forOutputStreamWriter(out, this, cs);
    }

    public OutputStreamWriter(OutputStream out, CharsetEncoder enc) {
        super(out);
        if (enc == null)
            throw new NullPointerException("charset encoder");
        se = StreamEncoder.forOutputStreamWriter(out, this, enc);
    }

    public String getEncoding() {
        return se.getEncoding();
    }

    void flushBuffer() throws IOException {
        se.flushBuffer();
    }

    public void write(int c) throws IOException {
        se.write(c);
    }

    public void write(char cbuf[], int off, int len) throws IOException {
        se.write(cbuf, off, len);
    }

    public void write(String str, int off, int len) throws IOException {
        se.write(str, off, len);
    }

    /**
     * Flushes the stream.
     *
     * @exception  IOException  If an I/O error occurs
     */
    public void flush() throws IOException {
        se.flush();
    }

    public void close() throws IOException {
        se.close();
    }
}
