package com.sportradar.scoreboard.interfaces;

import com.sportradar.scoreboard.domain.FootballMatch;

import java.util.List;

/**
 * ScoreBoard Interface with Start, Update, Finish and Summery of the match
 */
public interface ScoreBoard {
    FootballMatch updateMatch(FootballMatch match, int homeScore , int awayScore);

    void finishMatch(FootballMatch match);

     FootballMatch startMatch(String homeTeam , String awayTeam );

    List<FootballMatch> scoreBoardSummery();

}
