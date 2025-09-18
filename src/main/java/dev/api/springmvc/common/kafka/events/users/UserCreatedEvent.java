package dev.api.springmvc.common.kafka.events.users;

import dev.api.springmvc.api.users.dtos.UserDto;

public record UserCreatedEvent(UserDto user) {}

