package am.basic.notificator.auth.config;


import am.basic.notificator.auth.service.CrmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.remoting.caucho.HessianProxyFactoryBean;
import org.springframework.remoting.httpinvoker.HttpInvokerServiceExporter;
import org.springframework.remoting.support.RemoteExporter;
import org.springframework.security.oauth2.provider.token.TokenStore;

@Configuration
public class RemoteConfig {


    @Value("${spring.ws.crm.service.url}")
    private String crmServiceUrl;


    @Value("${spring.ws.crm.service.username}")
    private String crmServiceUsername;

    @Value("${spring.ws.crm.service.password}")
    private String crmServicePassword;

    @Autowired
    private TokenStore tokenStore;


    @Bean
    public CrmService crmService() {
        HessianProxyFactoryBean invoker = new HessianProxyFactoryBean();
        invoker.setServiceUrl(crmServiceUrl);
        invoker.setServiceInterface(CrmService.class);
        invoker.setHessian2(true);
        invoker.setUsername(crmServiceUsername);
        invoker.setPassword(crmServicePassword);
        invoker.afterPropertiesSet();
        return (CrmService) invoker.getObject();
    }


    @Bean(name = "/interconnect/tokenStore")
    public RemoteExporter tokenStoreHttp() {
        HttpInvokerServiceExporter exporter = new HttpInvokerServiceExporter();
        exporter.setService(tokenStore);
        exporter.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        exporter.setServiceInterface(TokenStore.class);
        return exporter;
    }




}
