package pl.edu.wszib.media.store;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.DigestUtils;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }
    /*
     * public static void main(String[] args) {
     * System.out.println(DigestUtils.md5DigestAsHex("mateusz123".getBytes()));
     * }
     */
}
