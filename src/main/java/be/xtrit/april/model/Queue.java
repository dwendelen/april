package be.xtrit.april.model;

import be.xtrit.april.model.jokes.Joke;
import be.xtrit.april.model.jokes.NullJoke;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Component
public class Queue {

    @Autowired
    private NullJoke nullJoke;

    private LinkedList<JokeInstance> jokes = new LinkedList<>();

    public JokeInstance getNextJoke() {
        if(jokes.isEmpty()) {
            return new JokeInstance(nullJoke);
        }
        return jokes.getFirst();
    }

    public void removeTop() {
        if(jokes.isEmpty()) {
            return;
        }
        jokes.removeFirst();
    }

    public void clear() {
        jokes.clear();
    }

    public List<JokeInstance> getQueue() {
        return new ArrayList<>(jokes);
    }

    public void add(JokeInstance joke) {
        jokes.add(joke);
    }

    public void remove(UUID uuid) {
        for (JokeInstance joke : jokes) {
            if(joke.getUuid().equals(uuid)) {
                jokes.remove(joke);
            }
        }
    }
}
