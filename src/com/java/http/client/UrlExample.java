package com.java.http.client;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;

public class UrlExample {

    public static void main(String[] args) throws IOException {
        /*
        Через класс URL мы можем обращаться не только к веб ресурсам, указав, например, https,
        но и к файлам на нашем диске. Обратимся к классу DatagramRunner
         */
        URL url = new URL("file:C:\\projects\\http-servlets-starter\\src\\com\\java\\http\\socket\\DatagramRunner.java");
        URLConnection urlConnection = url.openConnection();

        System.out.println(new String(urlConnection.getInputStream().readAllBytes()));
    }

    private static void checkGoogle() throws IOException {
    /* URL обычно предназначен для работы с методом GET.
       Им можно пользоваться для работы и с другими методами,
       но он очень неудобен.
       При создании объекта URL в конструкторе указываем протокол + домен.
       Далее подключаемся к серверу вызвав метод opeConnection()
    */
        URL url = new URL("https://www.google.com");
        URLConnection urlConnection = url.openConnection();
        /*
        Через URLConnection мы можем получить header,
        body через urlConnection.getInputStream() или urlConnection.getContent()
         */
        urlConnection.setDoOutput(true);
        try (OutputStream outputStream = urlConnection.getOutputStream()) {
        }
        System.out.println(urlConnection);
    }
}
