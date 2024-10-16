package com.example.eng.config.request;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 请求参数手动验证
 *
 * @author yangxueyong
 * @date 2021/06/02
 */
@Slf4j
@ControllerAdvice
public class DecodeRequestBodyAdvice implements RequestBodyAdvice {


    @Override
    public boolean supports(MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return true;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }


    /**
     * 将json数据解析为泛型实体对象
     *
     * @param json  json
     * @param clazz clazz
     * @return {@link T}
     */
    private static <T> T parseResultV3(String json, Class<T> clazz) {
        return JSONObject.parseObject(json, clazz);
    }

    @SneakyThrows
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) throws IOException {
        InputStream body1 = inputMessage.getBody();
        HttpHeaders headers = inputMessage.getHeaders();
        String bodyStr = IOUtils.toString(body1, "UTF-8");
        try {
//            Class<?> parameterType = methodParameter.getParameterType();
//            Class<Q> qClass = Q.class;
//            if (methodParameter.getMethod().isAnnotationPresent(CustomValidation.class)) {
//                if (parameterType == qClass) {
//                    Class actualTypeArgument = (Class) ((ParameterizedType) type).getActualTypeArguments()[0];
//                    Q q = parseResultV2(bodyStr, actualTypeArgument);
//                    Object body = q.getBody();
//                    validator(body);
//                } else {
//                    Object o = parseResultV3(bodyStr, parameterType);
//                    validator(o);
//                }
//            }
        } catch (Exception e) {
            log.error("beforeBodyRead报错 :【" + methodParameter.getMethod().getName() + "】 ：" + e.getMessage());
            throw e;
        }
        return new MyHttpInputMessage(bodyStr,headers);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage httpInputMessage, MethodParameter methodParameter, Type type, Class<? extends HttpMessageConverter<?>> aClass) {
        return body;
    }

    class MyHttpInputMessage implements HttpInputMessage {
        private HttpHeaders headers;

        private InputStream body;

        public MyHttpInputMessage(String body,HttpHeaders headers) throws Exception {
            this.headers = headers;
            this.body = IOUtils.toInputStream(body, "UTF-8");
        }

        @Override
        public InputStream getBody() throws IOException {
            return body;
        }

        @Override
        public HttpHeaders getHeaders() {
            return headers;
        }

        /**
         * @param requestData
         * @return
         */
        public String easpString(String requestData) {
            return requestData;
        }
    }
}