--Your integration test uses JdbcTemplate-based DAOs, so H2 must have all the tables they will query/insert/update.

CREATE TABLE trainers (
    trainer_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    contact VARCHAR(50),
    experience VARCHAR(100),
    skills VARCHAR(255),
    profile_pic VARCHAR(500),
    bio VARCHAR(1000),
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE internship_batches (
    batch_code BIGINT AUTO_INCREMENT PRIMARY KEY,
    batch_name VARCHAR(255),
    course_name VARCHAR(255),
    trainer_id BIGINT,
    start_date DATE,
    end_date DATE,
    total_seats INT,
    available_seats INT,
    CONSTRAINT fk_tid FOREIGN KEY (trainer_id)
        REFERENCES trainers(trainer_id)
);
    
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_name VARCHAR(255),
    email VARCHAR(255),
    password VARCHAR(255),
    dob DATE,
    contact_no VARCHAR(50),
    address VARCHAR(255),
    college_name VARCHAR(255),
    grade VARCHAR(50),
    major VARCHAR(100),
    team VARCHAR(100),
    batch_code BIGINT,
    start_date DATE,
    end_date DATE,
    graduating_year INT,
    resume VARCHAR(500),
   
    role VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_bc FOREIGN KEY (batch_code)
        REFERENCES internship_batches(batch_code)
);

CREATE TABLE stipend (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    user_name VARCHAR(255),
    payment_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_mode VARCHAR(50),
    amount DOUBLE,
    CONSTRAINT fk_uid FOREIGN KEY (user_id)
        REFERENCES users(user_id)
);

CREATE TABLE tasks (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    description VARCHAR(1000),
    trainer_id BIGINT,
    status VARCHAR(50),
    deadline DATE,
    upload_file VARCHAR(500),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_tasks_user FOREIGN KEY (user_id)
        REFERENCES users(user_id),
    CONSTRAINT fk_tasks_trainer FOREIGN KEY (trainer_id)
        REFERENCES trainers(trainer_id)
);

CREATE TABLE feedback (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    user_name VARCHAR(255),
    batch_code BIGINT,
    trainer_id BIGINT,
    trainer_name VARCHAR(255),
    date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    feedback VARCHAR(2000),
    rating INT,
    CONSTRAINT fk_f_user FOREIGN KEY (user_id)
        REFERENCES users(user_id),
    CONSTRAINT fk_f_train FOREIGN KEY (trainer_id)
        REFERENCES trainers(trainer_id),
    CONSTRAINT fk_f_batch FOREIGN KEY (batch_code)
        REFERENCES internship_batches(batch_code)
);

CREATE TABLE performance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    trainer_id BIGINT,
    batch_code BIGINT,
    trainer_name VARCHAR(255),
    remarks VARCHAR(2000),
    task_evaluation_score INT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    CONSTRAINT fk_p_user FOREIGN KEY (user_id)
        REFERENCES users(user_id),
    CONSTRAINT fk_p_train FOREIGN KEY (trainer_id)
        REFERENCES trainers(trainer_id),
    CONSTRAINT fk_p_batch FOREIGN KEY (batch_code)
        REFERENCES internship_batches(batch_code)
);

CREATE TABLE attendance (
    attendance_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT,
    batch_code BIGINT,
    date DATE,
    present BOOLEAN,
    CONSTRAINT fk_a_user FOREIGN KEY (user_id)
        REFERENCES users(user_id),
    CONSTRAINT fk_a_batch FOREIGN KEY (batch_code)
        REFERENCES internship_batches(batch_code)
);
