package com.marcodestefani.bowlingproject.frame;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder
public class Frame implements Serializable {
    private Long id;
    private Integer firstShot;
    private Integer secondShot;
    private Integer thirdShot;

    public boolean isStrike() {
        return secondShot == null && firstShot == 10;
    }

    public boolean isSpare() {
        if (!bothRolls())
            return false;
        return (firstShot + secondShot) == 10;
    }

    public boolean isFrameEnded() {
        return bothRolls() || isStrike();
    }

    private boolean bothRolls() {
        return firstShot != null && secondShot != null;
    }

    public boolean isFinalFrame() {
        return thirdShot != null;
    }
}
