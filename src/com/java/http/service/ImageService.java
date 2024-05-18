package com.java.http.service;

import com.java.http.util.PropertiesUtil;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import static lombok.AccessLevel.PRIVATE;

@NoArgsConstructor(access = PRIVATE)
public class ImageService {
    public static final ImageService INSTANCE = new ImageService();

    private final String basePath = PropertiesUtil.get("image.base.url");

    public static ImageService getInstance() {
        return INSTANCE;
    }

    @SneakyThrows
    public void upload(String imagePath, InputStream imageContent) {
        Path imageFullPath = Path.of(basePath, imagePath);
        try (imageContent) {
            // Создадим все папки до фото на всякий случай
            Files.createDirectories(imageFullPath.getParent());
            Files.write(
                    imageFullPath, //
                    imageContent.readAllBytes(),
                    StandardOpenOption.CREATE, // Создать файл если его нет
                    StandardOpenOption.TRUNCATE_EXISTING // Не фейлим приложение если пользователь сохраняет снова ту же фотку
            );

        }

    }

}
