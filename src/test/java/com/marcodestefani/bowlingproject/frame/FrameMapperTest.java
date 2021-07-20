package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import com.marcodestefani.bowlingproject.utils.UnitTest;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class FrameMapperTest extends UnitTest {
    @InjectMocks
    private FrameMapper frameMapper;

    @Test
    void frameConvertedToDaoTest() {
        Frame frame = Frame.builder()
                .firstShot(1)
                .secondShot(2)
                .thirdShot(3)
                .build();

        FrameDao frameDao = frameMapper.fromFrame(frame);

        assertEquals(1,frameDao.getFirstShot());
        assertEquals(2,frameDao.getSecondShot());
        assertEquals(3,frameDao.getThirdShot());
    }

    @Test
    void daoConvertedToFrame() {
        FrameDao frameDao = FrameDao.builder()
                .firstShot(1)
                .secondShot(2)
                .thirdShot(3)
                .build();

        Frame frame = frameMapper.fromFrameDao(frameDao);

        assertEquals(1,frame.getFirstShot());
        assertEquals(2,frame.getSecondShot());
        assertEquals(3,frame.getThirdShot());
    }

    @Test
    void frameDaoListConvertedToFrameList() {
        FrameDao firstFrameDao = FrameDao.builder()
                .id(1L)
                .firstShot(1)
                .secondShot(2)
                .thirdShot(3)
                .build();
        FrameDao secondFrameDao = FrameDao.builder()
                .id(2L)
                .firstShot(4)
                .secondShot(5)
                .thirdShot(6)
                .build();

        List<Frame> frames = frameMapper.fromDaoList(Arrays.asList(firstFrameDao, secondFrameDao));

        assertEquals(1,frames.get(0).getId());
        assertEquals(1,frames.get(0).getFirstShot());
        assertEquals(2,frames.get(0).getSecondShot());
        assertEquals(3,frames.get(0).getThirdShot());
        assertEquals(2,frames.get(1).getId());
        assertEquals(4,frames.get(1).getFirstShot());
        assertEquals(5,frames.get(1).getSecondShot());
        assertEquals(6,frames.get(1).getThirdShot());

    }
}
