package com.sportradar.scoreboard.service;

import com.sportradar.scoreboard.Interfaces.Match;
import com.sportradar.scoreboard.Interfaces.ScoreBoard;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.exception.MatchBadRequestException;
import io.micrometer.common.util.StringUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
@RequiredArgsConstructor
public class ScoreBoardImpl implements ScoreBoard {
    private final Set<FootballMatch> footballMatchList = new HashSet<>();
    private final Match match;

    /**
     * Update the Match and scoreBoard
     * @param footballMatch the match that should be updated
     * @param homeScore new home score
     * @param awayScore new away score
     * @return return match of updated
     */
    @Override
    public FootballMatch updateMatch(FootballMatch footballMatch, int homeScore, int awayScore) {
        log.debug("the match:{} is update scores, new homeScore:{} , new awayScore:{}" , footballMatch,homeScore,awayScore);
        return match.updateMatch(footballMatch, homeScore, awayScore);
    }

    /**
     * the match should remove from scoreBoard after finish
     * @param footballMatch match which should be removed
     */
    @Override
    public void finishMatch(FootballMatch footballMatch) {
        log.debug("the match is finish , match is:{}" , footballMatch);
        footballMatchList.remove(footballMatch);
    }

    /**
     *  the team name should not be null or exist in score board
     * @param homeTeam  home team in match
     * @param awayTeam  away team in match
     * @return  boolean true if  the team is not null or empty and does not exist in score board
     */
    private boolean isExistInScoreBoardAndNotBlankNames(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            return false;
        }
        return footballMatchList.stream()
                .noneMatch(
                        match -> match.getHomeTeam().equalsIgnoreCase(homeTeam) ||
                                match.getAwayTeam().equalsIgnoreCase(homeTeam) ||
                                match.getHomeTeam().equalsIgnoreCase(awayTeam) ||
                                match.getAwayTeam().equalsIgnoreCase(awayTeam)
                );
    }

    /**
     * start match and insert in score board , checked the match is not exist in score board and then start it
     * @param homeTeam home team in match
     * @param awayTeam away team in match
     * @return add the match in board and return the started match
     */
    @Override
    public FootballMatch startMatch(String homeTeam, String awayTeam) {
        log.debug("the Match is Start between homeTeam:{} , awayTeam:{}", homeTeam, awayTeam);
        if (!isExistInScoreBoardAndNotBlankNames(homeTeam, awayTeam)) {
            log.error("the home:{} or away:{} Team is exists on scoreBoard", homeTeam, awayTeam);
            throw new MatchBadRequestException("the teams are exists in scoreBoard");
        }
        FootballMatch footballMatch = match.startMatch(homeTeam, awayTeam);
        if (footballMatch != null) {
            log.debug("the match between homeTeam:{} , awayTeam:{} is add to scoreBoard", homeTeam, awayTeam);
            footballMatchList.add(footballMatch);
        }
        return footballMatch;
    }

    /**
     * sort the score board base on total  score and most recently start
     * @return sorted list of matches
     */
    @Override
    public List<FootballMatch> scoreBoardSummery() {
     return footballMatchList.stream().sorted(FootballMatch::compareTo).toList();
     }
}
