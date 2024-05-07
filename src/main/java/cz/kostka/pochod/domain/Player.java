package cz.kostka.pochod.domain;

import cz.kostka.pochod.configuration.DomainConfiguration;

import javax.persistence.*;

/**
 * Created by dkostka on 2/6/2022.
 */
@Entity
@SequenceGenerator(name = "seq-player", initialValue = DomainConfiguration.INITIAL_VALUE, allocationSize = 1)
@Table(name = "pop_player")
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
    private Long id;

    @Column(nullable = false, unique = true)
    private String nickname;

    @Column
    private String phoneNumber;

    @Column
    private int pin;

    @Column
    private String email;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Player() {
    }

    public Player(final Long id, final String nickname, final String email, final String phone, final int pin) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phone;
        this.pin = pin;
    }

    public Player(final String nickname, final String email, final String phone, final int pin, final User user) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phone;
        this.pin = pin;
        this.user = user;
    }

    public Player(final Long id, final String nickname, final String phoneNumber) {
        this.id = id;
        this.nickname = nickname;
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return
                "id = " + id + '\n' +
                "jméno = " + nickname + '\n' +
                "telefon = " + phoneNumber + '\n' +
                "email = " + email;
    }
}
