package cz.kostka.pochod.domain;

import javax.persistence.*;

/**
 * Created by dkostka on 2/6/2022.
 */
@Entity
@SequenceGenerator(name = "seq", initialValue = 1000, allocationSize = 1)
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

    @Column
    private int age;

    @Column
    private String city;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

    public Player() {
    }

    public Player(final Long id, final String nickname, final String email, final String phone, final int pin, final int age, final String city) {
        this.id = id;
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phone;
        this.pin = pin;
        this.age = age;
        this.city = city;
    }

    public Player(final String nickname, final String email, final String phone, final int pin, final int age, final String city, final User user) {
        this.nickname = nickname;
        this.email = email;
        this.phoneNumber = phone;
        this.pin = pin;
        this.age = age;
        this.city = city;
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

    public int getAge() {
        return age;
    }

    public void setAge(final int age) {
        this.age = age;
    }

    public String getCity() {
        return city;
    }

    public void setCity(final String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return
                "id = " + id + '\n' +
                "jméno = " + nickname + '\n' +
                "dědina = " + city + '\n' +
                "věk = " + age;
    }
}
