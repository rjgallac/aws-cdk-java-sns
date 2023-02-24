package secondfunction;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
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
