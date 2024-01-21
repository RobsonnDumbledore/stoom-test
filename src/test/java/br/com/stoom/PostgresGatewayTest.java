package br.com.stoom;

import org.junit.jupiter.api.Tag;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = "br.com.stoom")
@DataJpaTest
@Tag("integrationTest")
public abstract class PostgresGatewayTest {
}
