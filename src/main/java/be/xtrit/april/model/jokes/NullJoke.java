package be.xtrit.april.model.jokes;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class NullJoke implements Joke {
    @Autowired
    private ProxyServlet proxyServlet;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        proxyServlet.service(httpServletRequest, httpServletResponse);
    }

    @Override
    public void setOptions(Map<String, String> options) {

    }

    @Override
    public boolean validateOptions() {
        return true;
    }
}
