package dev.api.springmvc.api.users.dtos;

import dev.api.springmvc.common.kafka.KafkaMessage;

public record UserEventDto(KafkaMessage.Type type, UserDto user) {}
