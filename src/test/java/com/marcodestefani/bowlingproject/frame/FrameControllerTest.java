package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class FrameControllerTest extends UnitTest {

    @InjectMocks
    private FrameController frameController;

    @Mock
    private FrameService frameService;

    @Test
    void frameControllerCallsServiceForAddingFrame() {
        Frame frame = Frame.builder().build();
        Frame frameExpected = Frame.builder().build();
        when(frameService.addFrame(frame,123L)).thenReturn(frameExpected);

        Frame frameReturned = frameController.addFrame(123L, frame);

        assertSame(frameExpected,frameReturned);
    }
}
