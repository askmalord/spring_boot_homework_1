package ru.netology.spring_boot_app1;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class DemoApplicationTests {
    public static final int DEV_PORT = 8080;
    public static final int PROD_PORT = 8081;
    @Autowired
    TestRestTemplate restTemplate;
    @Container
    public static GenericContainer<?> devapp = new GenericContainer<>("devapp")
            .withExposedPorts(DEV_PORT);
    @Container
    public static GenericContainer<?> prodapp = new GenericContainer<>("prodapp")
            .withExposedPorts(PROD_PORT);

    @BeforeAll
    public static void setUp() {
        System.out.println("Запуск контейнеров");
        devapp.start();
        prodapp.start();
    }

    @Test
    void devProfileTest() {
        final String responceTemplate = "Current profile is dev";

        ResponseEntity<String> fromDev = restTemplate.getForEntity("http://localhost:"
                + devapp.getMappedPort(DEV_PORT) + "/profile", String.class);
        String responceBody = fromDev.getBody();
        System.out.println("Тело ответа:" + responceBody);

        assertEquals(responceBody, responceTemplate, "Тело ответа не соответсвует ожидаемому");
    }

    @Test
    void prodProfileTest() {
        final String responceTemplate = "Current profile is production";

        ResponseEntity<String> fromDProd = restTemplate.getForEntity("http://localhost:"
                + prodapp.getMappedPort(PROD_PORT) + "/profile", String.class);
        String responceBody = fromDProd.getBody();
        System.out.println("Тело ответа:" + responceBody);

        assertEquals(responceBody, responceTemplate, "Тело ответа не соответсвует ожидаемому");


    }

}
