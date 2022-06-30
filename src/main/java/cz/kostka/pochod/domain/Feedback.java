package cz.kostka.pochod.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by dkostka on 6/30/2022.
 */
@Entity
@Table(name = "pop_feedback")
public class Feedback {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 4000)
    private String text;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Feedback() {
    }

    public Feedback(final Long id, final String text, final Player player) {
        this.id = id;
        this.text = text;
        this.player = player;
    }

    public Feedback(final String text, final Player player) {
        this.text = text;
        this.player = player;
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

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }
}
