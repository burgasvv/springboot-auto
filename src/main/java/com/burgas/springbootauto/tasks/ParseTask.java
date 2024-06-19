package com.burgas.springbootauto.tasks;

import com.burgas.springbootauto.entity.news.News;
import com.burgas.springbootauto.service.news.NewsService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
@RequiredArgsConstructor
public class ParseTask {

    private final NewsService newsService;

    @SneakyThrows
    @Scheduled(fixedRate = 120000)
    public void parseNews() {

        for (int page = 2; page <= 4; page++) {
            Document newsList = Jsoup.connect("https://news.drom.ru/page" + page).get();
            Elements articles = newsList.getElementsByClass(
                    "b-media-query b-random-group b-random-group_margin_r-b-size-s-s b-media-cont b-visited"
            ).getFirst().getElementsByClass(
                    "b-info-block b-info-block_like-text b-info-block_width_215");

            for (Element article : articles) {
                String href = article.getElementsByTag("a").attr("href");
                String image = article.getElementsByTag("img").getLast().attr("src");
                String title = article.getElementsByClass("b-info-block__title b-link").getLast().ownText();
                String date = article.getElementsByClass(
                        "b-info-block__text b-info-block__text_type_news-date").getLast().ownText();

                Elements paragraphs = Objects.requireNonNull(Jsoup.connect(href).get().getElementById("news_text"))
                        .getElementsByTag("p");
                StringBuilder stringBuilder = new StringBuilder();
                for (Element paragraph : paragraphs) {
                    stringBuilder.append(paragraph.text()).append("\n\n");
                }

                if (!newsService.isExist(title)) {
                    News news = new News();
                    news.setUrl(href);
                    news.setImage(image);
                    news.setTitle(title);
                    news.setDate(date);
                    news.setContent(stringBuilder.toString());
                    newsService.save(news);
                }
            }
        }
    }
}
