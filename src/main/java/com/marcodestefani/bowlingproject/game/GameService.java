package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.frame.Frame;
import com.marcodestefani.bowlingproject.frame.FrameMapper;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.player.PlayerRepository;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class GameService {
    private final GameRepository gameRepository;
    private final PlayerRepository playerRepository;
    private final FrameMapper frameMapper;
    private final GameMapper gameMapper;
    private final GameScoreHelper gameScoreHelper;

    public Game startGame(Game game) {
        PlayerDao playerDao = playerRepository.getById(game.getPlayerId());
        Optional<GameDao> gameNotCompleted = playerDao.getGamesList()
                .stream()
                .filter(gameDao -> !gameDao.isEnded())
                .findAny();
        if (gameNotCompleted.isPresent()) {
            throw new RuntimeException("Can't start game: Another game with this player in progress");
        }
        GameDao gameDao = GameDao.builder()
                .playerDao(playerDao)
                .build();
        gameRepository.save(gameDao);
        return gameMapper.fromDao(gameDao);
    }

    public void endGame(GameDao gameDao) {
        gameDao.setEnded(true);
        gameRepository.save(gameDao);
    }

    public int calculateScore(Long gameId) {
        GameDao actualGame = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        int score = 0;
        List<Frame> frameList = frameMapper.fromDaoList(actualGame.getFrameList());

        for (Frame frame : frameList) {
            terminateIfNotEnded(frame);
            score += gameScoreHelper.calculateScore(frame);
            if (frame.isFinalFrame()) {
                score += gameScoreHelper.calculateScoreToAddForLastRoll(frame);
            } else {
                score += gameScoreHelper.calculateScoreToAddForSpareOrStrike(frameList, frame);
            }
        }

        return score;
    }

    private void terminateIfNotEnded(Frame frame) {
        if (!frame.isFrameEnded()) {
            throw new RuntimeException("Found a frame not ended");
        }
    }

    public GameDao getById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
    }
}
