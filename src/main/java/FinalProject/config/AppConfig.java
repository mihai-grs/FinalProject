package FinalProject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Value("${app.language:some default}")
    private String language;

    public String getLanguage() {
        return language;
    }
}