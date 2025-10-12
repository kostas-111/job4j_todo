package ru.job4j.todo.configuration;

import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class HibernateConfiguration {
    /**
     * Создаём SessionFactory как Spring Bean.
     * Теперь его можно внедрять (@Autowired) в другие компоненты.
     */
    @Bean(destroyMethod = "close")
    public SessionFactory sessionFactory() {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")  // Загрузка конфигурации из XML
                .build();
        try {
            return new MetadataSources(registry)
                    .buildMetadata()
                    .buildSessionFactory();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            throw new IllegalStateException("Ошибка инициализации SessionFactory", e);
        }
    }
}
