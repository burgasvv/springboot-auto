package com.burgas.springbootauto.service.news;

import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.repository.news.NewsRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NewsService {

    private final NewsRepository newsRepository;

    public List<News> findAll() {
        return newsRepository.findAll();
    }

    public Page<News> findAll(int page, int size) {
        return newsRepository.findAll(PageRequest.of(page - 1, size));
    }

    public Page<News>searchNewsByKeyword(String keyword, int page, int size) {
        return newsRepository.searchNewsByKeyword(keyword, PageRequest.of(page - 1, size));
    }

    public News findById(Long id) {
        return newsRepository.findById(id).orElse(null);
    }

    public boolean isExist(String title) {
        List<News> all = newsRepository.findAll();
        for (News news : all) {
            if (news.getTitle().equals(title)) {
                return true;
            }
        }
        return false;
    }

    @Transactional
    public News save(News news) {
        return newsRepository.save(news);
    }
}
