package com.PriceMonitoring.DataService.util;

import com.PriceMonitoring.DataService.dtos.GoldApiResponseDto;
import com.PriceMonitoring.DataService.dtos.SilverApiResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

@Component
public class FetchApiData {
    @Autowired
    private WebClient.Builder webClientBuilder;

    @Value("${API_KEY}")
    private String key;

    public Mono<?> getMarketData(String metal, String city) {
        if (metal.equalsIgnoreCase("gold")) {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url = "https://indian-gold-and-silver-price.p.rapidapi.com/gold" + "?city=" + encodedCity;
//        System.out.println(url);
            return webClientBuilder.build()
                    .get()
                    .uri(url)
                    .header("x-rapidapi-key", key)
                    .header("x-rapidapi-host", "indian-gold-and-silver-price.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(GoldApiResponseDto.class);
        } else {
            String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
            String url = "https://indian-gold-and-silver-price.p.rapidapi.com/silver" + "?city=" + encodedCity;
//        System.out.println(url);
            return webClientBuilder.build()
                    .get()
                    .uri(url)
                    .header("x-rapidapi-key", key)
                    .header("x-rapidapi-host", "indian-gold-and-silver-price.p.rapidapi.com")
                    .header("Content-Type", "application/json")
                    .retrieve()
                    .bodyToMono(SilverApiResponseDto.class);
        }
    }
}

//    public Mono<GoldApiResponseDto> getGoldPrice(String city) {
//        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
//        String url = "https://indian-gold-and-silver-price.p.rapidapi.com/gold" + "?city=" + encodedCity;
////        System.out.println(url);
//        return webClientBuilder.build()
//                .get()
//                .uri(url)
//                .header("x-rapidapi-key", key)
//                .header("x-rapidapi-host", "indian-gold-and-silver-price.p.rapidapi.com")
//                .header("Content-Type", "application/json")
//                .retrieve()
//                .bodyToMono(GoldApiResponseDto.class);
//    }
//
//    public Mono<SilverApiResponseDto> getSilverPrice(String city) {
//        String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8);
//        String url = "https://indian-gold-and-silver-price.p.rapidapi.com/silver" + "?city=" + encodedCity;
////        System.out.println(url);
//        return webClientBuilder.build()
//                .get()
//                .uri(url)
//                .header("x-rapidapi-key", key)
//                .header("x-rapidapi-host", "indian-gold-and-silver-price.p.rapidapi.com")
//                .header("Content-Type", "application/json")
//                .retrieve()
//                .bodyToMono(SilverApiResponseDto.class);
//    }
//}
