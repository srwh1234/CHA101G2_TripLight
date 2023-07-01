package com.tw.game.controller;

import com.tw.game.dto.UserDto;
import com.tw.game.model.User;
import com.tw.game.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {

    private UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Get user
    @GetMapping()
    public User getUser(HttpSession session){
        User user = (User)session.getAttribute("user");
        return user;
    }


    // Get all users
    @GetMapping("/all")
    public List<User> getAllUsers(HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user.getEmail().equals("?") && user.getPassword().equals("!")) {
            return userService.findAllUser();
        } else {
            return null;
        }
    }

    // Get all users sorted by score
    @GetMapping("/score")
    public List<UserDto> getUsersSortedByScore(){
        return userService.findAllUserOrderByMaxScore();
    }
    // Get all users sorted by level
    @GetMapping("/level")
    public List<UserDto> getUsersSortedByLevel(){
        return userService.findAllUserOrderLevel();
    }

    // Get a single user by username
    @GetMapping("/{userName}")
    public List<User> getUserByUserName(@PathVariable String userName){
        return userService.findUserByUserName(userName);
    }

    // Save a user
    @PutMapping("/money")
    public boolean saveUser(@RequestParam int money,HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setMoney(user.getMoney()+money);
        userService.save(user);
        return true;
    }

    @PutMapping("/money-50")
    public boolean UserMoneyDown(HttpSession session) {
        User user = (User) session.getAttribute("user");
        user.setMoney(user.getMoney()-50);
        userService.save(user);
        return true;
    }

    @PutMapping("/levelUp")
    public UserDto UserLevelUp(HttpSession session) {
        User user = (User) session.getAttribute("user");
        int requiredExp = 100;
        for (int i = 2; i <= user.getLevel(); i++) {
            requiredExp += 10 * (i - 1);
        }
        user.setMoney(user.getMoney()-requiredExp);

        // 如果金錢為正的 才會升等
        if(user.getMoney() > 0){
            user.setLevel(user.getLevel()+1);
            userService.save(user);
            return new UserDto(user);
        }
        return null;
    }



    // Update a user by ID
    @PutMapping("/{userId}")
    public boolean updateUser(@PathVariable int userId, @RequestParam int money,HttpSession session){
        User manager = (User)session.getAttribute("user");

        if (manager.getEmail().equals("?") && manager.getPassword().equals("!")) {
            User user = userService.findUserById(userId);
            System.out.println(money);
            user.setMoney(money);
            userService.save(user);
            return true;
        }else {
            return false;
        }
    }
    // Update a user's maximum score
    @PutMapping("/score")
    public boolean updateUserMaxScore(@RequestParam int score, HttpSession session){
        User user = (User) session.getAttribute("user");
        System.out.println(score);
        if(user.getMaxScore() < score){
            user.setMaxScore(score);
        }
        int money = user.getMoney();
        user.setMoney(money+score*20-50);
        user.setPlayTimes(user.getPlayTimes()+1);
        userService.save(user);
        return true;
    }
    // Delete a user by ID
    @DeleteMapping("/{userId}")
    public boolean deleteUser(@PathVariable int userId,HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user.getEmail().equals("?") && user.getPassword().equals("!")) {
            if(userService.findUserById(userId)!= null){
                userService.deleteUser(userId);
                return true;
            }
        }
        return false;
    }
}
