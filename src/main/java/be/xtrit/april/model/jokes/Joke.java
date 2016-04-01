package be.xtrit.april.model.jokes;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.time.Period;
import java.util.Map;
import java.util.UUID;

public interface Joke {
    void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException;
    void setOptions(Map<String, String> options);
    boolean validateOptions();
}
