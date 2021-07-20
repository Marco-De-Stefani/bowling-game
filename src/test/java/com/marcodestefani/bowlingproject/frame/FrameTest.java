package com.marcodestefani.bowlingproject.frame;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FrameTest  {

    @Test
    void isSpareTest() {
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(6)
                .build();
        assertTrue(frame.isSpare());
    }

    @Test
    void cantBeSpareWithOneRoll(){
        Frame frame = Frame.builder()
                .firstShot(4)
                .build();
        assertFalse(frame.isSpare());
    }

    @Test
    void cantBeSpareWithOneShotMissed(){
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(0)
                .build();
        assertFalse(frame.isSpare());
    }

    @Test
    void isFinalFrame() {
        Frame frame= Frame.builder()
                .firstShot(1)
                .secondShot(2)
                .thirdShot(3)
                .build();
        assertTrue(frame.isFinalFrame());
    }

    @Test
    void isFinalFrameWithStrike() {
        Frame frame= Frame.builder()
                .firstShot(10)
                .secondShot(0)
                .thirdShot(6)
                .build();
        assertTrue(frame.isFinalFrame());
    }

    @Test
    void isStrikeTest() {
        Frame frame = Frame.builder()
                .firstShot(10)
                .build();
        assertTrue(frame.isStrike());
    }

    @Test
    void cantBeStrikeAtSecondRoll() {
        Frame frame = Frame.builder()
                .firstShot(0)
                .secondShot(10)
                .build();
        assertFalse(frame.isStrike());
    }

    @Test
    void isFrameEndedTest() {
        Frame frame = Frame.builder()
                .firstShot(5)
                .secondShot(6)
                .build();
        assertTrue(frame.isFrameEnded());
    }

    @Test
    void cantBeFrameEndedWithOneRollTest() {
        Frame frame = Frame.builder()
                .firstShot(5)
                .build();
        assertFalse(frame.isFrameEnded());
    }


    @Test
    void isFrameEndedWithStike() {
        Frame frame = Frame.builder()
                .firstShot(10)
                .build();
        assertTrue(frame.isFrameEnded());
    }
}
