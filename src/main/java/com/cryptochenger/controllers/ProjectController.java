package com.cryptochenger.controllers;

import com.cryptochenger.aop.AccessCheckType;
import com.cryptochenger.aop.Accessible;
import com.cryptochenger.entity.Event;
import com.cryptochenger.entity.dto.DeleteEventDto;
import com.cryptochenger.entity.dto.EventDto;
import com.cryptochenger.repository.EventRepository;
import com.cryptochenger.repository.specifications.EventSpecification;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@RestController
public class ProjectController {

    private final EventRepository eventRepository;

    @Transactional
    @PostMapping("/public/test")
    public List<EventDto> test(@AuthenticationPrincipal UserDetails user) {
        Specification<Event> specification = EventSpecification.isEquals(user.getUsername());

        List<EventDto> dtos = new ArrayList<>();
        eventRepository.findAll(specification)
                .forEach(event -> dtos.add(new EventDto(event.getId(), event.getCreatedAt(), event.getEndAt())));

        return dtos;
    }

    @Accessible(checkBy = AccessCheckType.EVENT)
    @PostMapping("/public/delete/{id}")
    public ResponseEntity<String> deleteEvent(@PathVariable(name = "id") Long id,
                                              @RequestBody DeleteEventDto eventDto) {
        return new ResponseEntity<>(eventDto.id() + " deleted", HttpStatus.ACCEPTED);
    }


}
