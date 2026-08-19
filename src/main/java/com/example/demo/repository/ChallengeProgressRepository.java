package com.example.demo.repository;

import com.example.demo.domain.ChallengeProgressRecord;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ChallengeProgressRepository extends MongoRepository<ChallengeProgressRecord, String> {

    List<ChallengeProgressRecord> findByEmailAndLevel(String email, String level);
}
