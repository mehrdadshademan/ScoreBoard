package com.sportradar.scoreboard.interfaces;

import com.sportradar.scoreboard.domain.FootballMatch;

/**
 * Match interface with Update and Start match methods
 */
public interface Match {

    FootballMatch updateMatch(FootballMatch match, int homeScore , int awayScore);
    FootballMatch startMatch(String homeTeam, String awayTeam);

}
