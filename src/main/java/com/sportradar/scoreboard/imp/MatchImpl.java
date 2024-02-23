package com.sportradar.scoreboard.imp;

import com.sportradar.scoreboard.Interfaces.Match;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.exception.MatchBadRequestException;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MatchImpl implements Match {

    /**
     * Update match scores.
     * This method checks the validation of the match and ensures that the scores are not null, greater than 0, and higher than the old scores.
     *
     * @param match     The match to be updated.
     * @param homeScore The new home score.
     * @param awayScore The new away score.
     * @return The updated match with the new scores.
     */
    @Override
    public FootballMatch updateMatch(FootballMatch match, int homeScore, int awayScore) {
        log.debug("update the match:{} with homeScore:{} and  awayScore:{}", match, homeScore, awayScore);
        if (!isValidMatch(match, homeScore, awayScore)) {
            log.error("the input is not valid to update the Match:{}, homeScore:{} , awayScore:{}", match, homeScore, awayScore);
            throw new MatchBadRequestException("the input is not valid to update the Match");
        }
        match.setHomeScore(homeScore);
        match.setAwayScore(awayScore);
        return match;
    }

    /**
     * Start a match. When the match begins, the scores are initialized to 0. This method checks the validation of team names:
     * They should not be the same, null, or blank.
     * They must consist only of alphabetic characters.
     *
     * @param homeTeam Home team.
     * @param awayTeam Away team.
     * @return The started match with the specified teams and an initial score of 0-0.
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
     * Check the validation of a match that will be updated with new scores. The scores should not be less than 0 or less than the current scores.
     *
     * @param match     The ongoing match that has not finished yet; should not be null.
     * @param homeScore The new home score to be updated; should be greater than 0.
     * @param awayScore The new away score to be updated; should be greater than 0.
     * @return True if the inputs are valid; otherwise, false.
     */

    private boolean isValidMatch(FootballMatch match, int homeScore, int awayScore) {
        return match != null
                && homeScore >= 0
                && awayScore >= 0
                && match.getHomeScore() <= homeScore
                && match.getAwayScore() <= awayScore;
    }

    /**
     * Check that the input parameters (team names) for the method are valid:
     * They cannot be the same.
     * They cannot be null or empty.
     * They must consist only of alphabetic characters.
     *
     * @param homeTeam home team name
     * @param awayTeam away team name
     * @return result of validation
     */
    private boolean isValidTeamName(String homeTeam, String awayTeam) {
        return !StringUtils.isBlank(homeTeam) && !StringUtils.isBlank(awayTeam)
                && !homeTeam.equals(awayTeam)
                && isTeamNameAlphabetic(homeTeam) && isTeamNameAlphabetic(awayTeam);
    }

    /**
     * Check String is  only of alphabetic characters
     *
     * @param teamName String name of team
     * @return result of that Stirng is alphabetic characters or not
     */
    private boolean isTeamNameAlphabetic(String teamName) {
        return teamName.matches("^[a-zA-Z]+$");
    }

}
