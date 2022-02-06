package cz.kostka.pochod.domain;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by dkostka on 2/6/2022.
 */
@Entity
@Table
public class Stamp {

    @Id
    @GeneratedValue
    private Long id;

    @Column
    private LocalDateTime timestamp;

    @ManyToOne
    @JoinColumn(name = "stage_id")
    private Stage stage;

    @ManyToOne
    @JoinColumn(name = "player_id")
    private Player player;

    public Stamp(final Long id, final LocalDateTime timestamp, final Stage stage, final Player player) {
        this.id = id;
        this.timestamp = timestamp;
        this.stage = stage;
        this.player = player;
    }

    public Stamp() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(final LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(final Stage stage) {
        this.stage = stage;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(final Player player) {
        this.player = player;
    }


}
