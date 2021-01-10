package com.epam.jwd.core_final.context.impl;

import com.epam.jwd.core_final.context.ApplicationContext;
import com.epam.jwd.core_final.context.ApplicationMenu;
import com.epam.jwd.core_final.util.ConsoleSample;
import com.epam.jwd.core_final.util.handlers.EntityCreationHandler;
import com.epam.jwd.core_final.util.handlers.EntityUpdatingHandler;
import com.epam.jwd.core_final.util.handlers.EntityViewHandler;
import com.epam.jwd.core_final.util.handlers.WriteJsonHandler;


import java.util.Scanner;

public class SubApplicationMenu implements ApplicationMenu {

    private static class SubApplicationMenuHolder {
        private final static SubApplicationMenu INSTANCE = new SubApplicationMenu();
    }

    public static SubApplicationMenu getInstance() {
        return SubApplicationMenu.SubApplicationMenuHolder.INSTANCE;
    }

    @Override
    public ApplicationContext getApplicationContext() {
        return NassaContext.getInstance();
    }

    public void showFunctionalDetailsToUser(Integer command) {
        if (command != 4) {
            ConsoleSample.printEntitiesToChoseFrom();
        }
        handleUserOptionalInput(command);
    }

    private static void handleUserOptionalInput(Integer command) {
        Scanner scanner = new Scanner(System.in);

        switch (command) {
            case 1:
                EntityCreationHandler.handleEntityCreation(scanner.nextInt());
                break;
            case 2:
                EntityUpdatingHandler.handleEntityUpdating(scanner.nextInt());
                break;
            case 3:
                EntityViewHandler.handleEntityView(scanner.nextInt());
                break;
            case 4:
                WriteJsonHandler.handleWritingInJson();
                break;
            default:
                throw new IllegalArgumentException("Invalid command");
        }
    }
}
