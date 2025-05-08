package com.example.mock.util;

import com.example.mock.model.RequestDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Deserializer;

public class RequestDtoDeserializer implements Deserializer<RequestDto> {

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public RequestDto deserialize(String topic, byte[] data) {
        return mapper.readValue(new String(data), RequestDto.class);
    }
}
