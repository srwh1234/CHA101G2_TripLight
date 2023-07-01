package com.tw.game.controller;

import com.tw.game.model.PrizePool;
import com.tw.game.model.User;
import com.tw.game.repository.PrizePoolRepository;
import com.tw.game.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrizePoolController {

    private PrizePoolRepository prizePoolRepository;
    private UserService userService;

    public PrizePoolController(PrizePoolRepository prizePoolRepository,UserService userService) {
        this.prizePoolRepository = prizePoolRepository;
        this.userService = userService;
    }

    // 取得獎池資料
    @GetMapping("/prizePools")
    public double getPrizePool(){
        PrizePool prizePool = prizePoolRepository.findById(1).orElse(null);
        return prizePool.getLumpSum();
    }

    // 更新獎池資料
    @GetMapping("/prizePools/update")
    public double getPrizePoolUpdate(HttpSession session) {
        User user = (User) session.getAttribute("user");
        PrizePool prizePool = prizePoolRepository.findById(1).orElse(null);
        // 如果玩家資金不夠，獎池不會增加
        if(user.getMoney() < 100){
            return prizePool.getLumpSum();
        }
        if (prizePool != null) {
            prizePool.setLumpSum(prizePool.getLumpSum() + 100);
            prizePoolRepository.save(prizePool); // 保存更新後的獎池數據
            return prizePool.getLumpSum();
        }
        return 0.0; // 或者返回其他預設值，表示未找到獎池數據
    }

    // 將獎池歸0
    @GetMapping("/prizePools/zero")
    public double updatePrizePoolToZero(HttpSession session){
        PrizePool prizePool = prizePoolRepository.findById(1).orElse(null);
        User user = (User) session.getAttribute("user");

        // 避免漏洞玩家
        if (user.getMoney() >= 0) {
            user.setMoney((int) (user.getMoney() + prizePool.getLumpSum()));
            userService.save(user);
            prizePool.setLumpSum(0);
            prizePoolRepository.save(prizePool);
        }
        return prizePool.getLumpSum();


    }
}
