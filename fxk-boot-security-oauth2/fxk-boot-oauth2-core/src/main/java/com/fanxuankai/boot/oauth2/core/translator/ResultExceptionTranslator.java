package com.fanxuankai.boot.oauth2.core.translator;

import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.util.ResultUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

/**
 * 自定义登录或者鉴权失败时的返回信息
 *
 * @author fanxuankai
 */
public class ResultExceptionTranslator implements WebResponseExceptionTranslator<Result<Void>> {
    private final DefaultWebResponseExceptionTranslator translator = new DefaultWebResponseExceptionTranslator();

    @Override
    public ResponseEntity<Result<Void>> translate(Exception e) throws Exception {
        ResponseEntity<OAuth2Exception> responseEntity = translator.translate(e);
        HttpHeaders headers = new HttpHeaders();
        headers.setAll(responseEntity.getHeaders().toSingleValueMap());
        return new ResponseEntity<>(ResultUtils.fail(responseEntity.getStatusCodeValue(), e.getMessage()),
                headers, responseEntity.getStatusCode());
    }
}