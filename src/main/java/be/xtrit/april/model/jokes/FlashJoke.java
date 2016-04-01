package be.xtrit.april.model.jokes;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class FlashJoke extends AbstractVelocityJoke {
    @Override
    protected void validateRequired() {
        required("color-1");
    }

    @Override
    protected void setOptionals() {
        optional("color-2", value("color-1"));
        optional("text", "");
        optional("text-color-1", value("color-2"));
        optional("text-color-2", value("color-1"));
        optional("text-size", "100");
        optional("flash-timeout", "200");
        optional("timeout", "3000");
    }

    @Override
    protected String getTemplate() {
        return "flash.html";
    }
}
