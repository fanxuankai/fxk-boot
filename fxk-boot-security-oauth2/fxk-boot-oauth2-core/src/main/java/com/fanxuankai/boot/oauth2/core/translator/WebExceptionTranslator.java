package com.fanxuankai.boot.oauth2.core.translator;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;

/**
 * 自定义登录或者鉴权失败时的返回信息
 *
 * @author fanxuankai
 */
public class WebExceptionTranslator extends DefaultWebResponseExceptionTranslator {
    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
        OAuth2Exception body = responseEntity.getBody();
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(responseEntity.getHeaders().toSingleValueMap());
        return new ResponseEntity<>(body, headers, responseEntity.getStatusCode());
    }
}