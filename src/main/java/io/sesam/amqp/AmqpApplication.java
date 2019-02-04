package io.sesam.amqp;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Base64;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AmqpApplication {
    public static void main(String[] args) throws IOException {
        String clientCertificate = System.getenv("client_certificate");
        String clientCertificatePassword = System.getenv("client_certificate_password");
        if (null != clientCertificate && null != clientCertificatePassword) {
            byte[] base64decodedBytes = Base64.getDecoder().decode(clientCertificate);
            Files.write(Paths.get("keystore.p12"), base64decodedBytes, StandardOpenOption.CREATE_NEW);
            System.setProperty("javax.net.ssl.keyStore", "keystore.p12");
            System.setProperty("javax.net.ssl.keyStorePassword", clientCertificatePassword);
            System.setProperty("javax.net.ssl.keyStoreType", "pkcs12");
        }
        SpringApplication.run(AmqpApplication.class, args);
    }

}
