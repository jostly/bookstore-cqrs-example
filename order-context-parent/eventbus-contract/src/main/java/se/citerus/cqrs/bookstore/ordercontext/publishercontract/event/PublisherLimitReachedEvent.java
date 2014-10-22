package se.citerus.cqrs.bookstore.ordercontext.publishercontract.event;

import com.fasterxml.jackson.annotation.JsonProperty;
import se.citerus.cqrs.bookstore.event.DomainEvent;
import se.citerus.cqrs.bookstore.ordercontext.order.ProductId;
import se.citerus.cqrs.bookstore.ordercontext.publishercontract.PublisherContractId;

public class PublisherLimitReachedEvent extends DomainEvent<PublisherContractId> {

  public PublisherLimitReachedEvent(@JsonProperty("aggregateId") PublisherContractId aggregateId,
                                    @JsonProperty("version") int version,
                                    @JsonProperty("timestamp") long timestamp) {
    super(aggregateId, version, timestamp);
  }

}
