ALTER TABLE reporting_templates
    ADD CONSTRAINT uk_template_framework_version
        UNIQUE (framework, version);