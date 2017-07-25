package example;

import java.util.Properties;
import org.springframework.boot.CommandLineRunner;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.stereotype.Component;

@Component
public class JavaMailConfigPrintingCommandLineRunner implements CommandLineRunner {

  private final JavaMailSenderImpl javaMailSender;

  public JavaMailConfigPrintingCommandLineRunner(JavaMailSenderImpl javaMailSender) {
    this.javaMailSender = javaMailSender;
  }

  @Override
  public void run(String... args) throws Exception {

    Properties javaMailProperties = javaMailSender.getJavaMailProperties();

    if (javaMailProperties == null || javaMailProperties.isEmpty()) {

      System.out.println("SpringBoot's javaMailSender contained NO javaMailProperties.");

    } else {

      System.out.println("SpringBoot's javaMailSender.javaMailProperties:");
      System.out.println("---------------------------------------------------------------------------------------------");
      javaMailSender.getJavaMailProperties().entrySet()
          .forEach(System.out::println);
      System.out.println("---------------------------------------------------------------------------------------------");
    }

  }

}
