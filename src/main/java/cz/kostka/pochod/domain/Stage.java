package cz.kostka.pochod.domain;


import org.springframework.data.geo.Point;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Set;

/**
 * Created by dkostka on 2/6/2022.
 */
@Entity
@Table
public class Stage {

    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private int order;

    @Column(nullable = false)
    private Point location;

    @Column(nullable = false)
    private String pin;

    @OneToMany(mappedBy = "stage")
    private Set<Stamp> stamps;

    public Stage() {
    }

    public Stage(final Long id, final String name, final int order, final Point location, final String pin, final Set<Stamp> stamps) {
        this.id = id;
        this.name = name;
        this.order = order;
        this.location = location;
        this.pin = pin;
        this.stamps = stamps;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(final int order) {
        this.order = order;
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

    public Set<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(final Set<Stamp> stamps) {
        this.stamps = stamps;
    }
}