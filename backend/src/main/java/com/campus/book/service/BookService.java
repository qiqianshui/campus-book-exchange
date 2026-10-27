package com.campus.book.service;

import com.campus.book.entity.Textbook;
import com.campus.book.entity.User;
import com.campus.book.repository.TextbookRepository;
import com.campus.book.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private final TextbookRepository textbookRepository;
    private final UserRepository userRepository;

    public BookService(TextbookRepository textbookRepository, UserRepository userRepository) {
        this.textbookRepository = textbookRepository;
        this.userRepository = userRepository;
    }

    // 列出全部在售教材
    public List<Textbook> listAll() {
        return textbookRepository.findAll();
    }

    // 发布教材：自动把当前登录用户设为拥有者，状态置为在售
    public Textbook create(Textbook textbook, String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        textbook.setOwner(owner);
        textbook.setStatus("AVAILABLE");
        return textbookRepository.save(textbook);
    }

    // 列出当前用户自己发布的教材
    public List<Textbook> listMine(String username) {
        User owner = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("用户不存在"));
        return textbookRepository.findByOwnerId(owner.getId());
    }

    // 删除：只能删自己的
    public void delete(Long id, String username) {
        Textbook tb = textbookRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("教材不存在"));
        if (tb.getOwner() == null || !tb.getOwner().getUsername().equals(username)) {
            throw new RuntimeException("只能删除自己发布的教材");
        }
        textbookRepository.deleteById(id);
    }
}
