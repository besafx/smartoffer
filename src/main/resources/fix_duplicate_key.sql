SELECT setval('account_sequence', (SELECT MAX(id) FROM account) + 1);
SELECT setval('bank_sequence', (SELECT MAX(id) FROM bank) + 1);
SELECT setval('bill_buy_sequence', (SELECT MAX(id) FROM bill_buy) + 1);
SELECT setval('bill_buy_type_sequence', (SELECT MAX(id) FROM bill_buy_type) + 1);
SELECT setval('branch_sequence', (SELECT MAX(id) FROM branch) + 1);
SELECT setval('company_sequence', (SELECT MAX(id) FROM company) + 1);
SELECT setval('contact_sequence', (SELECT MAX(id) FROM contact) + 1);
SELECT setval('course_sequence', (SELECT MAX(id) FROM course) + 1);
SELECT setval('deposit_sequence', (SELECT MAX(id) FROM deposit) + 1);
SELECT setval('master_sequence', (SELECT MAX(id) FROM master) + 1);
SELECT setval('offer_sequence', (SELECT MAX(id) FROM offer) + 1);
SELECT setval('payment_sequence', (SELECT MAX(id) FROM payment) + 1);
SELECT setval('permission_sequence', (SELECT MAX(id) FROM permission) + 1);
SELECT setval('person_sequence', (SELECT MAX(id) FROM person) + 1);
SELECT setval('role_sequence', (SELECT MAX(id) FROM role) + 1);
SELECT setval('screen_sequence', (SELECT MAX(id) FROM screen) + 1);
SELECT setval('student_sequence', (SELECT MAX(id) FROM student) + 1);
SELECT setval('team_sequence', (SELECT MAX(id) FROM team) + 1);
SELECT setval('withdraw_sequence', (SELECT MAX(id) FROM withdraw) + 1);