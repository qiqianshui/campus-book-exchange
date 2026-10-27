package com.campus.book.repository;

import com.campus.book.entity.Textbook;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface TextbookRepository extends JpaRepository<Textbook, Long> {
    List<Textbook> findByOwnerId(Long ownerId);
}
