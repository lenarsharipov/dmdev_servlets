package com.java.http.socket;

import java.io.IOException;
import java.net.*;

public class DatagramRunner {
    public static void main(String[] args) throws IOException {
        InetAddress inetAddress = InetAddress.getByName("localhost");
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            /*
            В отличие от tcp, udp использует пакеты для отправки
            и получения сообщений.
            Поэтому здесь используются пакеты, а не
            потоки данных(InputStream/OutputStream)

            В конструктор DatagramPacket передается
            массив - buffer, отступ/offset, длина, ip адрес и порт.
            В этом также отличие от tcp, где эти данные
            передавались в сокет. Здесь эти данные вшиваются в пакет.

            Buffer - это массив, в который записывается
            информация, а затем считывается.
            Размер buffer обычно регламентируется, так как он должен
            быть одинаковым и на клиенте и на сервере,
            чтобы например не потерять информацию, в случае,
            если размер посылаемой информации будет больше
            принимающего буфера.
             */
            // byte[] buffer = new byte[512];
            byte[] buffer = "Hello from UDP!".getBytes();
            DatagramPacket packet =
                    new DatagramPacket(buffer, buffer.length, inetAddress, 7777);

            // отправляет пакет данных
            datagramSocket.send(packet);
        }
    }
}
