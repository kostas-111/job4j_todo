CREATE TABLE IF NOT EXISTS tasks (
   id SERIAL PRIMARY KEY,
   title TEXT,
   description TEXT,
   created TIMESTAMP default now(),
   done BOOLEAN default false
);