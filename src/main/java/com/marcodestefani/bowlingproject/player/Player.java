package com.marcodestefani.bowlingproject.player;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Player implements Serializable {
    private Long id;
    private String name;
    private String lastname;
}
