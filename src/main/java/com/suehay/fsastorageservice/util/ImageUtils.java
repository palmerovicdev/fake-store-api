package com.suehay.fsastorageservice.util;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class ImageUtils {

    private static final int BUFFER_SIZE = 4 * 1024;

    public byte[] compressImage(byte[] data) {
        var deflater = new Deflater();
        deflater.setLevel(Deflater.BEST_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        var output = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!deflater.finished()) {
            int count = deflater.deflate(buffer);
            output.write(buffer, 0, count);
        }

        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output.toByteArray();
    }

    public byte[] decompressImage(byte[] data) {
        var inflater = new Inflater();
        inflater.setInput(data);

        var output = new ByteArrayOutputStream(data.length);
        byte[] buffer = new byte[BUFFER_SIZE];
        while (!inflater.finished()) {
            try {
                int count = inflater.inflate(buffer);
                output.write(buffer, 0, count);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        try {
            output.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return output.toByteArray();
    }
}