package helloworld;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyRequestEvent;
import com.amazonaws.services.lambda.runtime.events.APIGatewayProxyResponseEvent;
import com.amazonaws.services.lambda.runtime.events.SNSEvent;

/**
 * Handler for requests to Lambda function.
 */
public class App implements RequestHandler<SNSEvent, Void> {

    public Void handleRequest(final SNSEvent snsEvent, final Context context) {
        context.getLogger().log(snsEvent.getRecords().get(0).getSNS().getMessage());
        return null;

    }

}
