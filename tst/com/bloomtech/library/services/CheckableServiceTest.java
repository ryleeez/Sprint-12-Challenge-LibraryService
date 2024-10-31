package com.bloomtech.library.services;

import com.bloomtech.library.exceptions.CheckableNotFoundException;
import com.bloomtech.library.exceptions.ResourceExistsException;
import com.bloomtech.library.models.Library;
import com.bloomtech.library.models.checkableTypes.*;
import com.bloomtech.library.repositories.CheckableRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
public class CheckableServiceTest {

    //TODO: Inject dependencies and mocks
    @Autowired
    private CheckableService checkableService;

    @MockBean
    private CheckableRepository checkableRepository;

    private List<Checkable> checkables;


    @BeforeEach
    void init() {
        //Initialize test data
        checkables = new ArrayList<>();

        checkables.addAll(
                Arrays.asList(
                        new Media("1-0", "The White Whale", "Melvin H", MediaType.BOOK),
                        new Media("1-1", "The Sorcerer's Quest", "Ana T", MediaType.BOOK),
                        new Media("1-2", "When You're Gone", "Complaining at the Disco", MediaType.MUSIC),
                        new Media("1-3", "Nature Around the World", "DocuSpecialists", MediaType.VIDEO),
                        new ScienceKit("2-0", "Anatomy Model"),
                        new ScienceKit("2-1", "Robotics Kit"),
                        new Ticket("3-0", "Science Museum Tickets"),
                        new Ticket("3-1", "National Park Day Pass")
                )
        );
    }

    //TODO: Write Unit Tests for all CheckableService methods and possible Exceptions
    @Test
    void getAll_returnsAllCheckables() {
        when(checkableRepository.findAll()).thenReturn(checkables);

        List<Checkable> result = checkableService.getAll();

        assertEquals(8, result.size());
        verify(checkableRepository).findAll();
    }

    @Test
    void getAll_returnsEmptyList() {
        when(checkableRepository.findAll()).thenReturn(new ArrayList<>());

        List<Checkable> result = checkableService.getAll();

        assertTrue(result.isEmpty());
        verify(checkableRepository).findAll();
    }

    @Test
    void getByIsbn_existingMedia() {
        String isbn = "1-0";
        when(checkableRepository.findByIsbn(isbn)).thenReturn(Optional.of(checkables.get(0)));

        Checkable result = checkableService.getByIsbn(isbn);

        assertTrue(result instanceof Media);
        assertEquals("The White Whale", ((Media)result).getTitle());
        assertEquals("Melvin H", ((Media)result).getAuthor());
        assertEquals(MediaType.BOOK, ((Media)result).getMediaType());
        verify(checkableRepository).findByIsbn(isbn);
    }

    @Test
    void getByIsbn_existingScienceKit() {
        String isbn = "2-0";
        when(checkableRepository.findByIsbn(isbn)).thenReturn(Optional.of(checkables.get(4)));

        Checkable result = checkableService.getByIsbn(isbn);

        assertTrue(result instanceof ScienceKit);
        assertEquals("Anatomy Model", result.getTitle());
        verify(checkableRepository).findByIsbn(isbn);
    }

    @Test
    void getByIsbn_existingTicket() {
        String isbn = "3-0";
        when(checkableRepository.findByIsbn(isbn)).thenReturn(Optional.of(checkables.get(6)));

        Checkable result = checkableService.getByIsbn(isbn);

        assertTrue(result instanceof Ticket);
        assertEquals("Science Museum Tickets", result.getTitle());
        verify(checkableRepository).findByIsbn(isbn);
    }

    @Test
    void getByIsbn_nonExistingCheckable() {
        String isbn = "non-existent";
        when(checkableRepository.findByIsbn(isbn)).thenReturn(Optional.empty());

        CheckableNotFoundException exception = assertThrows(CheckableNotFoundException.class, () -> {
            checkableService.getByIsbn(isbn);
        });

        assertEquals("No Checkable found with isbn: " + isbn, exception.getMessage());
        verify(checkableRepository).findByIsbn(isbn);
    }

    @Test
    void getByType_existingMediaType() {
        when(checkableRepository.findByType(Media.class)).thenReturn(Optional.of(checkables.get(0)));

        Checkable result = checkableService.getByType(Media.class);

        assertTrue(result instanceof Media);
        assertEquals("The White Whale", ((Media)result).getTitle());
        verify(checkableRepository).findByType(Media.class);
    }

    @Test
    void getByType_existingScienceKitType() {
        when(checkableRepository.findByType(ScienceKit.class)).thenReturn(Optional.of(checkables.get(4)));

        Checkable result = checkableService.getByType(ScienceKit.class);

        assertTrue(result instanceof ScienceKit);
        assertEquals("Anatomy Model", result.getTitle());
        verify(checkableRepository).findByType(ScienceKit.class);
    }

    @Test
    void getByType_existingTicketType() {
        when(checkableRepository.findByType(Ticket.class)).thenReturn(Optional.of(checkables.get(6)));

        Checkable result = checkableService.getByType(Ticket.class);

        assertTrue(result instanceof Ticket);
        assertEquals("Science Museum Tickets", result.getTitle());
        verify(checkableRepository).findByType(Ticket.class);
    }

    @Test
    void getByType_nonExistingType() {
        Class<?> type = Object.class;
        when(checkableRepository.findByType(type)).thenReturn(Optional.empty());

        CheckableNotFoundException exception = assertThrows(CheckableNotFoundException.class, () -> {
            checkableService.getByType(type);
        });

        assertEquals("No Checkable found of type: " + type.getName(), exception.getMessage());
        verify(checkableRepository).findByType(type);
    }

    @Test
    void save_newCheckable() {
        Media newMedia = new Media("1-4", "New Book", "New Author", MediaType.BOOK);
        when(checkableRepository.findAll()).thenReturn(checkables);

        checkableService.save(newMedia);

        verify(checkableRepository).save(newMedia);
    }

    @Test
    void save_existingCheckable() {
        Media duplicateMedia = new Media("1-0", "Duplicate Book", "Some Author", MediaType.BOOK);
        when(checkableRepository.findAll()).thenReturn(checkables);

        ResourceExistsException exception = assertThrows(ResourceExistsException.class, () -> {
            checkableService.save(duplicateMedia);
        });

        assertEquals("Checkable with isbn: 1-0 already exists!", exception.getMessage());
        verify(checkableRepository, never()).save(any(Checkable.class));
    }
}