package org.salesforce.integration.pubsub;

import com.nrapendra.Message;
import com.salesforce.eventbus.protobuf.ConsumerEvent;
import com.salesforce.eventbus.protobuf.FetchRequest;
import com.salesforce.eventbus.protobuf.FetchResponse;
import io.grpc.stub.ClientCallStreamObserver;
import io.grpc.stub.ClientResponseObserver;
import org.apache.avro.Schema;
import org.salesforce.integration.PubSubApiClient;
import org.salesforce.integration.pubsub.events.ChangeEventHeader;
import org.salesforce.integration.pubsub.events.Event;
import org.salesforce.integration.pubsub.events.EventParser;
import org.salesforce.integration.pubsub.events.EventParser.EventParseException;
import org.salesforce.integration.pubsub.kafka.EventProducer;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PubSubEventObserver implements ClientResponseObserver<FetchRequest, FetchResponse> {
	private PubSubApiClient client;
	private String topicName;
	private Schema schema;
	private int eventCoundRequested;

	private static final Logger logger = Logger.getLogger(PubSubEventObserver.class.getName());

	public PubSubEventObserver(PubSubApiClient client, String topicName, Schema schema, int eventCoundRequested) {
		this.client = client;
		this.topicName = topicName;
		this.schema = schema;
		this.eventCoundRequested = eventCoundRequested;
	}

	@Override
	public void onNext(FetchResponse value) {
		logger.info("Next event: " + value.toString());
		EventParser parser = new EventParser(schema);
		List<ConsumerEvent> consumerEvents = value.getEventsList();
		try {
			for (ConsumerEvent consumerEvent : consumerEvents) {
				Event event = parser.parse(consumerEvent);
				logger.info("Event raw payload: " + event);
				logger.info("Event replay ID: " + event.getReplayId());
				ChangeEventHeader header = event.getHeader();
				logger.info(header.getChangeType() + " operation on " + header.getEntityName() + " with record ID "
						+ String.join(",", header.getRecordIds()));
				logger.info("Changed fields: " + String.join(", ", header.getChangedFields()));

				var message= Message.builder()
						.id(value.getRpcId())
						.payload(String.valueOf(event.getPayload()).getBytes(StandardCharsets.UTF_8))
						.schema_id(String.valueOf(event.getReplayId())).build();

				new EventProducer().sendToKafkaTopic(message);
				logger.info("Event is produced to KafkaTopic");
			}	
		} catch (EventParseException e) {
			logger.log(Level.SEVERE, "Failed to parse message: " + e.getMessage(), e);
			this.client.shutdown();
		}
	}

	@Override
	public void onError(Throwable t) {
		logger.log(Level.SEVERE, "Subscribe/receive error: " + t.getMessage(), t);
	}

	@Override
	public void onCompleted() {
		logger.info("Done receiving events.");
		this.client.shutdown();
	}

	@Override
	public void beforeStart(ClientCallStreamObserver<FetchRequest> requestStream) {
		requestStream.setOnReadyHandler(new Runnable() {
			@Override
			public void run() {
				FetchRequest request = FetchRequest.newBuilder().setNumRequested(eventCoundRequested)
						.setTopicName(topicName).build();
				requestStream.onNext(request);
			}
		});
	}
}
