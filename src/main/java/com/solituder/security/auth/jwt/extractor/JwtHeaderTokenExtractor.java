package com.solituder.security.auth.jwt.extractor;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.stereotype.Component;

/**
 * An implementation of {@link TokenExtractor} extracts token  from
 * Authorization: Bearer scheme.
 * 
 * @author vladimir.stankovic
 *JwtHeaderTokenExtractor 是一个非常简单的类，通常用来扩展来处理身份检验的处理。
  你可以扩展TokenExtractor 接口 和 提供你常用的一些实现。例如从URL中提取标记。
 * Aug 5, 2016
 */
@Component
public class JwtHeaderTokenExtractor implements TokenExtractor {
    public final static String HEADER_PREFIX = "Bearer ";

    @Override
    public String extract(String header) {
        if (StringUtils.isBlank(header)) {
            throw new AuthenticationServiceException("Authorization header cannot be blank!");
        }

        if (header.length() < HEADER_PREFIX.length()) {
            throw new AuthenticationServiceException("Invalid authorization header size.");
        }

        return header.substring(HEADER_PREFIX.length(), header.length()); // sos 注意 这里 用 substring 处理掉 Bearer
    }
}
