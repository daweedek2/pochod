package cz.kostka.pochod.dto;

import org.springframework.data.geo.Point;

/**
 * Created by dkostka on 2/6/2022.
 */
public record StageDetailDTO(Integer number, String name, String[] info, Point location, String color) {
}
