CREATE DATABASE IF NOT EXISTS ortho_recover_ai;
USE ortho_recover_ai;

-- 1. users table
CREATE TABLE IF NOT EXISTS users (
    id INT AUTO_INCREMENT PRIMARY KEY,
    role ENUM('physiotherapist', 'patient') NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(255) UNIQUE,
    phone VARCHAR(20) UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- 2. physiotherapists table
CREATE TABLE IF NOT EXISTS physiotherapists (
    user_id INT PRIMARY KEY,
    qualification VARCHAR(255),
    specialization VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 3. patients table
CREATE TABLE IF NOT EXISTS patients (
    user_id INT PRIMARY KEY,
    age INT,
    gender ENUM('male', 'female', 'other'),
    surgery_type VARCHAR(255),
    physiotherapist_id INT,
    connection_code VARCHAR(10) UNIQUE,
    connected BOOLEAN DEFAULT FALSE,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (physiotherapist_id) REFERENCES physiotherapists(user_id) ON DELETE SET NULL
) ENGINE=InnoDB;

-- 4. exercise_plans table
CREATE TABLE IF NOT EXISTS exercise_plans (
    id INT AUTO_INCREMENT PRIMARY KEY,
    physiotherapist_id INT NOT NULL,
    patient_id INT NOT NULL,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    start_date DATE,
    end_date DATE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (physiotherapist_id) REFERENCES physiotherapists(user_id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 5. exercises table
CREATE TABLE IF NOT EXISTS exercises (
    id INT AUTO_INCREMENT PRIMARY KEY,
    plan_id INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    video_url VARCHAR(255),
    repetitions VARCHAR(255),
    instructions TEXT,
    FOREIGN KEY (plan_id) REFERENCES exercise_plans(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 6. exercise_logs table
CREATE TABLE IF NOT EXISTS exercise_logs (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    exercise_id INT NOT NULL,
    completed BOOLEAN DEFAULT TRUE,
    completed_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(user_id) ON DELETE CASCADE,
    FOREIGN KEY (exercise_id) REFERENCES exercises(id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 7. rom_sessions table
CREATE TABLE IF NOT EXISTS rom_sessions (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    joint_name VARCHAR(100),
    angle_value FLOAT,
    session_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (patient_id) REFERENCES patients(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 8. swelling_uploads table
CREATE TABLE IF NOT EXISTS swelling_uploads (
    id INT AUTO_INCREMENT PRIMARY KEY,
    patient_id INT NOT NULL,
    image_path VARCHAR(255) NOT NULL,
    upload_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    physiotherapist_note TEXT,
    FOREIGN KEY (patient_id) REFERENCES patients(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;

-- 9. physiotherapist_notes table
CREATE TABLE IF NOT EXISTS physiotherapist_notes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    physiotherapist_id INT NOT NULL,
    patient_id INT NOT NULL,
    note TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (physiotherapist_id) REFERENCES physiotherapists(user_id) ON DELETE CASCADE,
    FOREIGN KEY (patient_id) REFERENCES patients(user_id) ON DELETE CASCADE
) ENGINE=InnoDB;
