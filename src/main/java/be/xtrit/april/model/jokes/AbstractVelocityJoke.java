package be.xtrit.april.model.jokes;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

public abstract class AbstractVelocityJoke implements Joke {
    @Autowired
    private ErrorJoke errorJoke;
    @Autowired
    private VelocityEngine velocityEngine;

    private Map<String, String> options = new HashMap<>();
    private boolean allValid = true;

    @Override
    public void handle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        setOptionals();

        Template template = velocityEngine.getTemplate(getTemplate());
        VelocityContext context = new VelocityContext(options);
        StringWriter s = new StringWriter();
        template.merge(context, s);

        template.merge(context, httpServletResponse.getWriter());
        httpServletResponse.getWriter().flush();
    }

    protected abstract void setOptionals();
    protected abstract String getTemplate();

    @Override
    public void setOptions(Map<String, String> options) {
        this.options = options;
    }

    protected String value(String key) {
        return options.get(key);
    }

    @Override
    public boolean validateOptions() {
        validateRequired();
        return allValid;
    }

    protected abstract void validateRequired();

    protected void optional(String key, String value) {
        options.putIfAbsent(key, value);
    }

    protected void required(String key) {
        if(!options.containsKey(key)) {
            allValid = false;
        }
    }
}
