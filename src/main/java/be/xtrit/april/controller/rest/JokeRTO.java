package be.xtrit.april.controller.rest;

import be.xtrit.april.model.JokeInstance;

import java.util.UUID;

public class JokeRTO {
    private final int duration;
    private final UUID uuid;
    private String name;

    public JokeRTO(JokeInstance joke) {
        this.name = joke.getJoke().getClass().getSimpleName();
        this.uuid = joke.getUuid();
        this.duration = joke.getDuration();
        if(this.name.endsWith("Joke")) {
            this.name = this.name.substring(0, this.name.length() - 4);
        }
    }

    public String getName() {
        return name;
    }

    public int getDuration() {
        return duration;
    }

    public UUID getUuid() {
        return uuid;
    }
}
