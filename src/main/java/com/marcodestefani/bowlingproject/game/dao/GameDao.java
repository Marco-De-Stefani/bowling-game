package com.marcodestefani.bowlingproject.game.dao;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "GAMES")
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GameDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "GAME_ID")
    private Long id;

    @Column(name="ENDED")
    private boolean ended;

    @OneToMany(mappedBy = "gameDao",fetch = FetchType.LAZY)
    private List<FrameDao> frameList;

    @ManyToOne
    private PlayerDao playerDao;

}


