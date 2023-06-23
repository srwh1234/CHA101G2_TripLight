package com.tw.form.util;

public class HTMLFormat {

    public static String getHTMLFormat(String title,String titleColor,String content){
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
                            color: %s;
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
                        <h1>%s</h1>
                               
                        <div class="info">
                            <p>%s</p>
                        </div>
                    </div>
                </body>
                               
                </html>
                        
                """;
       return String.format(htmlContent,title ,titleColor, content);
    }
    public static String getHTMLFormat(String title,String titleColor,String content,String ButtonTitle,String ButtonLink){
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
                            color: %s;
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
                        <h1>%s</h1>
                               
                        <div class="info">
                            <p>%s</p>
                        </div>
                               
                        <div class="button-container">
                            <a href="%s" class="button">%s</a>
                        </div>
                    </div>
                </body>
                               
                </html>
                        
                """;
        return String.format(htmlContent,titleColor ,title, content,ButtonLink,ButtonTitle);
    }
}
