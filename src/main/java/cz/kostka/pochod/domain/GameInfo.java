package cz.kostka.pochod.domain;

import javax.persistence.*;
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

    @Column
    private String facebookUrl;

    @Column
    private int year;

    @Column
    private int minimumStamps;

    public GameInfo(final Long id, final LocalDateTime startGame, final LocalDateTime endGame, final String partners, final String mapUrl, final String facebookUrl, final int year) {
        this.id = id;
        this.startGame = startGame;
        this.endGame = endGame;
        this.partners = partners;
        this.mapUrl = mapUrl;
        this.facebookUrl = facebookUrl;
        this.year = year;
    }

    public GameInfo(final Long id, final LocalDateTime startGame, final LocalDateTime endGame, final String partners, final String mapUrl, final String facebookUrl, final int year, final int minimumStamps) {
        this.id = id;
        this.startGame = startGame;
        this.endGame = endGame;
        this.partners = partners;
        this.mapUrl = mapUrl;
        this.facebookUrl = facebookUrl;
        this.year = year;
        this.minimumStamps = minimumStamps;
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

    public String getFacebookUrl() {
        return facebookUrl;
    }

    public void setFacebookUrl(final String facebookUrl) {
        this.facebookUrl = facebookUrl;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMinimumStamps() {
        return minimumStamps;
    }

    public void setMinimumStamps(final int minimumStamps) {
        this.minimumStamps = minimumStamps;
    }
}
