package ro.fastrackit.homeWork8.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ro.fastrackit.homeWork8.model.entity.Cleanup;
import ro.fastrackit.homeWork8.repository.CleanupRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CleanupService {
    private final CleanupRepository cleanupRepository;

    public List<Cleanup> getCleanupByRoomId(long roomId) {
        return cleanupRepository.findCleanupsByRoomId(roomId);
    }
}
