package com.tw.ai.common;



import com.tw.trip.dao.TripDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripDAO tripDAO;

    public MyCommandLineRunner(TripDAO tripDAO) {
        this.tripDAO = tripDAO;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("伺服器部屬完畢時執行");
    }
}
