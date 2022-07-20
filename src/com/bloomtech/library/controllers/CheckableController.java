package com.bloomtech.library.controllers;

import com.bloomtech.library.models.checkableTypes.Checkable;
import com.bloomtech.library.services.CheckableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/checkables")
public class CheckableController {

    CheckableService checkableService;

    public CheckableController(CheckableService checkableService) {
        this.checkableService = checkableService;
    }

    @GetMapping(value = "")
    public ResponseEntity<?> getCheckables() {
        List<Checkable> checkables = checkableService.getAll();
        return new ResponseEntity<>(checkables, HttpStatus.OK);
    }
}
