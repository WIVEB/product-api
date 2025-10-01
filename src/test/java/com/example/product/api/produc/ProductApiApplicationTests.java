package com.example.product.api.produc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ConfigurableApplicationContext;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@SpringBootTest
class ProductApiApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void mainMethodCoverage() {
        try (var springApplication = mockStatic(SpringApplication.class)) {
            springApplication.when(() -> SpringApplication.run(any(Class.class), any(String[].class)))
                    .thenReturn(null);

            ProductApiApplication.main(new String[] {});

            springApplication.verify(() -> SpringApplication.run(ProductApiApplication.class, new String[] {}));
        }
    }

}
