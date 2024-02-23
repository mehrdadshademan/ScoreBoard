package com.sportradar.scoreboard.service;

import com.sportradar.scoreboard.Interfaces.Match;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.exception.MatchBadRequestException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchImpl implements Match {

    /**
     * update match scores, this method check validation of match and score that should be not null
     * and biger than 0 and old score
     * @param match the match which should be updated
     * @param homeScore home score
     * @param awayScore away score
     * @return updated match with new scores
     */
    @Override
    public FootballMatch updateMatch(FootballMatch match, int homeScore, int awayScore) {
        if (!isValidMatch(match, homeScore, awayScore)) {
            log.error("the input is not valid to update the Match:{}, homeScore:{} , awayScore:{}", match, homeScore, awayScore);
            throw new MatchBadRequestException("the input is not valid to update the Match");
        }
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        return match;
    }

    /**
     * start match , when the match is started , the score are 0 , this method check validation of team names
     * that should not be same, null , blank and they must be Alphabet
     * @param homeTeam home team
     * @param awayTeam away team
     * @return the match that started with teams and score 0-0
     */
    @Override
    public FootballMatch startMatch(String homeTeam, String awayTeam) {
        if (!isValidTeamName(homeTeam, awayTeam)) {
            log.error("the team names are not valid to start the Match , homeTeam:{} , awayTeam:{}", homeTeam, awayTeam);
            throw new MatchBadRequestException("the team names are not valid to start the Match");
        }
        return new FootballMatch(homeTeam, awayTeam);
    }

    /**
     * check validation of match which will be updated the score, the score should not less than 0
     * or less than before update
     *
     * @param match     the match which not finish yet , should not be null
     * @param homeScore home score will be updated and should be bigger than 0
     * @param awayScore away score will be updated and should be bigger than 0
     * @return the inputs are valid or not
     */
    private boolean isValidMatch(FootballMatch match, int homeScore, int awayScore) {
        return match != null
                && homeScore >= 0
                && awayScore >= 0
                && match.getHomeScore() <= homeScore
                && match.getAwayScore() <= awayScore;
    }

    /**
     * check the input of method ( team names) are valid ,
     * they can not be same , they can not be null or empty , they must be just alphabet
     *
     * @param homeTeam home team name
     * @param awayTeam away team name
     * @return result of validation
     */
    private boolean isValidTeamName(String homeTeam, String awayTeam) {
        return !StringUtils.isBlank(homeTeam) && !StringUtils.isBlank(awayTeam)
                && !homeTeam.equals(awayTeam)
                && homeTeam.matches("^[a-zA-Z]+$") && awayTeam.matches("^[a-zA-Z]+$");
    }

}
