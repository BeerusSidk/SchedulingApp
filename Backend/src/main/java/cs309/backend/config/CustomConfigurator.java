package cs309.backend.config;

import javax.websocket.server.ServerEndpointConfig;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class CustomConfigurator extends ServerEndpointConfig.Configurator implements ApplicationContextAware {

    private static volatile ApplicationContext context;

    @Override
    public <T> T getEndpointInstance(Class<T> endpointClass) throws InstantiationException {
        return context.getBean(endpointClass);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        CustomConfigurator.context = applicationContext;
    }
}
