package be.xtrit.april;

import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.*;
import javax.servlet.ServletConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class MasterServlet extends HttpServlet {
    @Autowired
    private ProxyServlet proxyServlet;
    @Autowired
    private DispatcherServlet dispatcherServlet;

    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void init(ServletConfig config) throws ServletException {
        proxyServlet.init(config);
        dispatcherServlet.init(config);
        super.init(config);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException
    {
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

    private void handleNormal(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            proxyServlet.service(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            Resource resource = resourceLoader.getResource("classpath:/error.html");
            resp.setContentLength((int) resource.contentLength());
            IOUtils.copy(resource.getInputStream(), resp.getOutputStream());
        }
    }

    @Override
    public void destroy() {
        proxyServlet.destroy();
        dispatcherServlet.destroy();
        super.destroy();
    }
}
