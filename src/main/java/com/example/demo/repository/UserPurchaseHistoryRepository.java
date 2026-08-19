package com.example.demo.repository;

import com.example.demo.domain.UserPurchaseHistory;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserPurchaseHistoryRepository extends MongoRepository<UserPurchaseHistory, String> {
}
