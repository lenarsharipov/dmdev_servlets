package com.java.http.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class HttpServer {

    private final ExecutorService pool;

    /*
    Мы будем использовать класс ServerSocket из пакета
    net для представления сервера, который будет принимать
    запросы от нашего клиента. Для сервера заведем порт.
    Также добавим метод run(), который запустит сервер.
    По сути этот метод будет создавать ServerSocket с
    нашим портом. Поместим код в try-catch. В коде мы
    будем обрабатывать полученный socket в методе processSocket().
     */
    private final int port;
    private boolean stopped;

    public HttpServer(int port, int poolSize) {
        this.port = port;
        this.pool = Executors.newFixedThreadPool(poolSize);
    }

    public void run() {
        try {
            ServerSocket server = new ServerSocket(port);
            while (!stopped) {
                // метод accept() - блокирует поток.
                Socket socket = server.accept();
                System.out.println("socket accepted");
                pool.submit(() -> processSocket(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void processSocket(Socket socket) {
        /*
        inputStream считывает запрос от клиента,
        outputStream отправляет ответ от сервера
         */
        try (socket;
             var inputStream = new DataInputStream(socket.getInputStream());
             var outputStream = new DataOutputStream(socket.getOutputStream())) {
            // step 1 handle request
            System.out.println("Request " + new String(inputStream.readNBytes(400)));

            Thread.sleep(10_000);

            // step 2 handle response
            byte[] body = Files.readAllBytes(Path.of("resources", "example.html"));
            var headers = """
                    HTTP/1.1 200 OK
                    content-type: text/html
                    content-length: %s
                    """.formatted(body.length).getBytes();
            outputStream.write(headers);
            outputStream.write(System.lineSeparator().getBytes());
            outputStream.write(body);

        } catch (IOException | InterruptedException e) {
            // TODO: log error message
        }
    }

    public void setStopped(boolean stopped) {
        this.stopped = stopped;
    }
}

























