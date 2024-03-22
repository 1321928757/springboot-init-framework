package com.luckysj.springbootinit.config;

import com.luckysj.springbootinit.common.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

/**
 * webMvc的配置类
 */
@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    /**
     * 扩展Mvc框架的消息转换器（将Long型数据转为字串符，解决精度丢失的问题）
     * @param converters
     */
    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        //创建消息转换器
        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        //设置对象转换器
        messageConverter.setObjectMapper(new JacksonObjectMapper());
        //将自己设置的转换器加到Mvc框架的转换器容器中
        converters.add(0, messageConverter);
    }

    /**
     * 运行跨域
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 所有接口
                .allowCredentials(true) // 是否发送 Cookie
                .allowedOriginPatterns("http://www.luckysj.top") // 支持域
                // .allowedOriginPatterns("http://127.0.0.1:5173") // 支持域
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTION") // 支持方法
                .allowedHeaders("*")
                .exposedHeaders("*");
    }
}
