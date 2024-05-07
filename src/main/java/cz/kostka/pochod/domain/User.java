package cz.kostka.pochod.domain;

import cz.kostka.pochod.configuration.DomainConfiguration;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Set;

/**
 * Created by dkostka on 5/29/2022.
 */
@Entity
@SequenceGenerator(name = "seqUser", initialValue = DomainConfiguration.INITIAL_VALUE, allocationSize = 1)
@Table(name = "pop_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "seqUser")
    @Column(name = "id")
    private Long id;

    @NotEmpty(message = "Jméno nesmí být prázdné")
    @Column(name = "username", nullable = false)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @ManyToMany(cascade = CascadeType.DETACH, fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToOne(mappedBy = "user", fetch = FetchType.EAGER)
    private Player player;

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(final Set<Role> roles) {
        this.roles = roles;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }

    @Override
    public String toString() {
        return String.format(
                "User with id %s, username %s is connected to player %s.",
                this.getId(),
                this.getUsername(),
                this.getPlayer() == null ? null : this.getPlayer().toString());
    }
}
