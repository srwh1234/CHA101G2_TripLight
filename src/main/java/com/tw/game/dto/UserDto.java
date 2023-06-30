package com.tw.game.dto;



import com.tw.game.model.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserDto {
    String userName;
    int level;
    int money;
    int maxScore;

    public UserDto(User user) {
        this.userName = user.getUserName();
        this.level = user.getLevel();
        this.money = user.getMoney();
        this.maxScore = user.getMaxScore();
    }
}
