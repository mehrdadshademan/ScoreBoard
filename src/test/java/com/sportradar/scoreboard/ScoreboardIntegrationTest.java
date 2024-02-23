package com.sportradar.scoreboard;


import com.sportradar.scoreboard.Interfaces.Match;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.service.MatchImpl;
import com.sportradar.scoreboard.service.ScoreBoardImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;

class ScoreboardIntegrationTest {

    @Mock
    private Match match;
    @Mock
    private ScoreBoardImpl scoreBoard;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.initMocks(this);
        this.match = new MatchImpl();
        this.scoreBoard = new ScoreBoardImpl(match);
    }


    @Test
    void testScoreboardIntegration() {
        FootballMatch match1 = scoreBoard.startMatch("Mexico", "Canada");
        FootballMatch match2 = scoreBoard.startMatch("Spain", "Brazil");
        FootballMatch match3 = scoreBoard.startMatch("Germany", "France");
        FootballMatch match4 = scoreBoard.startMatch("Uruguay", "Italy");
        FootballMatch match5 = scoreBoard.startMatch("Argentina", "Australia");

        scoreBoard.updateMatch(match1, 0, 5);
        scoreBoard.updateMatch(match2, 10, 2);
        scoreBoard.updateMatch(match3, 2, 2);
        scoreBoard.updateMatch(match4, 6, 6);
        scoreBoard.updateMatch(match5, 3, 1);

        List<FootballMatch> expectScoreBoard = new ArrayList<>();
        expectScoreBoard.add(match4);
        expectScoreBoard.add(match2);
        expectScoreBoard.add(match1);
        expectScoreBoard.add(match5);
        expectScoreBoard.add(match3);

        List<FootballMatch> scoreBoardSummery = scoreBoard.scoreBoardSummery();
        Assertions.assertEquals(expectScoreBoard, scoreBoardSummery);
    }
}


