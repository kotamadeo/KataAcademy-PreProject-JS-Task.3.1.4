package com.gmail.at.kotamadeo;

import com.gmail.at.kotamadeo.controller.AdminRestController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ExtendWith(SpringExtension.class)
class AdminRestControllerTest {
    private final AdminRestController adminRestController;

    @Autowired
    public AdminRestControllerTest(AdminRestController adminRestController) {
        this.adminRestController = adminRestController;
    }

    @Test
    void contextLoads() throws Exception {
        assertThat(adminRestController).isNotNull();
    }
}
