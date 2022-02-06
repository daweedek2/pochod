package cz.kostka.pochod.domain;

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
public class Player {
    @Id
    @GeneratedValue
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column(nullable = false)
    private int phoneNumber;

    @OneToMany(mappedBy = "player")
    private Set<Stamp> stamps;

    public Player() {
    }

    public Player(final String nickname, final int phoneNumber) {
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
    }

    public Player(final Long id, final String nickname, final int phoneNumber, final Set<Stamp> stamps) {
        this.id = id;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
        this.stamps = stamps;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(final String nickname) {
        this.nickname = nickname;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Stamp> getStamps() {
        return stamps;
    }

    public void setStamps(final Set<Stamp> stamps) {
        this.stamps = stamps;
    }
}
