package com.cnu.spg.config.resolver;

import com.cnu.spg.security.token.TokenProvider;
import com.cnu.spg.user.domain.User;
import com.cnu.spg.user.exception.TokenIsNotValidException;
import com.cnu.spg.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

import static com.cnu.spg.security.token.JWTProvider.CLAIM_ID_KEY;

@Component
@RequiredArgsConstructor
public class UserIdResolver implements HandlerMethodArgumentResolver {
    private final HttpServletRequest httpServletRequest;
    private final TokenProvider tokenProvider;
    private final UserService userService;

    @Value("${jwt.http.request.header}")
    private String authHeaderName;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        boolean hasAnnotation = parameter.getParameterAnnotation(UserId.class) != null;
        boolean isMatchType = parameter.getParameterType().equals(User.class);

        if (hasAnnotation && httpServletRequest.getHeader(authHeaderName) == null) {
            throw new IllegalArgumentException("UserId 정보를 찾을 수 없습니다.");
        }
        return hasAnnotation && isMatchType;
    }

    @Override
    public User resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        String authToken = webRequest.getHeader(authHeaderName);

        Map<String, Object> tokenPayload = tokenProvider.getTokenPayload(authToken);
        Object userId = tokenPayload.get(CLAIM_ID_KEY);

        if (userId == null) {
            throw new TokenIsNotValidException();
        }
        String userIdStr = (String) userId;

        return userService.findByUserId(Long.parseLong(userIdStr));
    }
}
