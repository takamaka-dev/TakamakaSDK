package io.takamaka.sdk.utils.threadSafeUtils;

import io.takamaka.sdk.globalContext.FixedParameters;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

public class TkmCompressionUtils {

    public static byte[] deflate(byte[] input) {
        byte[] out = null;
        try {
            Deflater deflater = new Deflater();
            deflater.setLevel(FixedParameters.COMPRESSION_LEVEL);
            deflater.setInput(input);
            deflater.finish();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            while (!deflater.finished()) {
                int byteCompressed = deflater.deflate(buffer);
                //System.out.println(Arrays.toString(buffer));
                baos.write(Arrays.copyOfRange(buffer, 0, byteCompressed));
            }
            out = baos.toByteArray();
            baos.close();
        } catch (IOException ex) {
            Logger.getLogger(TkmCompressionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

    public static byte[] inflate(byte[] input) {
        byte[] out = null;
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(input);
            //inflater.end();
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[4096];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                //System.out.println(Arrays.toString(buffer));
                baos.write(Arrays.copyOfRange(buffer, 0, count));
            }
            out = baos.toByteArray();
            baos.close();

        } catch (DataFormatException ex) {
            Logger.getLogger(TkmCompressionUtils.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TkmCompressionUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return out;
    }

}
