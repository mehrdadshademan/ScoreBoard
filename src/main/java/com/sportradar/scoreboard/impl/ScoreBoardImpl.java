package com.sportradar.scoreboard.impl;

import com.sportradar.scoreboard.interfaces.Match;
import com.sportradar.scoreboard.interfaces.ScoreBoard;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.exception.MatchBadRequestInputException;
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
     * Check that the team names are not null and do not already exist in the scoreboard.
     * @param homeTeam Home team in the match.
     * @param awayTeam Away team in the match.
     * @return True if the team names are not null or empty and do not exist in the scoreboard; otherwise, false.

     */
    private boolean isExistInScoreBoardAndNotBlankNames(String homeTeam, String awayTeam) {
        if (StringUtils.isBlank(homeTeam) || StringUtils.isBlank(awayTeam)) {
            return false;
        }
        return footballMatchList.stream()
                .noneMatch(
                        footballMatch -> footballMatch.getHomeTeam().equalsIgnoreCase(homeTeam) ||
                                footballMatch.getAwayTeam().equalsIgnoreCase(homeTeam) ||
                                footballMatch.getHomeTeam().equalsIgnoreCase(awayTeam) ||
                                footballMatch.getAwayTeam().equalsIgnoreCase(awayTeam)
                );
    }

    /**
     * Start a match and insert it into the scoreboard. Checks whether the match already exists on the scoreboard before starting it.
     * @param homeTeam Home team in the match.
     * @param awayTeam Away team in the match.
     * @return The match added to the scoreboard and the started match.

     */
    @Override
    public FootballMatch startMatch(String homeTeam, String awayTeam) {
        log.debug("the Match is Start between homeTeam:{} , awayTeam:{}", homeTeam, awayTeam);
        if (!isExistInScoreBoardAndNotBlankNames(homeTeam, awayTeam)) {
            log.error("the home:{} or away:{} Team is exists on scoreBoard", homeTeam, awayTeam);
            throw new MatchBadRequestInputException("the teams are exists in scoreBoard");
        }
        FootballMatch footballMatch = match.startMatch(homeTeam, awayTeam);
        if (footballMatch != null) {
            log.debug("the match between homeTeam:{} , awayTeam:{} is add to scoreBoard", homeTeam, awayTeam);
            footballMatchList.add(footballMatch);
        }
        return footballMatch;
    }

    /**
     * Sort the scoreboard based on total score and most recently started matches.
     * @return A sorted list of matches.

     */
    @Override
    public List<FootballMatch> scoreBoardSummery() {
     return footballMatchList.stream().sorted(FootballMatch::compareTo).toList();
     }
}
