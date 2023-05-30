package com.tw.ai.common;




import com.tw.ticket.controller.ImageController;
import com.tw.trip.dao.TripDAO;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class MyCommandLineRunner implements CommandLineRunner {

    private TripDAO tripDAO;




    public MyCommandLineRunner(TripDAO tripDAO, ImageController imageController) {
        this.tripDAO = tripDAO;
    }

    @Override
    public void run(String... args)  {
        System.out.println("伺服器部屬完畢時執行");
    }
}
