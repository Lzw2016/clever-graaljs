package org.clever.graaljs.spring.core.utils.spring;

import lombok.extern.slf4j.Slf4j;
import org.clever.graaljs.core.utils.ExceptionUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;

import javax.servlet.ServletContext;

/**
 * 获取Spring ApplicationContext容器的类<br/>
 * 1.以静态变量保存Spring ApplicationContext<br/>
 * 2.可在任何代码任何地方任何时候取出ApplicaitonContext<br/>
 * 3.提供获取Spring容器中的Bean的方法<br/>
 * <p>
 * 作者：LiZW <br/>
 * 创建时间：2016-5-9 14:25 <br/>
 */
@Slf4j
public class SpringContextHolder implements ApplicationContextAware, DisposableBean {

    /**
     * Spring ApplicationContext容器
     */
    private static ApplicationContext applicationContext = null;

    /**
     * Servlet容器的上下文
     */
    private static ServletContext servletContext = null;

    @SuppressWarnings("NullableProblems")
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        log.info("初始化ApplicationContext: {}", applicationContext);
        SpringContextHolder.applicationContext = applicationContext;
    }

    @Override
    public void destroy() {
        applicationContext = null;
    }

    /**
     * 获取Spring容器applicationContext对象
     */
    public static ApplicationContext getApplicationContext() {
        return getApplicationContext(true);
    }

    /**
     * 获取Spring容器applicationContext对象
     *
     * @param require 是否要求一定要获取到
     */
    @SuppressWarnings("BusyWait")
    public static ApplicationContext getApplicationContext(boolean require) {
        while (applicationContext == null && require) {
            log.info("等待Spring Context初始化成功...");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignore) {
            }
        }
        return applicationContext;
    }

    /**
     * 获取系统根目录
     */
    public static String getRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = getApplicationContext().getResource("").getFile().getAbsolutePath();
        } catch (Exception e) {
            log.warn("获取系统根目录失败", e);
        }
        return rootRealPath;
    }

    /**
     * 获取资源根目录
     */
    public static String getResourceRootRealPath() {
        String rootRealPath = "";
        try {
            rootRealPath = new DefaultResourceLoader().getResource("").getFile().getAbsolutePath();
        } catch (Exception e) {
            log.warn("获取资源根目录失败", e);
        }
        return rootRealPath;
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param name Bean名称
     * @return 返回Bean对象
     */
    public static <T> T getBean(String name) {
        return getBean(true, name);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param require 是否等待获取ApplicationContext
     * @param name    Bean名称
     * @return 返回Bean对象
     */
    @SuppressWarnings("unchecked")
    public static <T> T getBean(boolean require, String name) {
        try {
            return (T) getApplicationContext(require).getBean(name);
        } catch (Exception e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param requiredType Bean类型
     * @return 返回Bean对象
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getBean(true, requiredType);
    }

    /**
     * 从静态变量applicationContext中取得Bean, 自动转型为所赋值对象的类型.
     *
     * @param require      是否等待获取ApplicationContext
     * @param requiredType Bean类型
     * @return 返回Bean对象
     */
    public static <T> T getBean(boolean require, Class<T> requiredType) {
        try {
            return getApplicationContext(require).getBean(requiredType);
        } catch (Exception e) {
            throw ExceptionUtils.unchecked(e);
        }
    }

    /**
     * @return Servlet容器的上下文
     */
    private static ServletContext getServletContext() {
        if (servletContext == null) {
            WebApplicationContext webApplicationContext = ContextLoader.getCurrentWebApplicationContext();
            assert webApplicationContext != null;
            servletContext = webApplicationContext.getServletContext();
        }
        return servletContext;
    }
}