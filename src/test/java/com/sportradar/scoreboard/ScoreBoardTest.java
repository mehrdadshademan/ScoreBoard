package com.sportradar.scoreboard;


import com.sportradar.scoreboard.Interfaces.Match;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.exception.MatchBadRequestException;
import com.sportradar.scoreboard.service.ScoreBoardImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.mockito.BDDMockito.given;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class ScoreBoardTest {

    @Mock
    private ScoreBoardImpl scoreBoard;
    @Mock
    private Match match;


    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.scoreBoard = new ScoreBoardImpl(match);
    }


    @ParameterizedTest
    @CsvSource({"Mexico,Canada", "Mexico,Brazil", "mexico,canada", "France,canada", "' ',"})
    void should_ThrowsException_StartMatch_When_WrongInputForDefineMatch(String homeTeam, String awayTeam) {
        FootballMatch expectFootballMatch = new FootballMatch(homeTeam, awayTeam);
        FootballMatch firstMatchInScoreBoard = new FootballMatch("Mexico", "Canada");
        given(match.startMatch(homeTeam, awayTeam)).willReturn(expectFootballMatch);
        given(match.startMatch("Mexico", "Canada")).willReturn(firstMatchInScoreBoard);
        FootballMatch scoreBoardFootballMatch = scoreBoard.startMatch("Mexico", "Canada");
        Assertions.assertThrows(MatchBadRequestException.class, () -> scoreBoard.startMatch(homeTeam, awayTeam));
        List<FootballMatch> footballMatches = scoreBoard.scoreBoardSummery();
        Assertions.assertEquals(1, footballMatches.size());
        Assertions.assertTrue(footballMatches.contains(scoreBoardFootballMatch));
    }

    @ParameterizedTest
    @CsvSource({"Mexico,Canada", "Spain,Brazil", "Germany,France", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaassssssssssssssshdakjshdjaslkdjaskjdkajsdlkasldlasdaksdsalkd"})
    void should_Success_StartMatch_When_InputIsCorrectStartMatch(String homeTeam, String awayTeam) {
        FootballMatch expectFootballMatch = new FootballMatch(homeTeam, awayTeam);
        given(match.startMatch(homeTeam, awayTeam)).willReturn(expectFootballMatch);
        FootballMatch footballMatchResult = scoreBoard.startMatch(homeTeam, awayTeam);
        Assertions.assertNotNull(footballMatchResult);
        Assertions.assertEquals(expectFootballMatch.getHomeScore(), footballMatchResult.getHomeScore());
        Assertions.assertEquals(expectFootballMatch.getAwayScore(), footballMatchResult.getAwayScore());
        Assertions.assertEquals(expectFootballMatch.getAwayTeam(), footballMatchResult.getAwayTeam());
        Assertions.assertEquals(expectFootballMatch.getHomeTeam(), footballMatchResult.getHomeTeam());
    }


    @ParameterizedTest
    @CsvSource({"Mexico,Canada", "Spain,Brazil", "Germany,France", "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa, aaassssssssssssssshdakjshdjaslkdjaskjdkajsdlkasldlasdaksdsalkd"})
    void should_Success_FinishMatch_When_InputIsCorrect(String homeTeam, String awayTeam) {
        FootballMatch expectFootballMatch = new FootballMatch(homeTeam, awayTeam);
        given(match.startMatch(homeTeam, awayTeam)).willReturn(expectFootballMatch);
        FootballMatch footballMatchResult = scoreBoard.startMatch(homeTeam, awayTeam);
        List<FootballMatch> scoreBoardSummery = scoreBoard.scoreBoardSummery();
        int scoreBoardSummerySize = scoreBoardSummery.size();
        scoreBoard.finishMatch(footballMatchResult);
        List<FootballMatch> scoreBoardSummeryAfterFinish = scoreBoard.scoreBoardSummery();
        Assertions.assertNotEquals(scoreBoardSummerySize, scoreBoardSummeryAfterFinish.size());
    }


    @ParameterizedTest
    @CsvSource({"Mexico,0,Canada,2", "Spain,100,Brazil,1"})
    void should_Success_UpdateMatch_When_UpdateMatchScores(String homeTeam, int homeScore, String awayTeam, int awayScore) {
        FootballMatch footballMatch = new FootballMatch(homeTeam, awayTeam);

        given(match.startMatch(homeTeam, awayTeam)).willReturn(footballMatch);
        scoreBoard.startMatch(homeTeam, awayTeam);
        FootballMatch summeryBoard = scoreBoard.scoreBoardSummery().stream().findFirst().get();
        int summeryBoardHomeScore = summeryBoard.getHomeScore();
        int summeryBoardAwayScore = summeryBoard.getAwayScore();
        footballMatch.setAwayScore(awayScore);
        footballMatch.setHomeScore(homeScore);
        given(match.updateMatch(footballMatch, homeScore, awayScore)).willReturn(footballMatch);
        FootballMatch updatedFootballMatch = scoreBoard.updateMatch(footballMatch, homeScore, awayScore);
        FootballMatch summeryBoardAfterUpdate = scoreBoard.scoreBoardSummery().stream().findFirst().get();

        Assertions.assertEquals(updatedFootballMatch.getHomeScore(), footballMatch.getHomeScore());
        Assertions.assertEquals(updatedFootballMatch.getAwayScore(), footballMatch.getAwayScore());
        Assertions.assertNotEquals(summeryBoardAfterUpdate.getHomeScore() + summeryBoardAfterUpdate.getAwayScore(),
                summeryBoardHomeScore + summeryBoardAwayScore);
    }


    @Test
    void should_Success_ShowSummery_When_StartAndUpdateAndFinishMatches(){

        //start matches
        FootballMatch footballMatch1 = new FootballMatch("Brazil", "German");
        FootballMatch footballMatch2 = new FootballMatch("Iran", "Iraq");
        FootballMatch footballMatch3 = new FootballMatch("Austria", "Schweiz");
        int emptyScoreBoardSize = scoreBoard.scoreBoardSummery().size();
        given(match.startMatch("Brazil", "German")).willReturn(footballMatch1);
        given(match.startMatch("Iran", "Iraq")).willReturn(footballMatch2);
        given(match.startMatch("Austria", "Schweiz")).willReturn(footballMatch3);

        scoreBoard.startMatch("Brazil", "German");
        scoreBoard.startMatch("Iran", "Iraq");
        scoreBoard.startMatch("Austria", "Schweiz");
        int scoreBoardSummeryAfterMatches = scoreBoard.scoreBoardSummery().size();
        Assertions.assertNotEquals(emptyScoreBoardSize, scoreBoardSummeryAfterMatches);

        //check scoreBoard result after finish a match
        scoreBoard.finishMatch(footballMatch1);
        int scoreBoardSummeryAfterFinishMatch = scoreBoard.scoreBoardSummery().size();
        Assertions.assertEquals(scoreBoardSummeryAfterFinishMatch, scoreBoardSummeryAfterMatches-1);

        //Check scoreBoard after update a match
        footballMatch2.setHomeScore(2);
        footballMatch2.setAwayScore(2);
        given(match.updateMatch(footballMatch2, 2, 2)).willReturn(footballMatch2);
        scoreBoard.updateMatch(footballMatch2,2,2);
        int scoreBoardSummeryAfterUpdateMatch = scoreBoard.scoreBoardSummery().size();
        Assertions.assertEquals(scoreBoardSummeryAfterUpdateMatch, scoreBoardSummeryAfterFinishMatch);

    }
}
