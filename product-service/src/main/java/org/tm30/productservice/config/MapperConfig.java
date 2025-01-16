package org.tm30.productservice.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }

//    @Bean
//    public LoanService loanService(LoanRepository loanRepository, ModelMapper modelMapper, WebClient webClient, DiscoveryClient discoveryClient){
//        return new LoanServiceImpl(loanRepository, modelMapper, webClient, discoveryClient);
//    }
}
