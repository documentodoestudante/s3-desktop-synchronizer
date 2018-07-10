package view;

import java.io.IOException;

public class Principal {
    public static String BASE_PATH = "";
    public static String FILE_SEPARATOR = System.getProperty("file.separator");

    public static void main(String[] args) throws IOException {
        new TelaPrincipalView();
    }
}
