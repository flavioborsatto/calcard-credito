package br.com.calcard.credito.configuration;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.UriTemplate;

import io.swagger.annotations.Api;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.DocumentationCache;
import springfox.documentation.spring.web.PropertySourcedMapping;
import springfox.documentation.spring.web.json.JsonSerializer;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiResourceController;
import springfox.documentation.swagger2.annotations.EnableSwagger2;
import springfox.documentation.swagger2.mappers.ServiceModelToSwagger2Mapper;
import springfox.documentation.swagger2.web.Swagger2Controller;

@Configuration
@EnableSwagger2
public class SwaggerConfig implements WebMvcConfigurer {

    @Bean
    public Docket swaggerDocket() {
        return new Docket(DocumentationType.SWAGGER_2)
            .select()
            .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
            .build()
            .pathMapping("");
    }

    @Bean
    public HandlerMapping swagger2ControllerProxyMapping(
        Environment environment,
        DocumentationCache documentationCache,
        ServiceModelToSwagger2Mapper mapper,
        JsonSerializer jsonSerializer) {

        return new Swagger2ControllerRequestMappingHandlerMapping(
            environment,
            new Swagger2Controller(environment, documentationCache, mapper, jsonSerializer),
            "/docs");
    }

    @Bean
    public HandlerMapping apiResourceControllerProxyMapping(
        ApiResourceController apiResourceController) {

        return new ProxiedControllerRequestMappingHandlerMapping(
            apiResourceController,
            "/docs");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.
            addResourceHandler("/docs/swagger-ui.html**")
            .addResourceLocations("classpath:/META-INF/resources/swagger-ui.html");
      
        registry.
            addResourceHandler("/docs/webjars/**")
            .addResourceLocations("classpath:/META-INF/resources/webjars/");
    }
    
    
    public static class ProxiedControllerRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
        protected final Map<String, HandlerMethod> handlerMethods = new LinkedHashMap<String, HandlerMethod>();
        protected final Object handler;
        protected final String basePath;

        public ProxiedControllerRequestMappingHandlerMapping(
            Object handler,
            String basePath) {
            this.handler = handler;
            this.basePath = basePath;
        }

        @Override
        protected void initHandlerMethods() {

            logger.debug("initialising the handler methods");
            setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
            Class<?> clazz = handler.getClass();
            if (isHandler(clazz)) {
                RequestMappingInfo classMapping = createRequestMappingInfo(clazz);

                for (Method method : clazz.getMethods()) {
                    if (isValidMethod(method)) {
                        RequestMappingInfo methodMapping = getMappingForMethod(method, clazz);

                       if (methodMapping == null && classMapping == null) {
                            logger.warn(String.format(
                                "Cannot map any URL path onto method [%s] for class [%s] "
                                + "as no [@RequestMapping] found for the class and the method",
                                method.getName(),
                                clazz.getName()));
                            continue;
                        }

                        RequestMappingInfo mapping = methodMapping != null ? methodMapping : classMapping;
                        HandlerMethod handlerMethod = createHandlerMethod(handler, method);
                        doMapping(clazz, method, mapping, handlerMethod);
                    }
                }
            }
        }

        protected void doMapping(
            Class<?> clazz,
            Method method,
            RequestMappingInfo mapping,
            HandlerMethod handlerMethod) {

            for (String path : mapping.getPatternsCondition().getPatterns()) {
                path = basePath + path;

                logger.info(String.format(
                    "Mapped URL path [%s] onto method [%s]",
                    path,
                    handlerMethod.toString()));
                handlerMethods.put(path, handlerMethod);
            }
        }

        @Override
        protected boolean isHandler(Class<?> beanType) {

            return ((AnnotationUtils.findAnnotation(beanType, Controller.class) != null) ||
                (AnnotationUtils.findAnnotation(beanType, RequestMapping.class) != null));
        }

        @Override
        protected HandlerMethod lookupHandlerMethod(
            String urlPath,
            HttpServletRequest request) throws Exception {

            logger.debug("looking up handler for path: " + urlPath);
            HandlerMethod handlerMethod = handlerMethods.get(urlPath);
            if (handlerMethod != null) {
                return handlerMethod;
            }
            for (String path : handlerMethods.keySet()) {
                UriTemplate template = new UriTemplate(path);
                if (template.matches(urlPath)) {
                    request.setAttribute(
                        HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE,
                        template.match(urlPath));
                    return handlerMethods.get(path);
                }
            }
            return null;
        }

        private RequestMappingInfo createRequestMappingInfo(AnnotatedElement element) {

            RequestMapping requestMapping = AnnotatedElementUtils.findMergedAnnotation(element, RequestMapping.class);
            RequestCondition<?> condition = (element instanceof Class ?
                getCustomTypeCondition((Class<?>) element) : getCustomMethodCondition((Method) element));
            return (requestMapping != null ? createRequestMappingInfo(requestMapping, condition) : null);
        }

        private boolean isValidMethod(Method method) {

            return (AnnotationUtils.findAnnotation(method, RequestMapping.class) != null)
                && Modifier.isPublic(method.getModifiers());
        }
    }
    
    public class Swagger2ControllerRequestMappingHandlerMapping extends ProxiedControllerRequestMappingHandlerMapping {
        private final Environment environment;

        public Swagger2ControllerRequestMappingHandlerMapping(
            Environment environment,
            Object handler,
            String basePath) {

            super(handler, basePath);
            this.environment = environment;
        }

        @Override
        protected void doMapping(
            Class<?> clazz,
            Method method,
            RequestMappingInfo mapping,
            HandlerMethod handlerMethod) {

            PropertySourcedMapping mapper = AnnotationUtils.getAnnotation(method, PropertySourcedMapping.class);

            if (mapper != null) {
                String mappingPath = mappingPath(mapper);
                if (mappingPath != null) {
                    mappingPath = basePath + mappingPath;

                    logger.info(String.format(
                        "Mapped URL path [%s] onto method [%s]",
                        mappingPath,
                        handlerMethod.toString()));
                    handlerMethods.put(mappingPath, handlerMethod);
                } else {
                    super.doMapping(clazz, method, mapping, handlerMethod);
                }
            }
        }

        private String mappingPath(final PropertySourcedMapping mapper) {

            final String key = mapper.propertyKey();
            return Optional.ofNullable(environment.getProperty(key))
                .map(input -> input.replace(String.format("${%s}", key), input))
                .orElse(null);
        }
    }
}