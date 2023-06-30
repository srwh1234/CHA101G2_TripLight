package com.tw.game.controller;



import com.tw.game.model.User;
import com.tw.game.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/games")
public class GameLoginController {

    private UserService userService;

    @Autowired
    public GameLoginController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public User login(@RequestBody User user, HttpSession session){
        User theUser = userService.findUser(user);
        if(theUser != null){
            // 登入次數
            theUser.setLoginTimes(theUser.getLoginTimes()+1);
            userService.save(theUser);
            // 設置session
            session.setAttribute("user",theUser);
            return theUser;
        }
        return null;
    }

    @PostMapping("/register")
    public Boolean register(@RequestBody User user){
        User theUser = userService.findUser(user);
        if(theUser == null){
            user.setMoney(1000);
            user.setLevel(6);
            userService.save(user);
            return true;
        }
        return false;
    }
}
