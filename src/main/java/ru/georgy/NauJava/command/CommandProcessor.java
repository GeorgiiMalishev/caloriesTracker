package ru.georgy.NauJava.command;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.georgy.NauJava.service.product.ProductService;

@Component
public class CommandProcessor {
    private final ProductService productService;
    private final ActuatorCommand actuatorCommand;

    @Autowired
    public CommandProcessor(ProductService productService, ActuatorCommand actuatorCommand) {
        this.productService = productService;
        this.actuatorCommand = actuatorCommand;
    }

    public void processCommand(String input)
    {
        String[] cmd = input.split(" ");
        switch (cmd[0])
        {
            case "create" ->
            {
                productService.createProduct(Long.valueOf(cmd[1]),
                        cmd[2],
                        Integer.parseInt(cmd[3]),
                        Integer.parseInt(cmd[4]),
                        Integer.parseInt(cmd[5]),
                        Integer.parseInt(cmd[6]));
                System.out.println("Продукт успешно добавлен...");
            }

            case "delete" -> {
                productService.deleteById(Long.valueOf(cmd[1]));
                System.out.println("Продукт успешно удален...");
            }

            case "updateName" -> {
                productService.updateName(Long.valueOf(cmd[1]), cmd[2]);
                System.out.println("Название успешно обновлено...");
            }

            case "updateCalories" -> {
                productService.updateCalories(Long.valueOf(cmd[1]), Integer.parseInt(cmd[2]));
                System.out.println("Калории успешно обновлены...");
            }

            case "updateProteins" -> {
                productService.updateProteins(Long.valueOf(cmd[1]), Integer.parseInt(cmd[2]));
                System.out.println("Белки успешно обновлены...");
            }

            case "updateCarbs" -> {
                productService.updateCarbs(Long.valueOf(cmd[1]), Integer.parseInt(cmd[2]));
                System.out.println("Углеводы успешно обновлены...");
            }

            case "updateFats" -> {
                productService.updateFats(Long.valueOf(cmd[1]), Integer.parseInt(cmd[2]));
                System.out.println("Жиры успешно обновлены...");
            }

            case "print" -> System.out.println(productService.findById(Long.valueOf(cmd[1])));
            case "health" -> actuatorCommand.showHealth();
            case "info" -> actuatorCommand.showInfo();
            default -> System.out.println("Введена неизвестная команда...");
        }
    }
}
