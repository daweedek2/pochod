package cz.kostka.pochod.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDateTime;

/**
 * Created by dkostka on 6/20/2022.
 */
@Entity
@Table
public class GameInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private LocalDateTime startGame;

    @Column
    private String partners;

    public GameInfo(final Long id, final LocalDateTime startGame, final String partners) {
        this.id = id;
        this.startGame = startGame;
        this.partners = partners;
    }

    public GameInfo(final LocalDateTime startGame, final String partners) {
        this.startGame = startGame;
        this.partners = partners;
    }

    public GameInfo() {
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
    }

    public LocalDateTime getStartGame() {
        return startGame;
    }

    public void setStartGame(final LocalDateTime timestamp) {
        this.startGame = timestamp;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(final String partners) {
        this.partners = partners;
    }
}
