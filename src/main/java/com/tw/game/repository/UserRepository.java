package com.tw.game.repository;


import com.tw.game.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByEmailAndPassword(String email,String password);
    List<User> findAllByOrderByMaxScoreDesc();

    List<User> findAllByOrderByLevelDesc();

    List<User> findByUserNameOrderByMaxScoreDesc(String userName);

}
