package com.learn.kafka.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.util.Date;
import java.util.Map;

@Data
@Document(indexName = "connect-exchange")
public class ExchangeModel {

    @Id
    private String id;

    private String base;
    private String date;
    private Map<String, Double> rates;

    private Date timestamp;
}
