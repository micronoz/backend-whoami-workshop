package com.example.whoami;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

public interface UserRepository extends CrudRepository<User, Long> {
  User findByUserName(String userName);

  @Query("DELETE FROM User u WHERE u.id <> :excludeId")
  @Modifying
  @Transactional
  void deleteExcept(@Param("excludeId") String id);

  User findById(String id);
}