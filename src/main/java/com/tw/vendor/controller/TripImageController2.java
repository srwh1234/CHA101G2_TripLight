package com.tw.vendor.controller;

import com.tw.vendor.model.TripImage2;
import com.tw.vendor.service.*;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/trip")
public class TripImageController2 {

    public static String IMG_URL = "http://localhost:8080/img/trips/";

        @Autowired
        private TripImageService2 tripImageService2;  // 這邊改成呼叫service

        @GetMapping("/tripImage2")
        public List<TripImage2> getTripImage(){
            return tripImageService2.findAll();
        }


        @PostMapping("/tripImageadd")
        public String processTripImage(@RequestBody TripImage2 tripImage2){
        	tripImageService2.save(tripImage2);
            System.out.println(tripImage2);
            return "成功拿到資料";
        }

//    // 用於指定 {imgUrl} 只能由數字組成。 [0-9] 表示匹配一個數字字符，+ 表示匹配前面的表達式一次或多次。
//    @GetMapping(value = "/img/trips/{imgUrl:[0-9]+}", produces = MediaType.IMAGE_GIF_VALUE)
//    public byte[] getPhoto(@PathVariable("imgUrl") final int id) {
//        return tripService2.findImg(id);
//    }
}
