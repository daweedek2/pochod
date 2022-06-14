package cz.kostka.pochod.dto;

import org.springframework.data.geo.Point;

/**
 * Created by dkostka on 6/14/2022.
 */
public record StageAdminDTO(Long id, String name, Integer number, Point location, String pin, String info) {
}
