package com.burgas.springbootauto.repository.news;

import com.burgas.springbootauto.entity.news.News;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @NotNull
    Page<News>findAll(@NotNull Pageable pageable);
}
