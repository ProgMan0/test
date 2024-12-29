package com.cryptochenger.entity.dto;

import lombok.Data;

import java.time.Instant;

public record EventDto (
     Long id,
     Instant createdAt,
     Instant endAt
)
{}
