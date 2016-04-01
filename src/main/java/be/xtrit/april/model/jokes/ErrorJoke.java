package be.xtrit.april.model.jokes;


import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@Component
public class ErrorJoke implements Joke {
    @Autowired
    private ResourceLoader resourceLoader;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        Resource resource = resourceLoader.getResource("classpath:/public/error.html");
        httpServletResponse.setContentLength((int) resource.contentLength());
        IOUtils.copy(resource.getInputStream(), httpServletResponse.getOutputStream());
    }

    @Override
    public void setOptions(Map<String, String> options) {

    }

    @Override
    public boolean validateOptions() {
        return true;
    }
}
