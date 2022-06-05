package cz.kostka.pochod.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by dkostka on 5/29/2022.
 */
@Entity
@Table(name = "pop_role")
public class Role {
    public static final String ORGANIZATOR = "ORGANIZATOR";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;

    @Column(name = "role")
    private String name;

    public Long getId() {
        return id;
    }

    public void setId(final Long roleId) {
        this.id = roleId;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
