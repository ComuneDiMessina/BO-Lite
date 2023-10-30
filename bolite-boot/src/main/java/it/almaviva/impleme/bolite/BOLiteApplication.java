package it.almaviva.impleme.bolite;

import it.almaviva.eai.ljsa.sdk.core.bootstrap.EnableLjsa;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@EnableFeignClients
@EnableLjsa
@SpringBootApplication
public class BOLiteApplication {
  public static void main(final String[] args) {
    SpringApplication.run(BOLiteApplication.class, args);
  }
}