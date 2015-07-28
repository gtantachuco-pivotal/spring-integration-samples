package org.springframework.integration.samples.cafe.azure.eventhub;

import java.util.StringTokenizer;

import org.springframework.integration.metadata.PropertiesPersistingMetadataStore;
import org.springframework.messaging.Message;

public class EventHubOffsetManager {
	
	private PropertiesPersistingMetadataStore metadataStore;

	public String getOffset(String partitionId) {
		String partitionOffset = metadataStore.get(partitionId);
		return ((partitionOffset != null) ? partitionOffset : "0");
	}

	public Message<?> setOffset(Message<?> message) {		
		String offsetHeader = extractOffsetFromHeader(message.getHeaders().get("JMS_AMQP_MESSAGE_ANNOTATIONS"));
		metadataStore.put((String) message.getHeaders().get("PARTITION"), offsetHeader);		
		return message;
	}
	
	public void flushMetadata() {
		metadataStore.flush();
	}

	public PropertiesPersistingMetadataStore getMetadataStore() {
		return metadataStore;
	}

	public void setMetadataStore(PropertiesPersistingMetadataStore metadataStore) {
		this.metadataStore = metadataStore;
	}
	
	private String extractOffsetFromHeader(Object jsonValue) {
//
// Here is a sample header:
//		{ "x-opt-sequence-number" : { "long" : 16 }, "x-opt-offset" : "5952", "x-opt-enqueued-time" : { "timestamp" : 1437770644159 } }
//		
		String tmpOffset = "0";
		StringTokenizer st = new StringTokenizer((String) jsonValue, ":,");
		for (int i = 0; i < 5; i ++) {
			tmpOffset = st.nextToken().trim();
		}
		String newOffset = tmpOffset.replace("\"", "");
		return newOffset;
	}
}
