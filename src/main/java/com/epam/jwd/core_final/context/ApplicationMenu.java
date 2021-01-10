package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.SubApplicationMenu;
import com.epam.jwd.core_final.util.ConsoleSample;

import java.util.Scanner;

// todo replace Object with your own types
@FunctionalInterface
public interface ApplicationMenu {

    ApplicationContext getApplicationContext();

    default void printAvailableOptions() {
        Scanner scanner = new Scanner(System.in);
        Integer command;
        do {
            ConsoleSample.printMajorOptionsToChooseFrom();
            command = scanner.nextInt();
            handleUserInput(command);
        }
        while (!command.equals(0));
        System.out.println("Application is finished. Bye!");
        System.exit(0);
    }

    default void handleUserInput(Integer command) {
        if (command == 0) {
            System.out.println("Application is finished. Bye!");
            System.exit(0);
        } else if (command >= 1 && command <=4) {
            SubApplicationMenu.getInstance().showFunctionalDetailsToUser(command);
        } else {
            throw new IllegalArgumentException("Invalid command");
        }
    }
}
