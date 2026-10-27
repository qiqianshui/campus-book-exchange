package com.campus.book.controller;

import com.campus.book.entity.Textbook;
import com.campus.book.service.BookService;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    // 所有人都能看列表
    @GetMapping
    public List<Textbook> listAll() {
        return bookService.listAll();
    }

    // 发布（需登录）：当前登录用户名从 Authentication 里取
    @PostMapping
    public Textbook create(@RequestBody Textbook textbook, Authentication authentication) {
        return bookService.create(textbook, authentication.getName());
    }

    // 我的发布（需登录）
    @GetMapping("/me")
    public List<Textbook> listMine(Authentication authentication) {
        return bookService.listMine(authentication.getName());
    }

    // 删除（需登录，且只能删自己的）
    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id, Authentication authentication) {
        bookService.delete(id, authentication.getName());
    }
}
