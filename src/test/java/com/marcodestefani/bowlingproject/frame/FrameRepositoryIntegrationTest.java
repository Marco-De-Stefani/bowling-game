package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.utils.IntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FrameRepositoryIntegrationTest extends IntegrationTest {

    @Autowired
    private FrameRepository frameRepository;

    @Test
    void frameRepositorySavesDao() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(1)
                .secondShot(2)
                .thirdShot(3)
                .build();

        frameRepository.save(frameDao);

        List<FrameDao> allFrames = frameRepository.findAll();
        assertEquals(1, allFrames.size());
        assertEquals(1, allFrames.get(0).getFirstShot());
        assertEquals(2, allFrames.get(0).getSecondShot());
        assertEquals(3, allFrames.get(0).getThirdShot());

    }
}
