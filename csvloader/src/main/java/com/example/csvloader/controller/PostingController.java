package com.example.csvloader.controller;

import com.example.csvloader.model.Posting;
import com.example.csvloader.repository.PostingRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/postings")
public class PostingController {

    private final PostingRepository postingRepo;

    public PostingController(PostingRepository postingRepo) {
        this.postingRepo = postingRepo;
    }

    @GetMapping("/all")
    public List<Posting> getAll() {
        return postingRepo.findAll();
    }

    @GetMapping
    public List<Posting> getPostings(
            @RequestParam("start") LocalDate start,
            @RequestParam("end")   LocalDate end,
            @RequestParam(name = "authorizedSupply", required = false)
            Boolean authorizedSupply
    ) {
        if (authorizedSupply == null) {
            return postingRepo.findByDocDateBetween(start, end);
        }
        return postingRepo.findByDocDateBetweenAndAuthorizedSupply(
                start, end, authorizedSupply
        );
    }
}


