package cz.kostka.pochod.domain;

import cz.kostka.pochod.configuration.DomainConfiguration;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * Created by dkostka on 6/30/2022.
 */
@Entity
@SequenceGenerator(name = "seq-feedback", initialValue = DomainConfiguration.INITIAL_VALUE, allocationSize = 1)
@Table(name = "pop_feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4000)
    private String text;

    @Column
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Feedback() {
    }

    public Feedback(final Long id, final String text, final LocalDateTime timestamp, final Player player) {
        this.id = id;
        this.text = text;
        this.timestamp = timestamp;
        this.player = player;
    }

    public Feedback(final String text, final Player player, final LocalDateTime timestamp) {
        this.text = text;
        this.player = player;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(final String text) {
        this.text = text;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }
}
