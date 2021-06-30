package com.fanxuankai.boot.authorization.endpoint;

import cn.hutool.core.util.StrUtil;
import com.fanxuankai.commons.domain.Result;
import com.fanxuankai.commons.util.ResultUtils;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.endpoint.FrameworkEndpoint;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @author fanxuankai
 */
@FrameworkEndpoint
public class LogoutEndpoint {
    @Resource
    private TokenStore tokenStore;

    @PostMapping("/oauth/logout")
    @ResponseBody
    public Result<Void> logout(@RequestParam("access_token") String accessToken,
                               @RequestParam("refresh_token") String refreshToken) {
        OAuth2AccessToken oAuth2AccessToken;
        if (StrUtil.isNotBlank(accessToken)
                && (oAuth2AccessToken = tokenStore.readAccessToken(accessToken)) != null) {
            tokenStore.removeAccessToken(oAuth2AccessToken);
        }
        OAuth2RefreshToken oAuth2RefreshToken;
        if (StrUtil.isNotBlank(refreshToken)
                && (oAuth2RefreshToken = tokenStore.readRefreshToken(refreshToken)) != null) {
            tokenStore.removeRefreshToken(oAuth2RefreshToken);
        }
        return ResultUtils.ok();
    }
}