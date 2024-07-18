package com.burgas.springbootauto.repository.chat;

import com.burgas.springbootauto.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, Long> {

    @Query(
            nativeQuery = true,
            value = """
                        select distinct c.* from chat c left join chat_people cp on c.id = cp.chat_id where cp.person_id in (:people)
                    """
    )
    Optional<Chat> findChatByPeople(List<Long> people);

    Optional<Chat> findChatBySenderAndReceiver(String sender, String receiver);
}
