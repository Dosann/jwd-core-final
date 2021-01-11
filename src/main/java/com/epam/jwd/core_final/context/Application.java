package com.epam.jwd.core_final.context;

import com.epam.jwd.core_final.context.impl.NassaContext;
import com.epam.jwd.core_final.context.impl.SubApplicationMenu;
import com.epam.jwd.core_final.exception.InvalidStateException;

import java.util.function.Supplier;

public interface Application {

    static ApplicationMenu start() throws InvalidStateException {
        final ApplicationMenu applicationMenu = SubApplicationMenu.getInstance();
        final Supplier<ApplicationContext> applicationContextSupplier = applicationMenu::getApplicationContext;
        final NassaContext nassaContext = new NassaContext();

        nassaContext.init();
        return applicationContextSupplier::get;
    }
}
