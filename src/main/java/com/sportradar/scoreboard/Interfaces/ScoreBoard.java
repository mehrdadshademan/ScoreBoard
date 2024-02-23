package com.sportradar.scoreboard.Interfaces;

import com.sportradar.scoreboard.domain.FootballMatch;

import java.util.List;

public interface ScoreBoard {
    FootballMatch updateMatch(FootballMatch match, int homeScore , int awayScore);

    void finishMatch(FootballMatch match);

     FootballMatch startMatch(String homeTeam , String awayTeam );

    List<FootballMatch> scoreBoardSummery();

}
