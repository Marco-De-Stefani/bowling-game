package com.marcodestefani.bowlingproject.frame;

import com.marcodestefani.bowlingproject.frame.dao.FrameDao;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class FrameMapper {

    public FrameDao fromFrame(Frame frame) {
        return FrameDao.builder()
                .firstShot(frame.getFirstShot())
                .secondShot(frame.getSecondShot())
                .thirdShot(frame.getThirdShot())
                .build();
    }

    public Frame fromFrameDao(FrameDao frameDao){
        return Frame.builder()
                .id(frameDao.getId())
                .firstShot(frameDao.getFirstShot())
                .secondShot(frameDao.getSecondShot())
                .thirdShot(frameDao.getThirdShot())
                .build();
    }

    public List<Frame> fromDaoList(List<FrameDao> frameList) {
        return frameList
                .stream()
                .map(this::fromFrameDao)
                .collect(Collectors.toList());
    }
}
