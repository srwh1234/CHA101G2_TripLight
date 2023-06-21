package com.tw.form.controller;


import com.tw.form.dto.ContactData;
import com.tw.form.service.EmailSenderService;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ContactController {

    private final EmailSenderService emailSenderService;

    public ContactController(EmailSenderService emailSenderService) {
        this.emailSenderService = emailSenderService;
    }

    @PostMapping("/contacts")
    public Boolean getContact(@RequestBody ContactData contactData){
        String htmlContent = """
                <!DOCTYPE html>
                <html>
                               
                <head>
                    <style>
                        body {
                            font-family: Arial, sans-serif;
                            background-color: #F5F5F5;
                        }
                               
                        .container {
                            max-width: 800px;
                            margin: 0 auto;
                            padding: 30px 20px;
                            background-color: #fafafa;
                            border-radius: 15px;
                            border: 1px solid #E8E8E8;
                        }
                               
                        h1 {
                            color: #507fc5;
                            text-align: center;
                            margin-top: 0;
                            font-size: 2.5em;
                               
                        }
                               
                        p {
                            font-size: 1.2em;
                            font-weight: 400;
                            color: #333;
                            margin-bottom: 10px;
                            line-height: 1.6;
                        }
                               
                        .info {
                            margin-top: 30px;
                            padding: 10px;
                            border: 1px solid #BDBDBD;
                            border-radius: 10px;
                            background-color: #ECEFF1;
                            text-align: center;
                        }
                               
                        .button-container {
                            text-align: center;
                            margin-top: 30px;
                        }
                               
                        .button {
                            display: inline-block;
                            padding: 12px 24px;
                            background-color: #5C6BC0;
                            color: #ffffff !important;
                            text-decoration: none;
                            border-radius: 5px;
                            font-weight: 700;
                            font-size: 1.2em;
                            text-align: center;
                            transition: background-color 0.3s ease;
                        }
                               
                        .button:hover {
                            background-color: #3949AB;
                        }
                    </style>
                </head>
                               
                <body>
                    <div class="container">
                        <h1>TripLight聯絡我們表單</h1>
                               
                        <div class="info">
                            <p>%s</p>

                        </div>
                             
                    </div>
                </body>
                               
                </html>
                        
                """;
        String formattedMessage = String.format(htmlContent,contactData.toString());
        emailSenderService.sendHTMLEmail("triplight0411@gmail.com","聯絡我們表單",formattedMessage);
        return true;
    }
}
