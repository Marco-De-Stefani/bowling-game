package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.frame.Frame;
import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameScoreHelperTest extends UnitTest {
    @InjectMocks
    private GameScoreHelper gameScoreHelper;

    @Test
    void calculateScoreTest() {
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(5)
                .build();
        int score = gameScoreHelper.calculateScore(frame);
        assertEquals(score, 9);
    }
    @Test
    void calculateScoreWhenStrike() {
        Frame frame = Frame.builder()
                .firstShot(10)
                .build();
        int score = gameScoreHelper.calculateScore(frame);
        assertEquals(score, 10);
    }

    @Test
    void calculateScoreToAddForLastRoll() {
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(6)
                .thirdShot(7)
                .build();
        int score = gameScoreHelper.calculateScoreToAddForLastRoll(frame);
        assertEquals(7, score);
    }

    @Test
    void notAddingNothingIfLastRollNotSpare() {
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(5)
                .thirdShot(7)
                .build();
        int score = gameScoreHelper.calculateScoreToAddForLastRoll(frame);
        assertEquals(0, score);
    }

    @Test
    void calculateScoreToAddWhenSpare() {
        Frame firstFrame = Frame.builder()
                .firstShot(4)
                .secondShot(6)
                .build();
        Frame secondFrame = Frame.builder()
                .firstShot(3)
                .secondShot(2)
                .build();

        List<Frame> frameList = Arrays.asList(firstFrame, secondFrame);
        int score = gameScoreHelper.calculateScoreToAddForSpareOrStrike(frameList, firstFrame);

        assertEquals(3, score);
    }

    @Test
    void calculateScoreToAddWhenStrike() {
        Frame firstFrame = Frame.builder()
                .firstShot(10)
                .build();
        Frame secondFrame = Frame.builder()
                .firstShot(3)
                .secondShot(2)
                .build();

        List<Frame> frameList = Arrays.asList(firstFrame, secondFrame);
        int score = gameScoreHelper.calculateScoreToAddForSpareOrStrike(frameList, firstFrame);

        assertEquals(5, score);
    }
}
