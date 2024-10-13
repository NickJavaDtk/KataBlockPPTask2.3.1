package spring;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import spring.config.AppConfig;
import spring.model.User;
import spring.service.UserService;



import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Arrays.stream(context.getBeanDefinitionNames()).forEach(System.out ::println);

        User user = new User("Иван", "Иванов", 20);
        User user1 = new User("Петр", "Петров", 44);
        User user2 = new User("Сидор", "Сидоров", 82);
        User user3 = new User("Павел", "Павлов", 27);
        User user4 = new User("Петр", "Петров", 36);

        UserService service = context.getBean(UserService.class);
        service.addUser(user);
        service.addUser(user1);
        service.addUser(user2);
        service.addUser(user3);
        service.addUser(user4);

        service.getUserList().stream().forEach(System.out :: println);

    }

}
