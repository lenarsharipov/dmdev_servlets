package com.java.http.locale;

import java.util.Locale;
import java.util.ResourceBundle;

public class LocaleRunner {

    public static void main(String[] args) {
        /*
        * В java есть специальный класс: Locale
        * При создании объекта, надо в конструктор
        * передать язык и страну.
        * Locale представляется в виде язык_страна:
        * ru_RU
        * */
        Locale locale = Locale.of("ru", "RU");
        System.out.println(locale); // ru_RU
        System.out.println(Locale.US); // en_US

        // У каждого приложения есть дефолтная локаль
        System.out.println(Locale.getDefault()); // en_US


        /*
        * Есть класс ResourceBundle. Он используется для
        * того чтобы вытягивать переводы из properties файлов.
        * Вызываем у этого класса метод getBundle(), передав
        * аргументом название properties файла.
        * */
        ResourceBundle translations = ResourceBundle.getBundle("translations");
        System.out.println(translations.getString("page.login.password")); // Password

        /*
        * Получим значения для русского языка и России
        * */
        ResourceBundle translationsRu = ResourceBundle.getBundle(
                "translations",
                Locale.of("ru", "RU"));
        System.out.println(translationsRu.getString("page.login.password")); // Пароль

    }

}
