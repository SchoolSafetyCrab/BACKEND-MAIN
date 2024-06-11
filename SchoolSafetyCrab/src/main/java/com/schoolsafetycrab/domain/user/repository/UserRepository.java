package com.schoolsafetycrab.domain.user.repository;

import com.schoolsafetycrab.domain.user.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findUserByIdAndState(String id, boolean state);
    boolean existsUserByIdAndState(String id, boolean state);
}
