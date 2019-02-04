package io.sesam.amqp;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 *
 * @author 100tsa
 */
@ConfigurationProperties
@Component
public class AppConfig {

    @Value("${url}")
    private String url;
    
    public String getUrl() {
        return url;
    }
}
