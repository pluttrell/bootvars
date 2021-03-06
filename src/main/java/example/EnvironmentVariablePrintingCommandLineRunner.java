package example;

import java.util.Comparator;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EnvironmentVariablePrintingCommandLineRunner implements CommandLineRunner {

  @Override
  public void run(String... args) throws Exception {

    System.out.println("Environment variables visible to SpringBoot:");
    System.out.println("---------------------------------------------------------------------------------------------");

    System.getenv().entrySet().stream()
        .sorted(Comparator.comparing(entry -> entry.getKey().toLowerCase()))
        .forEach(System.out::println);

    System.out.println("---------------------------------------------------------------------------------------------");

  }

}
