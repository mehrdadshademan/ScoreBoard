package com.sportradar.scoreboard;


import com.sportradar.scoreboard.Interfaces.Match;
import com.sportradar.scoreboard.domain.FootballMatch;
import com.sportradar.scoreboard.imp.MatchImpl;
import com.sportradar.scoreboard.imp.ScoreBoardImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class ScoreboardIntegrationTest {

    @Mock
    private Match match;
    @Mock
    private ScoreBoardImpl scoreBoard;

    @BeforeEach
    public void setup() {
        this.match = new MatchImpl();
        this.scoreBoard = new ScoreBoardImpl(match);
    }


    @Test
    void testScoreboardIntegration() {
        FootballMatch firstMatch = scoreBoard.startMatch("Mexico", "Canada");
        FootballMatch secondMatch = scoreBoard.startMatch("Spain", "Brazil");
        FootballMatch thirdMatch = scoreBoard.startMatch("Germany", "France");
        FootballMatch fourthMatch = scoreBoard.startMatch("Uruguay", "Italy");
        FootballMatch fifthMatch = scoreBoard.startMatch("Argentina", "Australia");

        scoreBoard.updateMatch(firstMatch, 0, 5);
        scoreBoard.updateMatch(secondMatch, 10, 2);
        scoreBoard.updateMatch(thirdMatch, 2, 2);
        scoreBoard.updateMatch(fourthMatch, 6, 6);
        scoreBoard.updateMatch(fifthMatch, 3, 1);

        List<FootballMatch> expectScoreBoard = new ArrayList<>();
        expectScoreBoard.add(fourthMatch);
        expectScoreBoard.add(secondMatch);
        expectScoreBoard.add(firstMatch);
        expectScoreBoard.add(fifthMatch);
        expectScoreBoard.add(thirdMatch);

        List<FootballMatch> scoreBoardSummery = scoreBoard.scoreBoardSummery();
        Assertions.assertEquals(expectScoreBoard, scoreBoardSummery);
    }
}


