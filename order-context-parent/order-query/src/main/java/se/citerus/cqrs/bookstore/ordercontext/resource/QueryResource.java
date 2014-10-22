package se.citerus.cqrs.bookstore.ordercontext.resource;

import se.citerus.cqrs.bookstore.event.DomainEvent;
import se.citerus.cqrs.bookstore.event.DomainEventStore;
import se.citerus.cqrs.bookstore.ordercontext.publishercontract.PublisherContractId;
import se.citerus.cqrs.bookstore.ordercontext.query.QueryService;
import se.citerus.cqrs.bookstore.ordercontext.query.orderlist.OrderProjection;

import javax.validation.Valid;
import javax.ws.rs.*;
import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@Path("query")
@Produces(APPLICATION_JSON)
@Consumes(APPLICATION_JSON)
public class QueryResource {

    private final QueryService queryService;
    private final DomainEventStore eventStore;

    public QueryResource(QueryService queryService, DomainEventStore eventStore) {
        this.queryService = queryService;
        this.eventStore = eventStore;
    }

    @GET
    @Path("events")
    public List<Object[]> getAllEvents() {
        List<DomainEvent> allEvents = eventStore.getAllEvents();
        List<Object[]> eventsToReturn = new LinkedList<>();
        for (DomainEvent event : allEvents) {
            eventsToReturn.add(new Object[]{event.getClass().getSimpleName(), event});
        }
        return eventsToReturn;
    }

    @GET
    @Path("orders")
    public Collection<OrderProjection> getOrders() {
        return queryService.getOrders();
    }

    @GET
    @Path("orders-per-day")
    public Map getOrdersPerDay() {
        return queryService.getOrdersPerDay();
    }

    @GET
    @Path("profit/{id}")
    public Long getProfit(@PathParam("id") String contractId) {
        return queryService.getProfit(new PublisherContractId(contractId));
    }

}
