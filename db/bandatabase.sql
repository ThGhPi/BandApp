-- =============================
--  SCHEMA DE LA BASE bandatabase
--  VERSION INITIALE
-- =============================

-- ============= DROP QUERIES ==================

DROP TYPE IF EXISTS attendance_choice_enum;
DROP TYPE IF EXISTS role_enum;
DROP TYPE IF EXISTS file_type_enum;
DROP TYPE IF EXISTS instrument_key_enum;

DROP TABLE IF EXISTS
    answer,
    player,
    participation,
    attendance,
    programme,
    choice,
    survey,
    invoice_line,
    invoice,
    event,
    event_type,
    organisation,
    person,
    place,
    place_type,
    city,
    file_info,
    work_group,
    instrument,
    piece
CASCADE;

DROP TYPE IF EXISTS attendance_choice_enum;
DROP TYPE IF EXISTS role_enum;
DROP TYPE IF EXISTS file_type_enum;
DROP TYPE IF EXISTS key_enum;
DROP TYPE IF EXISTS group_type_enum;


-- ============= ENUM CREATION =================

CREATE TYPE group_type_enum AS ENUM ('group', 'other');
CREATE TYPE key_enum AS ENUM ('ut', 'f', 'b_flat', 'e_flat');
CREATE TYPE file_type_enum AS ENUM ('audio', 'score', 'photo', 'invoice');
CREATE TYPE role_enum AS ENUM ('admin', 'org', 'arr', 'member');
CREATE TYPE attendance_choice_enum AS ENUM ('yes', 'no', 'maybe');

-- ============= TABLE CREATION =================

CREATE TABLE piece (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL UNIQUE,
    composer VARCHAR(50) NOT NULL,
    number VARCHAR(3) UNIQUE
);

CREATE TABLE instrument (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    key key_enum NOT NULL
);

CREATE TABLE survey (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    question VARCHAR(255) NOT NULL,
    scheduled_end DATE NOT NULL,
    multiplicity BOOLEAN NOT NULL
);

CREATE TABLE choice (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    complement VARCHAR(255),
    url VARCHAR(255),
    survey_id BIGINT NOT NULL REFERENCES survey(id)
);

CREATE TABLE work_group (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE,
    creation_date DATE NOT NULL,
    group_type group_type_enum NOT NULL,
    goal VARCHAR(50),
    details VARCHAR(255),
    scheduled_end DATE
);

CREATE TABLE file_info (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    file_url VARCHAR(255) NOT NULL UNIQUE,
    file_type file_type_enum NOT NULL,
    group_id BIGINT REFERENCES work_group(id),
    piece_id BIGINT REFERENCES piece(id)
);

CREATE TABLE city (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    postcode CHAR(5) NOT NULL
);

CREATE TABLE place_type (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE place (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50),
    address VARCHAR(255) NOT NULL,
    address_details VARCHAR(255),
    latitude DOUBLE PRECISION,
    longitude DOUBLE PRECISION,
    capacity INT,
    photo_id BIGINT REFERENCES file_info(id),
    city_id BIGINT NOT NULL REFERENCES city(id),
    type_id BIGINT REFERENCES place_type(id)
);

CREATE TABLE person (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    lastname VARCHAR(50) NOT NULL,
    firstname VARCHAR(50) NOT NULL,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(50) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role role_enum NOT NULL,
    phone_number CHAR(12) NOT NULL,
    address_id BIGINT REFERENCES place(id)
);

CREATE TABLE organisation (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    siret_number VARCHAR(14) UNIQUE,
    email VARCHAR(50) UNIQUE,
    address_id BIGINT REFERENCES place(id)
);

CREATE TABLE event_type (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name VARCHAR(50) NOT NULL UNIQUE
);

CREATE TABLE event (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    title VARCHAR(100) NOT NULL,
    event_date DATE NOT NULL,
    event_start TIME NOT NULL,
    event_end TIME,
    rdv TIME,
    details VARCHAR(255),
    group_id biGINT REFERENCES work_group(id),
    place_id BIGINT NOT NULL REFERENCES place(id),
    organisation_id BIGINT NOT NULL REFERENCES organisation(id),
    type_id BIGINT REFERENCES event_type(id)
);

CREATE TABLE invoice (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    number CHAR(6) NOT NULL UNIQUE,
    issue_date DATE NOT NULL,
    file_id BIGINT NOT NULL UNIQUE REFERENCES file_info(id),
    organisation_id BIGINT NOT NULL REFERENCES organisation(id)
);

CREATE TABLE invoice_line (
    id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    rate DOUBLE PRECISION NOT NULL,
    event_id BIGINT NOT NULL UNIQUE REFERENCES event(id),
    invoice_id BIGINT NOT NULL REFERENCES invoice(id)
);

CREATE TABLE programme (
    event_id BIGINT REFERENCES event(id),
    piece_id BIGINT REFERENCES piece(id),
    running_order INT,
    PRIMARY KEY (event_id, piece_id)
);

CREATE TABLE attendance (
    person_id BIGINT REFERENCES person(id),
    event_id BIGINT REFERENCES event(id),
    attendance_choice attendance_choice_enum,
    PRIMARY KEY (person_id, event_id)
);

CREATE TABLE participation (
    person_id BIGINT REFERENCES person(id),
    group_id BIGINT REFERENCES work_group(id),
    PRIMARY KEY (person_id, group_id)
);

CREATE TABLE player (
    musician_id BIGINT REFERENCES person(id),
    instrument_id BIGINT REFERENCES instrument(id),
    PRIMARY KEY (musician_id, instrument_id)
);

CREATE TABLE answer (
    person_id BIGINT REFERENCES person(id),
    choice_id BIGINT REFERENCES choice(id),
    PRIMARY KEY (person_id, choice_id)
);
