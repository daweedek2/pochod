package cz.kostka.pochod.domain;


import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by dkostka on 2/6/2022.
 */
@Entity
@Table(name = "pop_stage")
public class Stage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private int number;

    @Column
    private Point location;

    @Column(nullable = false)
    private String pin;

    @Column
    private String info;

    public Stage() {
    }

    public Stage(final Long id, final String name, final int number, final Point location, final String pin,
                 final String info) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.location = location;
        this.pin = pin;
        this.info = info;
    }

    public Stage(final String name, final Integer number, final Point location, final String pin, final String info) {
        this.name = name;
        this.number = number;
        this.location = location;
        this.pin = pin;
        this.info = info;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int order) {
        this.number = order;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(final Point location) {
        this.location = location;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(final String pin) {
        this.pin = pin;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(final String beers) {
        this.info = beers;
    }
}