package be.xtrit.april.controller;

import be.xtrit.april.model.JokeExecutor;
import be.xtrit.april.model.JokeInstance;
import be.xtrit.april.model.Queue;
import be.xtrit.april.model.jokes.Joke;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
public class Controller {
    @Autowired
    private Queue queue;
    @Autowired
    private JokeExecutor jokeExecutor;
    @Autowired
    private BeanFactory beanFactory;

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "hello";
    }

    @RequestMapping(value = "/jokes", method = RequestMethod.GET)
    public List<JokeRTO> jokes() {
        return queue.getQueue().stream()
                .map(JokeRTO::new)
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/jokes/add", method = RequestMethod.POST)
    public void add(@RequestBody AddRTO jokeRTO) throws InvalidOptionsException {
        Joke joke = beanFactory.getBean(jokeRTO.getJoke() + "Joke", Joke.class);


        joke.setOptions(jokeRTO.getOptions());
        if(!joke.validateOptions()) {
            throw new InvalidOptionsException();
        }

        JokeInstance jokeInstance = new JokeInstance(joke, jokeRTO.getDuration());
        queue.add(jokeInstance);
    }

    @RequestMapping(value = "/jokes/{uuid}/remove", method = RequestMethod.POST)
    public void remove(@PathVariable("uuid") String uuid) throws InvalidOptionsException {
        queue.remove(UUID.fromString(uuid));
    }

    @RequestMapping(value = "/jokes/clear", method = RequestMethod.POST)
    public void clear() throws InvalidOptionsException {
        queue.clear();
    }

    @RequestMapping(value = "/jokes/next", method = RequestMethod.POST)
    public void stop() throws InvalidOptionsException {
        jokeExecutor.stop();
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    private static class InvalidOptionsException extends Exception {

    }
}
