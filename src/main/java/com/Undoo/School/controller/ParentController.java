package com.Undoo.School.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.Undoo.School.entity.Offering;
import com.Undoo.School.repository.OfferingRepository;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/parent")
@RequiredArgsConstructor
public class ParentController {
    private final OfferingRepository offeringRepository;

    @GetMapping("/offerings")
    public ResponseEntity<List<Offering>> getOffering(){
        return ResponseEntity.ok(offeringRepository.findAll());
    }

}
