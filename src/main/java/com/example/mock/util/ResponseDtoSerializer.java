package com.example.mock.util;

import com.example.mock.model.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.apache.kafka.common.serialization.Serializer;

public class ResponseDtoSerializer implements Serializer<ResponseDto> {

    private final ObjectMapper mapper = new ObjectMapper();

    @SneakyThrows
    @Override
    public byte[] serialize(String topic, ResponseDto data) {
        return mapper.writeValueAsBytes(data);
    }
}
