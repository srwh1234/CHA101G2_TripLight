package com.tw.game.dto;



import com.tw.game.model.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
