package com.marcodestefani.bowlingproject.frame;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
public class FrameController {
    private final FrameService frameService;

    @PostMapping("/bowling/games/{gameId}/frames/")
    public Frame addFrame(@PathVariable("gameId") Long gameId, @RequestBody Frame frame) {
        return frameService.addFrame(frame, gameId);
    }

}
