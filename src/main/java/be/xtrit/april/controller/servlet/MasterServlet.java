package be.xtrit.april.controller.servlet;

import be.xtrit.april.model.JokeExecutor;
import be.xtrit.april.model.jokes.ErrorJoke;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MasterServlet extends HttpServlet {
    @Autowired
    private ProxyServlet proxyServlet;

    @Autowired
    private ErrorJoke errorJoke;
    @Autowired
    private JokeExecutor jokeExecutor;

    @Override
    public void init(ServletConfig config) throws ServletException {
        proxyServlet.init(config);
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleNormal(req, resp);
    }

    @Override
    protected void doHead(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleNormal(req, resp);
    }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleNormal(req, resp);
    }
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleNormal(req, resp);
    }

    private void handleNormal(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {

        try {
            if(     req.getRequestURI().startsWith("/view") ||
                    req.getRequestURI().startsWith("/job") ||
                    req.getRequestURI().equals("/")         ||
                    req.getRequestURI().startsWith("/manage")) {

                System.out.println("Intercepting");
                jokeExecutor.handle(req, resp);
            } else {
                proxyServlet.service(req, resp);
            }
        } catch (Exception e) {
            //e.printStackTrace();
            errorJoke.handle(req, resp);
        }
    }

    @Override
    public void destroy() {
        proxyServlet.destroy();
        super.destroy();
    }
}
