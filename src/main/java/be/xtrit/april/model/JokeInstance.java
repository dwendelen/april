package be.xtrit.april.model;

import be.xtrit.april.model.jokes.Joke;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.UUID;

public class JokeInstance {
    private final UUID uuid = UUID.randomUUID();
    private int duration;
    private Joke joke;

    public JokeInstance(Joke joke) {
        this(joke, -1);
    }

    public JokeInstance(Joke joke, int duration) {
        this.joke = joke;
        this.duration = duration;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getDuration() {
        return duration;
    }

    public Joke getJoke() {
        return joke;
    }
}
