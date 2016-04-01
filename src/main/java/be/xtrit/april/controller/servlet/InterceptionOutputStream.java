package be.xtrit.april.controller.servlet;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class InterceptionOutputStream extends ServletOutputStream
{
    private ServletOutputStream servletOutputStream;

    boolean fillBuffer = true;
    boolean eat = false;

    private ByteArrayOutputStream byteOutputStream = new ByteArrayOutputStream(50);
    int todo = 50;

    public InterceptionOutputStream(ServletOutputStream servletOutputStream) {
        this.servletOutputStream = servletOutputStream;
    }

    @Override
    public boolean isReady() {
        return servletOutputStream.isReady();
    }

    @Override
    public void setWriteListener(WriteListener listener) {
        servletOutputStream.setWriteListener(listener);
    }

    @Override
    public void write(int b) throws IOException {
        if(eat) {
            return;
        }

        if(fillBuffer) {
            addToBuffer(b);
            return;
        }

        servletOutputStream.write(b);
    }

    private void addToBuffer(int b) throws IOException {
        byteOutputStream.write(b);
        checkBuffer();
    }

    private void checkBuffer() throws IOException {
        todo--;
        if(todo == 0) {
            checkContentBuffer();
        }
    }

    private void checkContentBuffer() throws IOException {
        byte[] bytes = byteOutputStream.toByteArray();
        String content = new String(bytes);

        if(content.contains("<!DOCTYPE html><html>")) {
            eat = true;
            System.out.println("Intercepting!");
        } else {
            for (byte aByte : bytes) {
                servletOutputStream.write(aByte);
            }
        }

        fillBuffer = false;
    }

    public void flushBuffer() throws IOException {
        if(!fillBuffer) {
            return;
        }

        byte[] bytes = byteOutputStream.toByteArray();
        for (byte aByte : bytes) {
            servletOutputStream.write(aByte);
        }
        fillBuffer = false;
    }

    public boolean wasWritten() {
        return !fillBuffer && !eat;
    }
}
