//package org.clever.graaljs.freemarker.support;
//
//import freemarker.cache.StringTemplateLoader;
//import freemarker.template.Configuration;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.BeansException;
//import org.springframework.context.ApplicationContext;
//import org.springframework.context.ApplicationContextAware;
//import org.springframework.stereotype.Component;
//
///**
// * 作者：lizw <br/>
// * 创建时间：2020/10/04 22:17 <br/>
// */
//@Slf4j
//public class FreeMarkerConfigurer implements ApplicationContextAware {
//    /**
//     * FreeMarker容器
//     */
//    private static Configuration CONFIG;
//
//    public static Configuration getConfig() {
//        return CONFIG;
//    }
//
//    @SuppressWarnings("NullableProblems")
//    @Override
//    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        try {
//            CONFIG = applicationContext.getBean(Configuration.class);
//        } catch (Exception e) {
//            log.warn("ApplicationContext.getBean(Configuration.class)失败: {}", e.getMessage());
//            CONFIG = createConfiguration();
//        }
//    }
//
//    private static Configuration createConfiguration() {
//        Configuration configuration = new Configuration(Configuration.VERSION_2_3_30);
//        StringTemplateLoader stringLoader = new StringTemplateLoader();
//        configuration.setTemplateLoader(stringLoader);
//        // FreeMarker 模版数据null值处理，不抛异常
//        configuration.setClassicCompatible(true);
//        return configuration;
//    }
//}
