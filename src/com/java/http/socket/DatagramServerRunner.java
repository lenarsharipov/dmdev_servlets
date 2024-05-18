package com.java.http.socket;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class DatagramServerRunner {
    public static void main(String[] args) throws IOException {
        /*
        В отличие от tcp, гд для клиента и сервера были выделенные классы,
        в udp для клиента и сервера выделен один класс - DatagramSocket.
        В случае сервера, в конструктор надо передать номер порта.
         */
        try (DatagramSocket datagramServer = new DatagramSocket(7777)) {
            byte[] buffer = new byte[512];
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

            // принимаем переданный клиентом пакет
            datagramServer.receive(packet);

            System.out.println(new String(buffer));
        }
    }
}