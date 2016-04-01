package be.xtrit.april.model;

import be.xtrit.april.model.jokes.Joke;
import be.xtrit.april.model.jokes.NullJoke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
public class JokeExecutor {
    @Autowired
    private Queue queue;

    @Autowired
    private NullJoke nullJoke;

    private LocalDateTime whenNextStarts = LocalDateTime.now();
    private Joke currentJoke;

    public void handle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(whenNextStarts.isBefore(LocalDateTime.now())) {
            JokeInstance nextJoke = queue.getNextJoke();
            queue.removeTop();
            whenNextStarts = LocalDateTime.now().plusSeconds(nextJoke.getDuration());
            currentJoke = nextJoke.getJoke();
        }

        currentJoke.handle(request, response);
    }

    public void doNormal(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        nullJoke.handle(request, response);
    }

    public void stop() {
        whenNextStarts = LocalDateTime.now();
    }
}
