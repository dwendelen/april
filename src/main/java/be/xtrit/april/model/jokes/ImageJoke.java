package be.xtrit.april.model.jokes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@Scope("prototype")
public class ImageJoke extends AbstractVelocityJoke {
    @Override
    protected void setOptionals() {
        optional("color", "black");
        optional("timeout", "1000");
    }

    @Override
    protected String getTemplate() {
        return "img.html";
    }

    @Override
    protected void validateRequired() {
        required("image");
    }
}
