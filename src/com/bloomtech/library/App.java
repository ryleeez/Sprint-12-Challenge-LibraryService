package com.bloomtech.library;

import com.bloomtech.library.controllers.CheckableController;
import com.bloomtech.library.controllers.LibraryController;
import com.bloomtech.library.datastore.Datastore;
import com.bloomtech.library.models.Patron;
import com.bloomtech.library.models.checkableTypes.Checkable;
import com.bloomtech.library.repositories.CheckableRepository;
import com.bloomtech.library.repositories.LibraryCardRepository;
import com.bloomtech.library.repositories.LibraryRepository;
import com.bloomtech.library.repositories.PatronRepository;
import com.bloomtech.library.services.CheckableService;
import com.bloomtech.library.services.LibraryCardService;
import com.bloomtech.library.services.LibraryService;
import com.bloomtech.library.services.PatronService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {

    //TODO: Implement DI and remove fields
    public static Datastore datastore;
    public static SeedData seedData;

    public static LibraryController libraryController;
    public static CheckableController checkableController;
    public static LibraryService libraryService;
    public static CheckableService checkableService;
    public static LibraryCardService libraryCardService;
    public static PatronService patronService;
    public static LibraryRepository libraryRepository;
    public static CheckableRepository checkableRepository;
    public static LibraryCardRepository libraryCardRepository;
    public static PatronRepository patronRepository;

    public static void main(String[] args) {

        //TODO: Remove Dependency Wiring
        datastore = new Datastore();

        libraryRepository = new LibraryRepository(datastore);
        checkableRepository = new CheckableRepository(datastore);
        libraryCardRepository = new LibraryCardRepository(datastore);
        patronRepository = new PatronRepository(datastore);

        libraryService = new LibraryService(libraryRepository);
        checkableService = new CheckableService(checkableRepository);
        libraryCardService = new LibraryCardService(libraryCardRepository);
        patronService = new PatronService(patronRepository);

        libraryController = new LibraryController(libraryService);
        checkableController = new CheckableController(checkableService);

        seedData = new SeedData(libraryService, patronService, libraryCardService, checkableService);
        seedData.run();
        //

        SpringApplication.run(App.class, args);
    }
}
