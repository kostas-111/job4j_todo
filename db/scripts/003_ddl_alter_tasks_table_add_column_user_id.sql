ALTER TABLE tasks
ADD column user_id INT REFERENCES users(id);