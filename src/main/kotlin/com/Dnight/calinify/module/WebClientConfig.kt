package com.dnight.calinify.ai_process.module

import io.netty.channel.ChannelOption
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.web.reactive.function.client.WebClient
import reactor.netty.http.client.HttpClient
import java.time.Duration

@Configuration
class WebClientConfig {
    /**
     * ai server(FastAPI)와 통신하는 webClient
     *
     * 15초를 기준으로 타임아웃을 설정한다.
     *
     * base url은 ai 서버의 도메인을 넣는다.
     *
     * @author 정인모
     */

    @Value("\${connect-server.ai-server.base-url}")
    private lateinit var aiServicePath: String

    @Bean
    fun aiRequestBuilder(): WebClient.Builder {
        val httpClient = HttpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 15000)
            .responseTimeout(Duration.ofMillis(15000))
        return WebClient.builder()
            .baseUrl(aiServicePath)
            .clientConnector(ReactorClientHttpConnector(httpClient))
            .defaultHeader("Content-Type", "application/json")
    }
}