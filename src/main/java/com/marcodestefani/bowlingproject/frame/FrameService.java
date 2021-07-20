package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.game.GameService;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FrameService {

    private final FrameRepository frameRepository;

    private final FrameMapper frameMapper;

    private final GameService gameService;

    public Frame addFrame(Frame frame, Long gameId) {
        GameDao gameDao = gameService.getById(gameId);
        FrameDao frameDao = frameMapper.fromFrame(frame);
        endGameIfIsNeeded(frame, gameDao);
        frameDao.setGameDao(gameDao);
        frameRepository.save(frameDao);
        return frameMapper.fromFrameDao(frameDao);
    }

    private void endGameIfIsNeeded(Frame frame, GameDao gameDao) {
        if (frame.getThirdShot() != null) {
            gameService.endGame(gameDao);
        }
    }

}
