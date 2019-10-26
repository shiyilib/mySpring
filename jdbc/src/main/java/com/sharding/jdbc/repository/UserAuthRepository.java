package com.sharding.jdbc.repository;

import com.sharding.jdbc.model.UserAuthEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAuthRepository extends JpaRepository<UserAuthEntity,Long> {
}
