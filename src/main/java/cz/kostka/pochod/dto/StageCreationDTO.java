package cz.kostka.pochod.dto;


import org.springframework.data.geo.Point;


/**
 * Created by dkostka on 6/6/2022.
 */
public record StageCreationDTO(String name, Integer number, Point location, String pin, String info, String color, Integer year, Integer distanceInMeters, Double distance) {
}
