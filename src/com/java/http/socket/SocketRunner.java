package com.java.http.socket;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class SocketRunner {
    public static void main(String[] args) throws IOException {
        /*
        В примере реализуется TCP networking. Socket - наш клиент,
        который должен открыть соединение и передать запрос.
        То есть мы должны считать данные из потока.

        Если http и порт не указан, то по-умолчанию порт = 80
        Если https и порт не указан, то по-умолчанию порт = 443
        Так как мы работаем с Socket, а он работает с tcp,
        то указанные порты это порты tcp.
        Socket - это ресурс. Каждый объект Socket реализует
        интерфейс Closeable и его надо закрывать после
        использования => обернем в try with resources.
        */
        // Inet4Address.getByName возвращает один рандомно выбранный адрес принадлежащий указанному хосту
        InetAddress inetAddress = Inet4Address.getByName("localhost");
        try (Socket socket = new Socket(inetAddress, 7777);
             var outputStream = new DataOutputStream(socket.getOutputStream());
             var inputStream = new DataInputStream(socket.getInputStream());
             var scanner = new Scanner(System.in)) {
            while (scanner.hasNextLine()) {
                String request = scanner.nextLine();
                outputStream.writeUTF(request);
                System.out.println("Response from the server: " + inputStream.readUTF());
            }
        }
    }
}
