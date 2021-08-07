package com.fanxuankai.boot.oauth2.core.condition;

import cn.hutool.core.util.StrUtil;
import com.fanxuankai.boot.oauth2.core.autoconfigure.Oauth2Properties;
import com.fanxuankai.boot.oauth2.core.enums.TokenStoreType;
import org.springframework.boot.autoconfigure.condition.ConditionMessage;
import org.springframework.boot.autoconfigure.condition.ConditionOutcome;
import org.springframework.boot.autoconfigure.condition.SpringBootCondition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * @author fanxuankai
 */
public class RedisCondition extends SpringBootCondition {
    @Override
    public ConditionOutcome getMatchOutcome(ConditionContext context, AnnotatedTypeMetadata metadata) {
        ConditionMessage.Builder message = ConditionMessage.forCondition("Token Store Type Jdbc Condition");
        Environment environment = context.getEnvironment();
        String keyValue = environment.getProperty(Oauth2Properties.TOKEN_STORE_TYPE);
        if (StrUtil.equals(keyValue, TokenStoreType.REDIS.name(), true)) {
            return ConditionOutcome.match(message.foundExactly("provided private or symmetric key"));
        }
        return ConditionOutcome.noMatch(message.didNotFind("provided private or symmetric key").atAll());
    }
}