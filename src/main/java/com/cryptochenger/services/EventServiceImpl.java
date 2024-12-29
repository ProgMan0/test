package com.cryptochenger.services;

import com.cryptochenger.entity.Event;
import com.cryptochenger.entity.RequestFilter;
import com.cryptochenger.repository.EventRepository;
import com.cryptochenger.repository.specifications.EventSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    @Override
    public boolean isEventCreator(Long eventId, String username) {
        Specification<Event> specification = EventSpecification.getEventsByFilter(new RequestFilter(eventId, username));
        return !eventRepository.findAll(specification).isEmpty();
    }
}
