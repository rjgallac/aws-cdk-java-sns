package com.rob.dynamodbfunction;

import com.amazonaws.regions.Regions;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.SQSEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<SQSEvent, Void> {

    public Void handleRequest(final SQSEvent sqsEvent, final Context context) {
        context.getLogger().log("test");
        sqsEvent.getRecords().forEach( r -> {
            context.getLogger().log(r.getBody());
            putItemDynamoDB("test");
        });


        return null;

    }

    private static void putItemDynamoDB(String test) {
        AmazonDynamoDB amazonDynamoDB = AmazonDynamoDBClientBuilder.standard()
                .withRegion(Regions.EU_WEST_2)
                .build();

        Map<String, AttributeValue> attributesMap = new HashMap<>();

        attributesMap.put("id", new AttributeValue(UUID.randomUUID().toString()));
        attributesMap.put("firstName", new AttributeValue(test));


        amazonDynamoDB.putItem(System.getenv("DYNO_TABLE"), attributesMap);
    }

}
