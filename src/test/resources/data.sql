INSERT INTO trainers VALUES
(1, 'John Miller', 'john@trainer.com', 'pass123', '9876543210', '5 Years',
 'Java, Spring', NULL, 'Senior Trainer', 'TRAINER', CURRENT_TIMESTAMP);

INSERT INTO internship_batches VALUES
(1001, 'Java Full Stack', 'Java', 1, '2025-12-01', '2026-03-01', 30, 25);

INSERT INTO users VALUES
(1, 'Sravya', 'sravya@user.com',
'$2a$10$Dow1YOGS2/MEGLjmVnTQPuGcDeihdj5Z8SGvtQvV4H14R/2uOeGdy',
'2002-05-10', '9876543210', 'Chennai', 'Vel Tech', 'A', 'CSE',
'Backend', 1001, '2025-01-01', '2025-03-01', 2025, 'resume.pdf',
 'USER', CURRENT_TIMESTAMP);

INSERT INTO users (user_id, user_name, email, password, role)
VALUES (99, 'Test Admin', 'existing@admin.com', '$2a$10$Dow1YOGS2/MEGLjmVnTQPuGcDeihdj5Z8SGvtQvV4H14R/2uOeGdy', 'ADMIN');

INSERT INTO tasks VALUES
(1, 1, 'Build REST API', 1, 'PENDING', '2025-12-04', 'file.pdf', CURRENT_TIMESTAMP);

INSERT INTO attendance VALUES
(1, 1, 1001, '2025-12-03', TRUE);

INSERT INTO performance VALUES
(1, 1, 1, 1001, 'John Miller', 'Good progress', 90, CURRENT_TIMESTAMP);

INSERT INTO feedback VALUES
(1, 1, 'Sravya', 1001, 1, 'John Miller', CURRENT_TIMESTAMP, 'Very helpful trainer', 5);

INSERT INTO stipend VALUES
(1, 1, 'Sravya', CURRENT_TIMESTAMP, 'Cash', 3000.00);