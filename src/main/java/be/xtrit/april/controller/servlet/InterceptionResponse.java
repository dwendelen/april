package be.xtrit.april.controller.servlet;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

public class InterceptionResponse extends HttpServletResponseWrapper {

    private InterceptionOutputStream interceptionOutputStream;

    public InterceptionResponse(HttpServletResponse response) throws IOException {
        super(response);
    }

    @Override
    public InterceptionOutputStream getOutputStream() throws IOException {
        interceptionOutputStream = new InterceptionOutputStream(super.getOutputStream());
        return interceptionOutputStream;
    }

    public InterceptionOutputStream getInterceptionOutputStream() {
        return interceptionOutputStream;
    }

    @Override
    public PrintWriter getWriter() throws IOException {
        return new PrintWriter(getOutputStream());
    }

    @Override
    public void setContentLength(int len){

    }

    @Override
    public void setContentLengthLong(long length){

    }

    public void flushBuffer() throws IOException {
        interceptionOutputStream.flushBuffer();
    }

    public boolean wasWritten() {
        return interceptionOutputStream.wasWritten();
    }
}
