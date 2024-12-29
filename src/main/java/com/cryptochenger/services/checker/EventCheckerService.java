package com.cryptochenger.services.checker;

import com.cryptochenger.repository.EventRepository;
import com.cryptochenger.services.EventService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EventCheckerService extends AbstractAccessCheckerService<EventCheckerService.EventAccessData> {

    private final EventService eventService;

    @Override
    protected boolean check(EventAccessData accessData) {
        return eventService.isEventCreator(
                accessData.id,
                accessData.currentUserUsername
        );
    }

    @Override
    protected EventAccessData getAccessData(HttpServletRequest httpServletRequest) {
        Long eventId = getFromPathVariable(
                httpServletRequest,
                "id",
                Long::valueOf
        );

        return new EventAccessData(eventId, getCurrentAuthentication().getUsername());
    }

    protected record EventAccessData(
            Long id, String currentUserUsername
    ) implements AccessData {}
}
