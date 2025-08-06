package com.example.csvloader.repository;

import com.example.csvloader.model.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDate;
import java.util.List;

public interface PostingRepository extends JpaRepository<Posting, Long> {

    List<Posting> findByDocDateBetween(LocalDate start, LocalDate end);

    List<Posting> findByDocDateBetweenAndAuthorizedSupply(
            LocalDate start, LocalDate end, boolean authorized
    );
}

