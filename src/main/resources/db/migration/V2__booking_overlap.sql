CREATE EXTENSION IF NOT EXISTS btree_gist;

ALTER TABLE bookings
    ADD CONSTRAINT excl_bookings_overlap
        EXCLUDE USING gist (
            workspace_id WITH =,
            tstzrange(start_time, end_time) WITH &&
        ) WHERE (status = 'ACTIVE');

CREATE INDEX idx_bookings_user_id ON bookings (user_id);
