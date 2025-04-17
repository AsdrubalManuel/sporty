package com.sporty.repository;

import com.sporty.model.Book;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface BookRepository extends PagingAndSortingRepository<Book, Long> {
    Optional<Book> findById(Long id);

    void deleteById(Long id);

    void save(Book book);
    List<Book> findAllByIdIn(Set<Long> ids);

}
