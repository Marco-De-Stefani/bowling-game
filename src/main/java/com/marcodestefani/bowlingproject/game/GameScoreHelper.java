package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.frame.Frame;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class GameScoreHelper {

    public int calculateScoreToAddForLastRoll(Frame frame) {
        if (frame.isSpare()) {
            return frame.getThirdShot();
        }
        return 0;
    }

    public int calculateScoreToAddForSpareOrStrike(List<Frame> frameList, Frame frame) {
        for (int i = 0; i < frameList.size(); i++) {
            if (frameList.get(i).equals(frame)) {
                if (frame.isStrike()) {
                    return calculateScore(frameList.get(i + 1));
                }
                if (frame.isSpare()) {
                    return frameList.get(i + 1).getFirstShot();
                }
            }
        }
        return 0;
    }

    public int calculateScore(Frame frame) {
        if (frame.getSecondShot() != null)
            return frame.getFirstShot() + frame.getSecondShot();
        else
            return frame.getFirstShot();
    }
}
