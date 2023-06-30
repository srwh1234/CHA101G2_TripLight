package com.tw.game.service;



import com.tw.game.dto.UserDto;
import com.tw.game.model.User;
import com.tw.game.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findUser(User user){
        return userRepository.findByEmailAndPassword(user.getEmail(),user.getPassword());
    }
    public User findUserById(Integer id){
        return userRepository.findById(id).orElse(null);
    }

    public User save(User user){
        // 符合規定 才儲存 (-1000 為可容忍範圍)
        if(user.getMoney() > -1000){
            return userRepository.save(user);
        }
        return user;
    }

    public List<User> findAllUser(){
        return userRepository.findAll();
    }
    public List<UserDto> findAllUserOrderByMaxScore() {
        List<User> allByOrderByMaxScoreDesc = userRepository.findAllByOrderByMaxScoreDesc();
        List<UserDto> userDtos = new ArrayList<>();
        allByOrderByMaxScoreDesc.forEach(user -> userDtos.add(new UserDto(user)));
        return userDtos;
    }

    public List<UserDto> findAllUserOrderLevel(){
        List<User> allByOrderByLevelDesc = userRepository.findAllByOrderByLevelDesc();
        List<UserDto> userDtos = new ArrayList<>();
        allByOrderByLevelDesc.forEach(user -> userDtos.add(new UserDto(user)));
        return userDtos;
    }

    public List<User> findUserByUserName(String userName){
        return userRepository.findByUserNameOrderByMaxScoreDesc(userName);
    }

    public void deleteUser(Integer id){
        userRepository.deleteById(id);
    }
}
