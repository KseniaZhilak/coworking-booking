CREATE TABLE users
(
    id    BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name  VARCHAR(100) NOT NULL,
    email VARCHAR(255) NOT NULL UNIQUE,
    role  VARCHAR(20)  NOT NULL,
    CONSTRAINT chk_users_role CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE workspaces
(
    id       BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    name     VARCHAR(100) NOT NULL UNIQUE,
    type     VARCHAR(20)  NOT NULL,
    capacity INT          NOT NULL,
    active   BOOLEAN      NOT NULL DEFAULT TRUE,
    CONSTRAINT chk_workspaces_type CHECK (type IN ('DESK', 'MEETING_ROOM')),
    CONSTRAINT chk_workspaces_capacity CHECK (capacity > 0)
);

CREATE TABLE bookings
(
    id           BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    user_id      BIGINT      NOT NULL REFERENCES users (id),
    workspace_id BIGINT      NOT NULL REFERENCES workspaces (id),
    start_time   TIMESTAMPTZ NOT NULL,
    end_time     TIMESTAMPTZ NOT NULL,
    status       VARCHAR(20) NOT NULL,
    created_at   TIMESTAMPTZ NOT NULL DEFAULT now(),
    CONSTRAINT chk_bookings_time CHECK (end_time > start_time),
    CONSTRAINT chk_bookings_status CHECK (status IN ('ACTIVE', 'CANCELLED'))
);