package cz.kostka.pochod.dto;


import org.springframework.data.geo.Point;


/**
 * Created by dkostka on 6/6/2022.
 */
public record StageCreationDTO(String name, Integer number, Point location, String pin, String info) {
    @Override
    public String name() {
        return name;
    }

    @Override
    public Integer number() {
        return number;
    }

    @Override
    public Point location() {
        return location;
    }

    @Override
    public String pin() {
        return pin;
    }

    public String info() {
        return info;
    }
}
