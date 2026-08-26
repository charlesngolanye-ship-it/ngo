-- 1. Insert 5 New Real-World Grants (IDs 17 - 21)
INSERT INTO grants (id, grant_number, grant_name, donor_name, total_approved_budget, start_date, end_date, status)
VALUES (17, 'EU-2026-MALI-05', 'Sahel Youth Economic Empowerment & Skills Initiative', 'European Union', 1850000.00,
        '2026-03-01', '2028-02-28', 'ACTIVE'),
       (18, 'GAVI-2026-MOZ-12', 'Northern Mozambique Maternal & Infant Immunization Drive', 'GAVI Alliance', 1200000.00,
        '2026-04-15', '2027-04-14', 'ACTIVE'),
       (19, 'NORAD-2026-COL-03', 'Andean Forest Protection & Sustainable Agriculture', 'NORAD', 2300000.00,
        '2026-01-01', '2028-12-31', 'ACTIVE'),
       (20, 'IRC-2026-SDN-08', 'Sudan Emergency Food Distribution & Malnutrition Intervention',
        'International Rescue Committee', 950000.00, '2026-05-01', '2027-04-30', 'ACTIVE'),
       (21, 'UNDP-2026-UGA-01', 'Uganda Primary Rural School Rehabilitation & Tech Fund', 'UNDP', 780000.00,
        '2026-06-01', '2027-05-31', 'ACTIVE');

-- 2. Insert Associated Budget Allocations (IDs 53 - 75)
INSERT INTO budget_allocations (id, grant_id, category_id, approved_amount)
VALUES
-- EU Mali Youth Initiative (Grant 17)
(53, 17, 1, 600000.00), -- Personnel
(54, 17, 4, 500000.00), -- Training
(55, 17, 3, 350000.00), -- Equipment
(56, 17, 9, 250000.00), -- Contractual & Consultants
(57, 17, 10, 150000.00),-- Operating & Administrative

-- GAVI Mozambique Immunization Drive (Grant 18)
(58, 18, 1, 350000.00), -- Personnel
(59, 18, 6, 450000.00), -- Immunition
(60, 18, 2, 150000.00), -- Travel
(61, 18, 8, 150000.00), -- Program Supplies
(62, 18, 10, 100000.00),-- Operating & Administrative

-- NORAD Colombia Forest Protection (Grant 19)
(63, 19, 1, 800000.00), -- Personnel
(64, 19, 3, 600000.00), -- Equipment
(65, 19, 2, 300000.00), -- Travel
(66, 19, 9, 400000.00), -- Contractual & Consultants
(67, 19, 10, 200000.00),-- Operating & Administrative

-- IRC Sudan Food Distribution (Grant 20)
(68, 20, 5, 500000.00), -- Nutrition & Natal Care
(69, 20, 8, 250000.00), -- Program Supplies
(70, 20, 2, 120000.00), -- Travel
(71, 20, 10, 80000.00), -- Operating & Administrative

-- UNDP Uganda Primary School Rehab (Grant 21)
(72, 21, 7, 350000.00), -- School Fees & Education
(73, 21, 3, 230000.00), -- Equipment
(74, 21, 4, 120000.00), -- Training
(75, 21, 10, 80000.00);
-- Operating & Administrative

-- 3. Insert Associated Expenses (IDs 20 - 29)
INSERT INTO expenses (id, grant_id, category_id, description, amount, expense_date, vendor, reference_number)
VALUES
-- Grant 17 (EU Mali)
(20, 17, 4, 'Vocational training tools and workshop kits', 125000.00, '2026-04-10', 'Bamako Vocational Supplies Ltd',
 'EU-ML-2026-01'),
(21, 17, 3, 'Laptops for youth digital hub centers', 88000.00, '2026-05-18', 'West Africa Tech Solutions',
 'INV-WAT-9081'),

-- Grant 18 (GAVI Mozambique)
(22, 18, 6, 'Bulk cold-chain vaccine carrier boxes & temperature monitors', 185000.00, '2026-05-02',
 'B Medical Systems', 'GAVI-MOZ-EXP-01'),
(23, 18, 2, 'Field logistics and 4x4 rental for rural medical teams', 34000.00, '2026-06-11', 'Pemba Fleet Logistics',
 'PFL-2026-044'),

-- Grant 19 (NORAD Colombia)
(24, 19, 3, 'Geographic satellite mapping software & field GPS gear', 145000.00, '2026-02-20', 'ESRI Colombia',
 'NOR-COL-8831'),
(25, 19, 9, 'Environmental impact assessment consultant fee', 42000.00, '2026-04-15', 'BioAndes Consulting Group',
 'BAC-INV-009'),

-- Grant 20 (IRC Sudan)
(26, 20, 5, 'Therapeutic food rations and infant nutritional supplements', 210000.00, '2026-05-28', 'Nutriset Global',
 'NUT-SDN-2026-11'),
(27, 20, 8, 'Clean water storage jerrycans and hygiene packs', 65000.00, '2026-06-19', 'Nile Relief Goods Ltd',
 'NRG-77210'),

-- Grant 21 (UNDP Uganda)
(28, 21, 7, 'Classroom desk sets and chalkboard renovations', 92000.00, '2026-06-10', 'Kampala School Furniture Ltd',
 'UNDP-UG-001'),
(29, 21, 3, 'Desktop computers and solar backup batteries for schools', 115000.00, '2026-07-04',
 'SolarTech East Africa', 'STE-INV-304');