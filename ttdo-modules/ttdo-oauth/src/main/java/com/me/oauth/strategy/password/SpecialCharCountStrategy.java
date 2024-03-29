package com.me.oauth.strategy.password;

import com.me.oauth.domain.entity.BaseUser;
import com.me.oauth.policy.PasswordPolicyMap;
import com.me.oauth.policy.PasswordPolicyType;
import com.me.oauth.strategy.PasswordStrategy;
import com.me.core.exception.CommonException;
import org.springframework.stereotype.Component;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author wuguokai
 */
@Component
public class SpecialCharCountStrategy implements PasswordStrategy {
    private static final String ERROR_MESSAGE = "hoth.warn.password.policy.specialChar";
    public static final String TYPE = PasswordPolicyType.SPECIALCHAR_COUNT.getValue();
    /**
     * The regex expression to validate special characters. It actually includes 24 characters:
     *  ~`@#$%^&*-_=+|/()<>,.;:!
     */
    private static final String SPECIAL_REGEX = "[~`@#$%^&*\\-_=+|/()<>,.;:!]";
    private static final Pattern PATTERN = Pattern.compile(SPECIAL_REGEX);

    @Override
    public Object validate(PasswordPolicyMap policyMap, BaseUser user, String password) {
        Integer min = (Integer) policyMap.getPasswordConfig().get(TYPE);
        if (min != null && min != 0) {
            int count = 0;

            Matcher m = PATTERN.matcher(password);

            while (m.find()){
                count++;
            }

            if (count < min) {
                throw new CommonException(ERROR_MESSAGE, min);
            }
        }
        return null;
    }

    @Override
    public String getType() {
        return TYPE;
    }

    @Override
    public Object parseConfig(Object value) {
        return null;
    }
}
