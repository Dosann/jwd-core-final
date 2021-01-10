package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static void start() throws InvalidStateException {

        final Supplier<ApplicationContext> applicationContextSupplier = NassaContext::getInstance;
        final NassaContext nassaContext = new NassaContext();
        ApplicationMenu applicationMenu = applicationContextSupplier::get;

        nassaContext.init();
        applicationMenu.printAvailableOptions();
    }
}
