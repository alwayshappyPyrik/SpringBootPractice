package com.example.springbootpractise;

import com.example.springbootpractise.config.JavaConfig;
import com.example.springbootpractise.profile.DevProfile;
import com.example.springbootpractise.profile.SystemProfile;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.runner.ApplicationContextRunner;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class SpringBootPractiseApplicationTests {
    private final ApplicationContextRunner contextRunner = new ApplicationContextRunner();

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

}
