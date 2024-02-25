package com.sportradar.scoreboard;

import com.sportradar.scoreboard.interfaces.Match;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.exception.MatchBadRequestInputException;
import com.sportradar.scoreboard.impl.MatchImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class MatchTest {

    @Mock
    private Match match;

    @BeforeEach
    public void setup() {
        this.match = new MatchImpl();
    }

    @ParameterizedTest
    @CsvSource({"'' , ''", "'      @!!@#' ,'!!!!!!a123123a'", "'A','A'", "null , ''"})
    void should_ThrowsException_StartMatch_When_WrongInputForDefineMatch(String homeTeam, String awayTeam) {
        System.out.println("ss");
        Assertions.assertThrows(MatchBadRequestInputException.class, () -> match.startMatch(homeTeam, awayTeam));
    }

    @ParameterizedTest
    @CsvSource({"Mexico,Canada", "Spain,Brazil", "Germany,France", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaassssssssssssssshdakjshdjaslkdjaskjdkajsdlkasldlasdaksdsalkd"})
    void should_Success_StartMatch_When_InputIsCorrectDefineMatch(String homeTeam, String awayTeam) {
        FootballMatch expectFootballMatch = new FootballMatch(homeTeam, awayTeam);
        Assertions.assertNotNull(match.startMatch(homeTeam, awayTeam));
        Assertions.assertEquals(expectFootballMatch.getHomeScore(), match.startMatch(homeTeam, awayTeam).getHomeScore());
        Assertions.assertEquals(expectFootballMatch.getAwayScore(), match.startMatch(homeTeam, awayTeam).getAwayScore());
        Assertions.assertEquals(expectFootballMatch.getAwayTeam(), match.startMatch(homeTeam, awayTeam).getAwayTeam());
        Assertions.assertEquals(expectFootballMatch.getHomeTeam(), match.startMatch(homeTeam, awayTeam).getHomeTeam());
    }

    @ParameterizedTest
    @CsvSource({"Mexico,0,Canada,-2", "Spain,-1,Brazil,-6", "-1,-100000,!,9 "})
    void should_ThrowsException_UpdateMatch_When_WrongInputIsUpdateMatch(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam);
        Assertions.assertThrows(MatchBadRequestInputException.class, () -> match.updateMatch(footballMatch, homeScore, awayScore));
        FootballMatch nullFootballMatch = null;
        Assertions.assertThrows(MatchBadRequestInputException.class, () -> match.updateMatch(nullFootballMatch, homeScore, awayScore));
    }

    @ParameterizedTest
    @CsvSource({"Mexico,1,Canada,1"})
    void should_ThrowsException_UpdateMatch_When_InputScoreIsLessThanTeamScores(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam);
        footballMatch.setHomeScore(100);
        Assertions.assertThrows(MatchBadRequestInputException.class, () -> match.updateMatch(footballMatch, homeScore, awayScore));
    }

    @ParameterizedTest
    @CsvSource({"Mexico,0,Canada,2", "Spain,100,Brazil,1"})
    void should_Success_UpdateMatch_When_InputIsCorrectInUpdateMatch(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam);
        FootballMatch updateResult = match.updateMatch(footballMatch, homeScore, awayScore);
        Assertions.assertNotNull(updateResult);

        FootballMatch expectFootballMatch = new FootballMatch(homeTeam, awayTeam);
        expectFootballMatch.setHomeScore(homeScore);
        expectFootballMatch.setAwayScore(awayScore);

        Assertions.assertEquals(expectFootballMatch.getHomeTeam(), updateResult.getHomeTeam());
        Assertions.assertEquals(expectFootballMatch.getHomeScore(), updateResult.getHomeScore());

        Assertions.assertEquals(expectFootballMatch.getAwayTeam(), updateResult.getAwayTeam());
        Assertions.assertEquals(expectFootballMatch.getAwayScore(), updateResult.getAwayScore());
    }
}
