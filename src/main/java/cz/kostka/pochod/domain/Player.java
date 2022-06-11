package cz.kostka.pochod.domain;

import javax.persistence.*;
import java.util.Collections;
import java.util.Set;

/**
 * Created by dkostka on 2/6/2022.
 */
@Entity
@SequenceGenerator(name = "seq", allocationSize = 1000)
@Table(name = "pop_player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String phoneNumber;

    @Column
    private int pin;

    @Column
    private String email;

    @OneToMany(mappedBy = "player")
    private Set<Stamp> stamps;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Player() {
    }

    public Player(final String nickname, final String email, final String phone, final int pin, final User user) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phone;
        this.pin = pin;
        this.user = user;
    }

    public Player(final Long id, final String nickname, final String phoneNumber, final Set<Stamp> stamps) {
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

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(final String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public Set<Stamp> getStamps() {
        return stamps != null ? stamps : Collections.emptySet();
    }

    public void setStamps(final Set<Stamp> stamps) {
        this.stamps = stamps;
    }

    public int getPin() {
        return pin;
    }

    public void setPin(final int pin) {
        this.pin = pin;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(final String email) {
        this.email = email;
    }
}
