package com.cryptochenger.services;

public interface EventService {

    boolean isEventCreator(Long eventId, String userUsername);

}
