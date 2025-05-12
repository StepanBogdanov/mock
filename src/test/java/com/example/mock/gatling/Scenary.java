package com.example.mock.gatling;

import com.example.mock.model.Action;
import com.example.mock.model.RequestDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.gatling.javaapi.core.ChainBuilder;
import io.gatling.javaapi.core.FeederBuilder;
import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.Duration;
import java.util.*;

import static io.gatling.javaapi.core.CoreDsl.*;

public class Scenary extends Simulation {

    private static final FeederBuilder<String> usersFeeder = csv("users.csv").circular();

    private KafkaProducer<String, String> kafkaProducer;
    private ObjectMapper mapper;

    public Scenary() {
        Properties producerProps = new Properties();
        producerProps.put("bootstrap.servers", "localhost:9092");
        producerProps.put("key.serializer", StringSerializer.class.getName());
        producerProps.put("value.serializer", StringSerializer.class.getName());
        kafkaProducer = new KafkaProducer<>(producerProps);

        mapper = new ObjectMapper();
    }

    protected ChainBuilder sendRegisterRequest = exec(
            session -> {
                String login = session.getString("login");
                String password = session.getString("password");
            var request = new RequestDto(
                    Action.REGISTER,
                    new HashMap<>(Map.of("login", login, "password", password))
            );
            String jsonRequest;
            try {
                jsonRequest = mapper.writeValueAsString(request);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            kafkaProducer.send(new ProducerRecord<>("requestTopic", jsonRequest));
            return session;
        }
    );

    protected ChainBuilder sendHandleRequest = exec(
            session -> {
                String firstName = session.getString("first_name");
                String lastName = session.getString("last_name");
                String middleName = session.getString("middle_name");
                var request = new RequestDto(
                        Action.REQUEST,
                        new HashMap<>(Map.of("firstName", firstName, "lastName", lastName, "middleName", middleName))
                );
                String jsonRequest;
                try {
                    jsonRequest = mapper.writeValueAsString(request);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                kafkaProducer.send(new ProducerRecord<>("requestTopic", jsonRequest));
                return session;
            }
    );

    protected ChainBuilder sendDeleteRequest = exec(
            session -> {
                String login = session.getString("login");
                var request = new RequestDto(
                        Action.DELETE,
                        new HashMap<>(Map.of("login", login))
                );
                String jsonRequest;
                try {
                    jsonRequest = mapper.writeValueAsString(request);
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
                kafkaProducer.send(new ProducerRecord<>("requestTopic", jsonRequest));
                return session;
            }
    );

    private ScenarioBuilder scn = scenario("sendRequests")
            .feed(usersFeeder)
            .exec(sendRegisterRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendRegisterRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendRegisterRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendHandleRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendHandleRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendHandleRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendHandleRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendHandleRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendDeleteRequest).feed(usersFeeder)
            .pause(1, 4)
            .exec(sendDeleteRequest).feed(usersFeeder)
            .pause(1, 4);
    {
        setUp(
                scn.injectClosed(
                        incrementConcurrentUsers(1)
                                .times(5)
                                .eachLevelLasting(5*60)
                                .separatedByRampsLasting(10)
                                .startingFrom(1) // Int
                )
        );
    }

    @Override
    public void after() {
        kafkaProducer.close();
    }
}
