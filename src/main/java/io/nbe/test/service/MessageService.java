package io.nbe.test.service;

import io.nbe.test.domain.Message;
import io.nbe.test.repository.MessageRepository;
import io.nbe.test.repository.search.MessageSearchRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import static org.elasticsearch.index.query.QueryBuilders.*;

/**
 * Service Implementation for managing Message.
 */
@Service
@Transactional
public class MessageService {

    private final Logger log = LoggerFactory.getLogger(MessageService.class);
    
    @Inject
    private MessageRepository messageRepository;

    @Inject
    private MessageSearchRepository messageSearchRepository;

    /**
     * Save a message.
     *
     * @param message the entity to save
     * @return the persisted entity
     */
    public Message save(Message message) {
        log.debug("Request to save Message : {}", message);
        Message result = messageRepository.save(message);
        messageSearchRepository.save(result);
        return result;
    }

    /**
     *  Get all the messages.
     *  
     *  @param pageable the pagination information
     *  @return the list of entities
     */
    @Transactional(readOnly = true) 
    public Page<Message> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        Page<Message> result = messageRepository.findAll(pageable);
        return result;
    }

    /**
     *  Get one message by id.
     *
     *  @param id the id of the entity
     *  @return the entity
     */
    @Transactional(readOnly = true) 
    public Message findOne(Long id) {
        log.debug("Request to get Message : {}", id);
        Message message = messageRepository.findOne(id);
        return message;
    }

    /**
     *  Delete the  message by id.
     *
     *  @param id the id of the entity
     */
    public void delete(Long id) {
        log.debug("Request to delete Message : {}", id);
        messageRepository.delete(id);
        messageSearchRepository.delete(id);
    }

    /**
     * Search for the message corresponding to the query.
     *
     *  @param query the query of the search
     *  @return the list of entities
     */
    @Transactional(readOnly = true)
    public Page<Message> search(String query, Pageable pageable) {
        log.debug("Request to search for a page of Messages for query {}", query);
        Page<Message> result = messageSearchRepository.search(queryStringQuery(query), pageable);
        return result;
    }
}
