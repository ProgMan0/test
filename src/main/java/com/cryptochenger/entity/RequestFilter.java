package com.cryptochenger.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class RequestFilter {
    private Long eventId;

    private String creatorUsername;
}
