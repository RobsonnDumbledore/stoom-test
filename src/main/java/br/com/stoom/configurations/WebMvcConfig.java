package br.com.stoom.configurations;

import org.slf4j.MDC;
import java.util.UUID;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private static final String X_REQUEST_ID = "x-request-id";

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new MdcInterceptor());
    }

    private static class MdcInterceptor implements HandlerInterceptor {

        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
                throws Exception {

            String xRequestId = MDC.get(X_REQUEST_ID);

            if (xRequestId == null) {
                xRequestId = request.getHeader(X_REQUEST_ID);
                if (xRequestId == null) {
                    xRequestId = UUID.randomUUID().toString();
                }
                response.addHeader(X_REQUEST_ID, xRequestId);
                MDC.put(X_REQUEST_ID, xRequestId);
            }

            return true;
        }

        @Override
        public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
                                    Object handler, Exception ex) throws Exception {

            MDC.remove(X_REQUEST_ID);
        }
    }
}
