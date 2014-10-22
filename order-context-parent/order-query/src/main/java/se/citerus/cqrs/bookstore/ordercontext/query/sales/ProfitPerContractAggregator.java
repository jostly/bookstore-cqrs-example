package se.citerus.cqrs.bookstore.ordercontext.query.sales;

import com.google.common.collect.Maps;
import com.google.common.eventbus.Subscribe;
import se.citerus.cqrs.bookstore.event.DomainEventListener;
import se.citerus.cqrs.bookstore.ordercontext.publishercontract.PublisherContractId;
import se.citerus.cqrs.bookstore.ordercontext.publishercontract.event.PurchaseRegisteredEvent;

import java.util.Map;

public class ProfitPerContractAggregator implements DomainEventListener {

    final private Map<PublisherContractId, Long> profitPerContract = Maps.newHashMap();

    @Subscribe
    public void handleEvent(PurchaseRegisteredEvent event) {
        Long accumulatedProfit = profitPerContract.get(event.aggregateId);
        if (accumulatedProfit == null) {
            accumulatedProfit = 0L;
        }
        accumulatedProfit += event.purchaseAmount - event.feeAmount;
        profitPerContract.put(event.aggregateId, accumulatedProfit);
    }

    @Override
    public boolean supportsReplay() {
        return true;
    }


    public Long profit(PublisherContractId id) {
        return profitPerContract.get(id);
    }
}
