package org.clever.graaljs.spring.core.utils.validator;

import org.hibernate.validator.HibernateValidator;
import org.hibernate.validator.HibernateValidatorConfiguration;

import javax.validation.Configuration;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

/**
 * 创建各种不同的Validator实现类<br/>
 * 1.Hibernate Validator<br/>
 * <p/>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-10 22:31 <br/>
 *
 * @see org.clever.graaljs.spring.core.utils.validator.BaseValidatorUtils
 */
public class ValidatorFactoryUtils {

    /**
     * Hibernate实现的ValidatorFactory
     */
    public static final ValidatorFactory HIBERNATE_VALIDATOR_FACTORY;

    private static final Validator Instance;

    static {
        Configuration<HibernateValidatorConfiguration> configure = Validation.byProvider(HibernateValidator.class).configure();
        HIBERNATE_VALIDATOR_FACTORY = configure.buildValidatorFactory();
        Instance = getHibernateValidator();
    }

    /**
     * 返回Hibernate实现的Validator单例(验证器实例是线程安全的，可以多次重用)
     */
    public static Validator getValidatorInstance() {
        return Instance;
    }

    /**
     * 创建一个新的Hibernate实现的Validator
     *
     * @return Hibernate实现的Validator
     */
    public static Validator getHibernateValidator() {
        return HIBERNATE_VALIDATOR_FACTORY.getValidator();
    }
}
