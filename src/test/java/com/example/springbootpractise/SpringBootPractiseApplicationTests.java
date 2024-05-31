package com.example.springbootpractise;

import com.example.springbootpractise.config.JavaConfig;
import com.example.springbootpractise.profile.DevProfile;
import com.example.springbootpractise.profile.SystemProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootPractiseApplicationTests {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

    private  final GenericContainer<?> genericContainerDev = new GenericContainer<>("devapp")
            .withExposedPorts(8080);

    private  final GenericContainer<?> genericContainerProduction = new GenericContainer<>("productionapp")
            .withExposedPorts(8081);

    @Autowired
    private TestRestTemplate testRestTemplate;

    @BeforeEach
    void setUp() {
        genericContainerDev.start();
        genericContainerProduction.start();
    }

    @Test
    public void whenValueSetToDev_thenCreateDevProfile() {
        this.contextRunner.withPropertyValues("profile.dev = true")
                .withUserConfiguration(JavaConfig.class)
                .run(context -> {
                    assertThat(context).hasBean("devProfile");
                    SystemProfile systemProfile = context.getBean(DevProfile.class);
                    assertThat(systemProfile.getProfile()).isEqualTo("Current profile is dev");
                    assertThat(context).doesNotHaveBean("profile.production");
                });
    }

    @Test
    void testDevProfile() {
        ResponseEntity<String> forEntity = testRestTemplate.getForEntity("http://localhost:" + genericContainerDev.getMappedPort(8080) + "/profile" , String.class);
        String currentProfileExpected = forEntity.getBody();
        assertEquals(currentProfileExpected, "Current profile is dev");
    }

    @Test
    void testProductionProfile() {
        ResponseEntity<String> forEntity = testRestTemplate.getForEntity("http://localhost:" + genericContainerProduction.getMappedPort(8081) + "/profile" , String.class);
        String currentProfileExpected = forEntity.getBody();
        assertEquals(currentProfileExpected, "Current profile is production");
    }
}
