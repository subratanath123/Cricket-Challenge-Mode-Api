package com.example.demo.service;

import com.example.demo.domain.ChallengeProgressRecord;
import com.example.demo.dto.MyChallengeLevelProgress;
import com.example.demo.repository.ChallengeProgressRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ChallengeProgressService {

    private final ChallengeProgressRepository repository;

    public ChallengeProgressService(ChallengeProgressRepository repository) {
        this.repository = repository;
    }

    public MyChallengeLevelProgress getProgress(String email, String level, String myTeam) {
        List<MyChallengeLevelProgress.ChallengeProgress> progress = repository.findByEmailAndLevel(email, level)
                .stream()
                .map(record -> new MyChallengeLevelProgress.ChallengeProgress.Builder()
                        .setMyTeamName(record.getMyTeamName() != null ? record.getMyTeamName() : myTeam)
                        .setChallengeId(record.getChallengeId())
                        .setSummary(record.getSummary())
                        .build())
                .collect(Collectors.toList());

        return new MyChallengeLevelProgress.Builder()
                .setLevel(level)
                .setChallengeProgress(progress)
                .build();
    }

    public void saveProgress(String email, String level, String myTeam, int challengeId, String summary) {
        ChallengeProgressRecord record = new ChallengeProgressRecord();
        record.setEmail(email);
        record.setLevel(level);
        record.setMyTeamName(myTeam);
        record.setChallengeId(challengeId);
        record.setSummary(summary);
        repository.save(record);
    }
}
