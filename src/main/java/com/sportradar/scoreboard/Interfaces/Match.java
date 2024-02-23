package com.sportradar.scoreboard.Interfaces;

import com.sportradar.scoreboard.domain.FootballMatch;

public interface Match {

    FootballMatch updateMatch(FootballMatch match, int homeScore , int awayScore);
    FootballMatch startMatch(String homeTeam, String awayTeam);

}
