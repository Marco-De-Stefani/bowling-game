package com.marcodestefani.bowlingproject.player;

import com.marcodestefani.bowlingproject.player.dao.PlayerDao;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final PlayerMapper playerMapper;

    public Player add(Player player) {
        PlayerDao playerDao = playerMapper.fromPlayer(player);
        PlayerDao savedPlayer = playerRepository.save(playerDao);
        return playerMapper.fromPlayerDao(savedPlayer);
    }

    public List<Player> getAllPlayers() {
        return playerRepository.findAll()
                .stream()
                .map(playerMapper::fromPlayerDao)
                .collect(Collectors.toList());
    }
}
