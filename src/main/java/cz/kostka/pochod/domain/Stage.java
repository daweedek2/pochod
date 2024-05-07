package cz.kostka.pochod.domain;


import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

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

    @Column(length = 1000)
    private String info;

    @Column
    private String color;

    @Column
    private int year;

    public Stage() {
    }

    public Stage(
            final Long id,
            final String name,
            final int number,
            final Point location,
            final String pin,
            final String info,
            final String color,
            final int year) {
        this.id = id;
        this.name = name;
        this.number = number;
        this.location = location;
        this.pin = pin;
        this.info = info;
        this.color = color;
        this.year = year;
    }

    public Stage(
            final String name,
            final Integer number,
            final Point location,
            final String pin,
            final String info,
            final String color,
            final int year) {
        this.name = name;
        this.number = number;
        this.location = location;
        this.pin = pin;
        this.info = info;
        this.color = color;
        this.year = year;
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

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}