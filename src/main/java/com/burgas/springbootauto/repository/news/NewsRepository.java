package com.burgas.springbootauto.repository.news;

import com.burgas.springbootauto.entity.news.News;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface NewsRepository extends JpaRepository<News, Long> {

    @NotNull
    Page<News>findAll(@NotNull Pageable pageable);

    @Query(
            nativeQuery = true,
            value = """
                        select distinct n.* from news n
                        where concat(n.title,' ',n.date,' ',n.title,' ') ilike concat('%',?1,'%')
                    """
    )
    Page<News>searchNewsByKeyword(@NotNull String keyword, @NotNull Pageable pageable);
}
