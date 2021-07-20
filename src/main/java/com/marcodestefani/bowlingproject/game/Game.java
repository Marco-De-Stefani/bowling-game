package com.marcodestefani.bowlingproject.game;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Game implements Serializable {
    private Long id;
    private Long playerId;
}
