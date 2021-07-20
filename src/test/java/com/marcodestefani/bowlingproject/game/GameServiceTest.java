package com.marcodestefani.bowlingproject.game;

import com.marcodestefani.bowlingproject.frame.Frame;
import com.marcodestefani.bowlingproject.frame.FrameMapper;
import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.game.GameRepository;
import com.marcodestefani.bowlingproject.game.GameService;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.player.Player;
import com.marcodestefani.bowlingproject.player.PlayerRepository;
import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class GameServiceTest extends UnitTest {

    @InjectMocks
    private GameService service;

    @Mock
    private GameRepository gameRepository;
    @Mock
    private PlayerRepository playerRepository;
    @Captor
    private ArgumentCaptor<GameDao> gameDaoArgumentCaptor;

    @Mock
    private GameScoreHelper gameScoreHelper;

    @Mock
    private FrameMapper frameMapper;

    @Mock
    private GameMapper gameMapper;


    @Test
    void gameServiceCallsRepositoryForStartingNewGame() {
        Game game = Game.builder().playerId(123L).build();
        PlayerDao playerDao = createPlayerDao(123L, new ArrayList<>());
        when(playerRepository.getById(123L)).thenReturn(playerDao);
        Game gameMapped = Game.builder().build();
        when(gameMapper.fromDao(any())).thenReturn(gameMapped);

        Game gameReturned = service.startGame(game);

        verify(gameRepository).save(any());
        assertSame(gameMapped,gameReturned);
    }

    @Test
    void startingGameOnlyIfAllOtherGamesOfThePlayerEnded() {
        Game game = Game.builder().playerId(123L).build();

        GameDao firstGame = GameDao.builder()
                .ended(true)
                .build();
        GameDao secondGame = GameDao.builder()
                .ended(true)
                .build();
        PlayerDao playerDao = createPlayerDao(123L, Arrays.asList(firstGame, secondGame));
        when(playerRepository.getById(123L)).thenReturn(playerDao);
        Game gameMapped = Game.builder().build();
        when(gameMapper.fromDao(any())).thenReturn(gameMapped);

        Game gameReturned = service.startGame(game);

        verify(gameRepository).save(any());
        assertSame(gameMapped,gameReturned);
    }

    @Test
    void notStartingGameIfAnotherIsStarted() {
        Game game = Game.builder().playerId(123L).build();

        GameDao gameDao = GameDao.builder()
                .ended(false)
                .build();
        PlayerDao playerDao = createPlayerDao(123L, Arrays.asList(gameDao));
        when(playerRepository.getById(123L)).thenReturn(playerDao);

        assertThrows(RuntimeException.class,
                () -> service.startGame(game),
                "Can't start game: Another game with this player in progress");

        verify(gameRepository, times(0)).save(any());
    }

    @Test
    void gameServiceCallsRepositoryForEndingGame() {
        GameDao gameDao = GameDao.builder().build();

        service.endGame(gameDao);

        verify(gameRepository).save(gameDaoArgumentCaptor.capture());
        assertTrue(gameDaoArgumentCaptor.getValue().isEnded());
    }

    @Test
    void cantCalculateScoreIfGameDoesNotExists() {
        assertThrows(RuntimeException.class,
                () -> service.calculateScore(456L),
                "Game not found");
    }

    @Test
    void calculateScoreForSingleFrameTest() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(4)
                .secondShot(5)
                .build();
        List<FrameDao> frameDaoList = Arrays.asList(frameDao);
        GameDao gameDao = GameDao.builder()
                .frameList(frameDaoList)
                .build();
        when(gameRepository.findById(123L)).thenReturn(Optional.of(gameDao));
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(5)
                .build();
        List<Frame> frameList = Arrays.asList(frame);
        when(frameMapper.fromDaoList(frameDaoList)).thenReturn(frameList);
        when(gameScoreHelper.calculateScore(frame)).thenReturn(9);

        int score = service.calculateScore(123L);

        assertEquals(9, score);
    }

    @Test
    void calculateScoreForFinalFrame() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(4)
                .secondShot(6)
                .thirdShot(3)
                .build();
        List<FrameDao> frameDaoList = Arrays.asList(frameDao);
        GameDao gameDao = GameDao.builder()
                .frameList(frameDaoList)
                .build();
        when(gameRepository.findById(123L)).thenReturn(Optional.of(gameDao));
        Frame frame = Frame.builder()
                .firstShot(4)
                .secondShot(6)
                .thirdShot(3)
                .build();
        List<Frame> frameList = Arrays.asList(frame);
        when(frameMapper.fromDaoList(frameDaoList)).thenReturn(frameList);
        when(gameScoreHelper.calculateScore(frame)).thenReturn(10);
        when(gameScoreHelper.calculateScoreToAddForLastRoll(frame)).thenReturn(3);

        int score = service.calculateScore(123L);

        assertEquals(13, score);
    }

    @Test
    void calculateScoreWhenIsSpare() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(4)
                .secondShot(6)
                .build();
        List<FrameDao> frameDaoList = Arrays.asList(frameDao);
        GameDao gameDao = GameDao.builder()
                .frameList(frameDaoList)
                .build();
        when(gameRepository.findById(123L)).thenReturn(Optional.of(gameDao));
        Frame actualFrame = Frame.builder()
                .firstShot(4)
                .secondShot(6)
                .build();
        Frame nextFrame = Frame.builder()
                .firstShot(5)
                .secondShot(3)
                .build();
        List<Frame> frameList = Arrays.asList(actualFrame, nextFrame);
        when(frameMapper.fromDaoList(frameDaoList)).thenReturn(frameList);
        when(gameScoreHelper.calculateScore(actualFrame)).thenReturn(10);
        when(gameScoreHelper.calculateScoreToAddForSpareOrStrike(frameList, actualFrame)).thenReturn(5);

        int score = service.calculateScore(123L);

        assertEquals(15, score);
    }

    @Test
    void calculateScoreWhenIsStrike() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(10)
                .build();
        List<FrameDao> frameDaoList = Arrays.asList(frameDao);
        GameDao gameDao = GameDao.builder()
                .frameList(frameDaoList)
                .build();
        when(gameRepository.findById(123L)).thenReturn(Optional.of(gameDao));
        Frame actualFrame = Frame.builder()
                .firstShot(4)
                .secondShot(6)
                .build();
        Frame nextFrame = Frame.builder()
                .firstShot(5)
                .secondShot(3)
                .build();
        List<Frame> frameList = Arrays.asList(actualFrame, nextFrame);
        when(frameMapper.fromDaoList(frameDaoList)).thenReturn(frameList);
        when(gameScoreHelper.calculateScore(actualFrame)).thenReturn(10);
        when(gameScoreHelper.calculateScoreToAddForSpareOrStrike(frameList, actualFrame)).thenReturn(8);

        int score = service.calculateScore(123L);

        assertEquals(18, score);
    }

    @Test
    void foundAFrameNotEndedWhenCalculatingScore() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(4)
                .secondShot(6)
                .build();
        List<FrameDao> frameDaoList = Arrays.asList(frameDao);
        GameDao gameDao = GameDao.builder()
                .frameList(frameDaoList)
                .build();
        when(gameRepository.findById(123L)).thenReturn(Optional.of(gameDao));
        Frame actualFrame = Frame.builder()
                .firstShot(1)
                .build();
        List<Frame> frameList = Arrays.asList(actualFrame);
        when(frameMapper.fromDaoList(frameDaoList)).thenReturn(frameList);

        assertThrows(RuntimeException.class,
                () -> service.calculateScore(123L),
                "Found a frame not ended");
    }

    @Test
    void findGameByIdIfExists() {
        GameDao gameDao = GameDao.builder().build();
        when(gameRepository.findById(123L)).thenReturn(Optional.of(gameDao));

        GameDao gameReturned = service.getById(123L);
        assertSame(gameDao,gameReturned);
    }

    @Test
    void errorInFindGameByIdWhenNotExists() {
        when(gameRepository.findById(123L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class,
                () -> service.getById(123L),
                "Found a frame not ended");
    }

    private PlayerDao createPlayerDao(Long id, List<GameDao> games) {
        PlayerDao playerDao = new PlayerDao();
        playerDao.setId(id);
        playerDao.setGamesList(games);
        return playerDao;
    }
}
