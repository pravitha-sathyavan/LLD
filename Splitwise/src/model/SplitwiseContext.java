package model;

import service.EventsService;
import service.ExpenseService;
import service.Impl.EventsServiceImpl;
import service.Impl.ExpenseServiceImpl;
import service.Impl.RegisterServiceImpl;
import service.RegisterService;

public class SplitwiseContext {
    private final Splitwise splitwise;
    private final EventsService eventsService;
    private final ExpenseService expenseService;
    private final RegisterService registerService;

    public SplitwiseContext(RegisterService registerService) {
        this.registerService = new RegisterServiceImpl(this);
        this.eventsService = new EventsServiceImpl(this); // Pass context or required services only
        this.expenseService = new ExpenseServiceImpl(this);
        this.splitwise = new Splitwise(this); // Pass context to Splitwise
    }

    // Provide getters for services so each service can access others
    public Splitwise getSplitwise() {
        return splitwise;
    }

    public EventsService getEventsService() {
        return eventsService;
    }

    public ExpenseService getExpenseService() {
        return expenseService;
    }

    public RegisterService getRegisterService() {
        return registerService;
    }
}
