package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.game.GameService;
import com.marcodestefani.bowlingproject.game.dao.GameDao;
import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertSame;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class FrameServiceTest extends UnitTest {
    @InjectMocks
    private FrameService frameService;

    @Mock
    private GameService gameService;
    @Mock
    private FrameRepository frameRepository;
    @Mock
    private FrameMapper frameMapper;

    @Test
    void addFrameTest() {
        Frame frame = Frame.builder()
                .firstShot(1)
                .secondShot(5)
                .build();
        FrameDao frameDao = FrameDao.builder().build();
        when(frameMapper.fromFrame(frame)).thenReturn(frameDao);
        Frame frameDaoMapped = Frame.builder().build();
        when(frameMapper.fromFrameDao(frameDao)).thenReturn(frameDaoMapped);

        Frame frameReturned = frameService.addFrame(frame, 123L);

        verify(frameRepository).save(frameDao);
        assertSame(frameDaoMapped, frameReturned);
        verify(gameService, times(0)).endGame(any());
    }

    @Test
    void addLastFrameAndEndGame() {
        Frame frame = Frame.builder()
                .firstShot(8)
                .secondShot(2)
                .thirdShot(4)
                .build();

        FrameDao frameDao = FrameDao.builder().build();
        GameDao gameDaoNotEnded = GameDao.builder().ended(false).build();
        Frame frameDaoMapped = Frame.builder().build();

        when(gameService.getById(123L)).thenReturn(gameDaoNotEnded);
        when(frameMapper.fromFrame(frame)).thenReturn(frameDao);
        when(frameMapper.fromFrameDao(frameDao)).thenReturn(frameDaoMapped);

        Frame frameReturned = frameService.addFrame(frame, 123L);

        verify(gameService).endGame(gameDaoNotEnded);
        verify(frameRepository).save(frameDao);
        assertSame(frameDaoMapped, frameReturned);
    }
}
