package se.citerus.cqrs.bookstore.ordercontext.query.sales;

import org.junit.Test;
import se.citerus.cqrs.bookstore.ordercontext.order.ProductId;
import se.citerus.cqrs.bookstore.ordercontext.publishercontract.PublisherContractId;
import se.citerus.cqrs.bookstore.ordercontext.publishercontract.event.PurchaseRegisteredEvent;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.*;

public class ProfitPerContractAggregatorTest {

    public static final long PURCHASE_AMOUNT = 100;
    public static final long FEE_AMOUNT = 20;
    public static final long ACCUMULATED_FEE = 30;
    public static final int VERSION = 1;
    public static final int TIMESTAMP = 0;
    public static final PublisherContractId PUBLISHER_CONTRACT_ID_1 = new PublisherContractId("id1");
    public static final PublisherContractId PUBLISHER_CONTRACT_ID_2 = new PublisherContractId("id2");
    ProfitPerContractAggregator aggregator = new ProfitPerContractAggregator();

    @Test
    public void shouldAggregateSingleEvent() {
        PurchaseRegisteredEvent event = createEvent(PUBLISHER_CONTRACT_ID_1, PURCHASE_AMOUNT, FEE_AMOUNT, ACCUMULATED_FEE);

        aggregator.handleEvent(event);

        assertThat(aggregator.profit(new PublisherContractId("id1")), is(PURCHASE_AMOUNT - FEE_AMOUNT));
    }

    @Test
    public void shouldAggregateTwoEventsOnSamePublisher() {
        PurchaseRegisteredEvent event1 = createEvent(PUBLISHER_CONTRACT_ID_1, 200, 20, 0);
        PurchaseRegisteredEvent event2 = createEvent(PUBLISHER_CONTRACT_ID_1, 100, 10, 20);

        aggregator.handleEvent(event1);
        aggregator.handleEvent(event2);


        assertThat(aggregator.profit(PUBLISHER_CONTRACT_ID_1), is(270L));
    }

    @Test
    public void shouldKeepDifferentContractsSeparate() {
        PurchaseRegisteredEvent event1 = createEvent(PUBLISHER_CONTRACT_ID_1, 200, 20, 0);
        PurchaseRegisteredEvent event2 = createEvent(PUBLISHER_CONTRACT_ID_2, 100, 10, 20);

        aggregator.handleEvent(event1);
        aggregator.handleEvent(event2);


        assertThat(aggregator.profit(PUBLISHER_CONTRACT_ID_1), is(180L));
        assertThat(aggregator.profit(PUBLISHER_CONTRACT_ID_2), is(90L));
    }

    private PurchaseRegisteredEvent createEvent(PublisherContractId publisherContractId, long amount, long fee, long accumulatedFee) {
        return new PurchaseRegisteredEvent(
                publisherContractId,
                VERSION,
                TIMESTAMP,
                new ProductId("product1"),
                amount,
                fee,
                accumulatedFee
        );
    }


}