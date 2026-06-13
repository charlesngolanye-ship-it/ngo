CREATE TABLE grants (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    grant_number VARCHAR(50) NOT NULL UNIQUE,
    grant_name VARCHAR(255) NOT NULL,
    donor_name VARCHAR(255) NOT NULL,
    total_approved_budget DECIMAL(15,2) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(20) NOT NULL
);

CREATE TABLE budget_categories (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(255)
);


CREATE TABLE budget_allocations (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    grant_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,
    approved_amount DECIMAL(15,2) NOT NULL,

    FOREIGN KEY (grant_id) REFERENCES grants(id),
    FOREIGN KEY (category_id) REFERENCES budget_categories(id)
);

CREATE TABLE expenses (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    grant_id BIGINT NOT NULL,
    category_id BIGINT NOT NULL,

    description VARCHAR(255) NOT NULL,
    amount DECIMAL(15,2) NOT NULL,
    expense_date DATE NOT NULL,

    vendor VARCHAR(255),
    reference_number VARCHAR(100),

    FOREIGN KEY (grant_id) REFERENCES grants(id),
    FOREIGN KEY (category_id) REFERENCES budget_categories(id)
);