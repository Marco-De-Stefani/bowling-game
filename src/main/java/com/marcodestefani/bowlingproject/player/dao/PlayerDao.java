package com.marcodestefani.bowlingproject.player.dao;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "PLAYERS")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PlayerDao {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PLAYER_ID")
    private long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "LASTNAME")
    private String lastname;

    @OneToMany(mappedBy = "playerDao",fetch = FetchType.LAZY)
    private List<GameDao> gamesList = new ArrayList<>();
}
