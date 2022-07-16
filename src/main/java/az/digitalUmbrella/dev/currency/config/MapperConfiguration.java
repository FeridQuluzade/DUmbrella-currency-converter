package az.digitalUmbrella.dev.currency.config;

import az.digitalUmbrella.dev.currency.mapper.CurrencyMapper;
import az.digitalUmbrella.dev.currency.mapper.UserMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfiguration {

    @Bean
    public UserMapper userMapper() {
        return UserMapper.INSTANCE;
    }

    @Bean
    public CurrencyMapper exchangeMapper() {
        return CurrencyMapper.INSTANCE;
    }

}
