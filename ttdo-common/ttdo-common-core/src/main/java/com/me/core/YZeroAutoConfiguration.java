package com.me.core;

//import com.me.core.config.CoreProperties;
import com.me.core.exception.BaseExceptionHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.validation.beanvalidation.BeanValidationPostProcessor;

/**
 * Supporter 的自动化配置
 *
 * @author jiangzhou.bo@hand-china.com 2018/06/25 16:45
 */
@Component
@EnableConfigurationProperties({
//        CoreProperties.class,
//        ServiceProperties.class
})
//@EnableAspectJAutoProxy(proxyTargetClass = true, exposeProxy = true)
@Configuration
public class YZeroAutoConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(YZeroAutoConfiguration.class);

//    private static final Pattern[] PATTERNS = new Pattern[]{
//            Pattern.compile("(\\S*)(hzero-starter-core/)([0-9.]+)((.RELEASE)|(-SNAPSHOT))(/hzero-starter-core)(\\S*)"),
//            Pattern.compile("(\\S*)(hzero-starter-core-)([0-9.]+)((.RELEASE)|(-SNAPSHOT))(.jar)(\\S*)")
//    };

    @Bean
    public BaseExceptionHandler abaseExceptionHandler() {
        return new BaseExceptionHandler();
    }

//    @Bean
//    public javax.validation.Validator localValidatorFactoryBean() {
//        return new LocalValidatorFactoryBean();
//    }

    @Bean
    public BeanPostProcessor beanValidationPostProcessor() {
        return new BeanValidationPostProcessor();
    }
//    @Bean
//    public DetailsExtractor detailsExtractor(CoreProperties properties) {
//        return new DetailsExtractor(properties);
//    }
//
//    @Bean
//    @LoadBalanced
//    public RestTemplate restTemplate() {
//        RestTemplate restTemplate = new RestTemplate();
//        restTemplate.setInterceptors(Collections.singletonList(new RequestHeaderCopyInterceptor()));
//        return restTemplate;
//    }
//
//    @Bean
//    public UserType platformUserType() {
//        return new PlatformUserType();
//    }
//
//    @Bean
//    public UserType customerUserType() {
//        return new CustomerUserType();
//    }
//
//    /**
//     * 默认从hzero-starter-core中读取版本号，作为服务元信息的版本号
//     * ps. bootstrap配置文件中的版本配置优先级更高
//     * @return
//     */
//    @Bean
//    public MetadataEntry versionMetadataEntry() {
//        ProtectionDomain pd = HZeroAutoConfiguration.class.getProtectionDomain();
//        CodeSource cs = pd.getCodeSource();
//        URL url = cs.getLocation();
//        String path = url.getPath();
//        LOGGER.info("find hzero-starter-core path:{}", path);
//
//        for (Pattern pattern : PATTERNS) {
//            Matcher matcher = pattern.matcher(path);
//            if (matcher.matches()) {
//                String version = matcher.group(3);
//                LOGGER.info("hzero-starter-core.jar was found [path={}]. metadata.VERSION = {}", path, version);
//                return new MetadataEntry(MetadataEntry.METADATA_VERSION, version);
//            }
//        }
//
//        LOGGER.info("hzero-starter-core.jar not found [path={}]. metadata.VERSION = NULL_VERSION", path);
//        return new MetadataEntry(MetadataEntry.METADATA_VERSION, "NULL_VERSION");
//    }
//
//    /**
//     * 服务元数据中的CONTEXT值，默认取server.servlet.context-path
//     * @param properties
//     * @return
//     */
//    @Bean
//    public MetadataEntry contextMetadataEntry(ServerProperties properties) {
//        return new MetadataEntry(MetadataEntry.METADATA_CONTEXT, properties.getServlet().getContextPath());
//    }
//
//    /**
//     * 自动为服务增加元数据
//     * @return
//     */
//    @Primary
//    @Bean
//    @ConditionalOnBean(AutoServiceRegistrationProperties.class)
//    @ConditionalOnProperty(value = "spring.cloud.service-registry.auto-registration.enabled", matchIfMissing = true)
//    public Registration addMetadata(@Autowired(required = false) Registration registration, @Autowired(required = false) List<MetadataEntry> metadataEntries) {
//        if (registration != null && !CollectionUtils.isEmpty(metadataEntries)) {
//            for (MetadataEntry entry : metadataEntries) {
//                registration.getMetadata().putIfAbsent(entry.getKey(), entry.getValue());
//            }
//        }
//        return registration;
//    }


//        @Bean
//        public ApplicationContextHelper applicationContextHelper() {
//            return new ApplicationContextHelper();
//        }


}
