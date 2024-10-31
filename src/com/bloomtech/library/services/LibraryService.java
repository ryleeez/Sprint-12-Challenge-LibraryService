package com.bloomtech.library.services;

import com.amazonaws.event.DeliveryMode;
import com.bloomtech.library.exceptions.LibraryNotFoundException;
import com.bloomtech.library.exceptions.ResourceExistsException;
import com.bloomtech.library.models.*;
import com.bloomtech.library.models.checkableTypes.Checkable;
import com.bloomtech.library.models.checkableTypes.Media;
import com.bloomtech.library.repositories.LibraryRepository;
import com.bloomtech.library.models.CheckableAmount;
import com.bloomtech.library.views.LibraryAvailableCheckouts;
import com.bloomtech.library.views.OverdueCheckout;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LibraryService {

    //TODO: Implement behavior described by the unit tests in tst.com.bloomtech.library.services.LibraryService

    @Autowired
    private LibraryRepository libraryRepository;

    @Autowired
    private CheckableService checkableService;

    public List<Library> getLibraries() {
        return libraryRepository.findAll();
    }

    public Library getLibraryByName(String name) {
        return libraryRepository.findByName(name)
                .orElseThrow(() -> new LibraryNotFoundException("Library not found with name: " + name));
    }


    public void save(Library library) {
        List<Library> libraries = libraryRepository.findAll();
        if (libraries.stream().filter(p->p.getName().equals(library.getName())).findFirst().isPresent()) {
            throw new ResourceExistsException("Library with name: " + library.getName() + " already exists!");
        }
        libraryRepository.save(library);
    }

    public CheckableAmount getCheckableAmount(String libraryName, String checkableIsbn) {
        Library library = getLibraryByName(libraryName);
        Checkable checkable = checkableService.getByIsbn(checkableIsbn);

        return library.getCheckables().stream()
                .filter(ca -> ca.getCheckable().getIsbn().equals(checkableIsbn))
                .findFirst()
                .orElse(new CheckableAmount(checkable, 0));
    }

    public List<LibraryAvailableCheckouts> getLibrariesWithAvailableCheckout(String isbn) {
        List<LibraryAvailableCheckouts> available = new ArrayList<>();
        Checkable checkable = checkableService.getByIsbn(isbn);
        List<Library> allLibraries = libraryRepository.findAll();

        for (Library library : allLibraries) {
            CheckableAmount amount = library.getCheckables().stream()
                    .filter(ca -> ca.getCheckable().getIsbn().equals(isbn))
                    .findFirst()
                    .orElse(new CheckableAmount(checkable, 0));

            available.add(new LibraryAvailableCheckouts(amount.getAmount(), library.getName()));
        }

        return available;
    }


    public List<OverdueCheckout> getOverdueCheckouts(String libraryName) {
        List<OverdueCheckout> overdueCheckouts = new ArrayList<>();
        Library library = getLibraryByName(libraryName);
        LocalDateTime now = LocalDateTime.now();

        for (LibraryCard card : library.getLibraryCards()) {
            for (Checkout checkout : card.getCheckouts()) {
                if (checkout.getDueDate().isBefore(now)) {
                    overdueCheckouts.add(new OverdueCheckout(card.getPatron(), checkout));
                }
            }
        }

        return overdueCheckouts;
    }
}
