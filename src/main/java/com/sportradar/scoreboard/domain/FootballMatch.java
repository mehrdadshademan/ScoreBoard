package com.sportradar.scoreboard.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;


@Data
@AllArgsConstructor
public class FootballMatch implements Comparable<FootballMatch> {

    private String homeTeam;
    private String awayTeam;
    private int homeScore;
    private int awayScore;
    private LocalDateTime dateTime;

    public FootballMatch(String homeTeam, String awayTeam) {
        this.homeTeam = homeTeam;
        this.awayTeam = awayTeam;
        this.awayScore = 0;
        this.homeScore = 0;
        this.dateTime = LocalDateTime.now();
    }

    @Override
    public int compareTo(FootballMatch o) {
        int compare = Integer.compare(this.getHomeScore() + this.awayScore, o.getHomeScore() + o.getAwayScore()) * -1;
        if (compare == 0) {
            return this.getDateTime().compareTo(o.dateTime) * -1;
        }
        return compare;
    }
}
