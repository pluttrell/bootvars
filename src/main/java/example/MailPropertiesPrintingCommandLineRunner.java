package example;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.mail.MailProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@ConditionalOnProperty(prefix = "spring.mail", name = "host")
public class MailPropertiesPrintingCommandLineRunner implements CommandLineRunner {

  private final ApplicationContext applicationContext;

  public MailPropertiesPrintingCommandLineRunner(ApplicationContext applicationContext) {
    this.applicationContext = applicationContext;
  }

  @Override
  public void run(String... args) throws Exception {

    MailProperties mailProperties = applicationContext.getBean(MailProperties.class);

    System.out.println("SpringBoot's MailProperties:");
    System.out.println("---------------------------------------------------------------------------------------------");

    mailProperties.getProperties().entrySet()
        .forEach(System.out::println);

    System.out.println("---------------------------------------------------------------------------------------------");

  }

}
