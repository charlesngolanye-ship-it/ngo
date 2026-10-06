CREATE TABLE reporting_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    framework VARCHAR(50) NOT NULL,
    CONSTRAINT uk_framework_code UNIQUE (framework, code)
);
CREATE TABLE reporting_templates (
    id BIGINT AUTO_INCREMENT  PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    framework VARCHAR(50) NOT NULL,
    version VARCHAR(50) NOT NULL,
    template_status VARCHAR(50) NOT NULL
);
CREATE TABLE report_sections (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    code VARCHAR(100) NOT NULL,
    name VARCHAR(255) NOT NULL,
    display_order INT NOT NULL,
    template_id BIGINT NOT NULL,
    CONSTRAINT fk_report_sections_template FOREIGN KEY (template_id)
        REFERENCES reporting_templates (id) ON DELETE CASCADE,
    CONSTRAINT uk_template_code UNIQUE (template_id, code),
    CONSTRAINT uk_template_display_order UNIQUE (template_id, display_order)
);
CREATE TABLE report_lines (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    display_order INT NOT NULL,
    calculation_type VARCHAR(50) NOT NULL,
    section_id BIGINT NOT NULL,
    reporting_code_id BIGINT NOT NULL,
    CONSTRAINT fk_report_lines_section FOREIGN KEY (section_id)
        REFERENCES report_sections (id) ON DELETE CASCADE,
    CONSTRAINT fk_report_lines_reporting_code FOREIGN KEY (reporting_code_id)
        REFERENCES reporting_codes (id),
    CONSTRAINT uk_section_id_display_order UNIQUE (section_id, display_order),
    CONSTRAINT uk_section_id_name UNIQUE (section_id, name)
);
CREATE TABLE reporting_mappings (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    report_template_id BIGINT NOT NULL,
    budget_category_id BIGINT NOT NULL,
    report_line_id BIGINT NOT NULL,
    CONSTRAINT fk_reporting_mappings_template FOREIGN KEY (report_template_id)
        REFERENCES reporting_templates (id) ON DELETE CASCADE,
    CONSTRAINT fk_reporting_mappings_category FOREIGN KEY (budget_category_id)
        REFERENCES budget_categories (id),
    CONSTRAINT fk_reporting_mappings_line FOREIGN KEY (report_line_id)
        REFERENCES report_lines (id) ON DELETE CASCADE,
    CONSTRAINT uk_template_budget_category UNIQUE (report_template_id, budget_category_id)
);
CREATE TABLE reporting_periods (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    start_date DATE NOT NULL,
    end_date DATE NOT NULL,
    status VARCHAR(50) NOT NULL,
    grant_id BIGINT NOT NULL,
    CONSTRAINT fk_reporting_periods_grant FOREIGN KEY (grant_id)
        REFERENCES grants (id) ON DELETE CASCADE
);