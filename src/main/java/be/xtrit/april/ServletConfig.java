package be.xtrit.april;

import org.mitre.dsmiley.httpproxy.ProxyServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ServletConfig {
    @Value("${proxy.port}")
    private int proxyPort;

    @Autowired
    private MasterServlet masterServlet;

    @Bean
    public ServletRegistrationBean servletRegistrationBean() {
        ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean(masterServlet, "/*");
        servletRegistrationBean.addInitParameter("targetUri", "http://localhost:" + proxyPort);
        servletRegistrationBean.addInitParameter(ProxyServlet.P_LOG, "true");
        return servletRegistrationBean;
    }

    @Bean
    public ProxyServlet proxyServlet() {
        return new ProxyServlet();
    }
}
