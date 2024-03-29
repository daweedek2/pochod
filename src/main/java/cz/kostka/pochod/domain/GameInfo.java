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
    private LocalDateTime endGame;

    @Column
    private String partners;

    @Column
    private String mapUrl;

    public GameInfo(final Long id, final LocalDateTime startGame, final LocalDateTime endGame, final String partners, final String mapUrl) {
        this.id = id;
        this.startGame = startGame;
        this.endGame = endGame;
        this.partners = partners;
        this.mapUrl = mapUrl;
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

    public LocalDateTime getEndGame() {
        return endGame;
    }

    public void setEndGame(LocalDateTime endGame) {
        this.endGame = endGame;
    }

    public String getPartners() {
        return partners;
    }

    public void setPartners(final String partners) {
        this.partners = partners;
    }

    public String getMapUrl() {
        return mapUrl;
    }

    public void setMapUrl(final String mapUrl) {
        this.mapUrl = mapUrl;
    }
}
