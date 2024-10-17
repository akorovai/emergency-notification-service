package com.ad.service.user;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface UserRepository extends MongoRepository<User, String> {
    Optional<User> findUserByEmail(String email);
    Optional<User> findUserByUsername(String username);
    Optional<User> findUserByUsernameOrEmail(String username, String email);
    @Query("{ 'refreshToken.token': ?0, 'refreshToken.expires': { $gt: ?1 } }")
    Optional<User> findByNotExpiredRefreshToken(String token, LocalDateTime now);

}
