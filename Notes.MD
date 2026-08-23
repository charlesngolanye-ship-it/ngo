## BudgetAllocationController
* according to my chatgpt design
* POST   /api/budgetallocations/{id}/budgets
* GET    /api/budgetallocations/{id}/budgets
---
* This sub-resource URL design pattern is great when you are working with strict parent-child lifecycles.
* However, since a BudgetAllocation is a join/relationship record linking a Grant to a Budget Category with an approved amount, the standard REST way to look at this is:
* If you want to see all budget allocations belonging to a specific Grant, use:
* GET /grants/{grantId}/budget-allocations
* If you want to manage allocations as standalone records, stick to your primary endpoint:
* POST /budgetAllocations and GET /budgetAllocations/{id}


