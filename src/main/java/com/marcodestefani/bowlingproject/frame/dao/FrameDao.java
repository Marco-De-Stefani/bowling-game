package com.marcodestefani.bowlingproject.frame.dao;

import com.marcodestefani.bowlingproject.game.dao.GameDao;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "FRAMES")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FrameDao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "FRAME_ID")
    private Long id;

    @Column(name = "FIRST_SHOT")
    private Integer firstShot;
    @Column(name = "SECOND_SHOT")
    private Integer secondShot;
    @Column(name = "THIRD_SHOT")
    private Integer thirdShot;

    @ManyToOne
    private GameDao gameDao;

}
