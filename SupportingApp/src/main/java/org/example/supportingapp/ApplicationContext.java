package org.example.supportingapp;

import org.example.supportingapp.configuration.Configuration;
import org.example.supportingapp.configuration.Instance;
import org.reflections.Reflections;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ApplicationContext {
    private Map<Class<?>, Object> instances = new HashMap<>();
    public ApplicationContext() throws InvocationTargetException, IllegalAccessException {
        Reflections reflections = new Reflections("org.example.supportingapp.configuration");
        // получаем список объектов конфигураций
        var configurations = reflections.getTypesAnnotatedWith(Configuration.class)
                .stream()
                .map(type -> {
                    try {
                        return type.getDeclaredConstructor().newInstance();
                    } catch (InstantiationException e) {
                        throw new RuntimeException(e);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    } catch (InvocationTargetException e) {
                        throw new RuntimeException(e);
                    } catch (NoSuchMethodException e) {
                        throw new RuntimeException(e);
                    }
                }).toList();
        for(Object conf : configurations){
            var methods = Arrays.stream(conf.getClass().getMethods())
                    .filter(method -> method.isAnnotationPresent(Instance.class))
                    .toList();
            for (Method method : methods) {
                instances.put(method.getReturnType(), method.invoke(conf));
            }
        }
    }

    public <T> T getInstance(Class<T> type){
        return type.cast(instances.get(type));
    }
}
