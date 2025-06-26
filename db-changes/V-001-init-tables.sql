CREATE USER carsten WITH PASSWORD 'verysecretpassword';
GRANT ALL PRIVILEGES ON DATABASE cinema_booking TO carsten;
GRANT USAGE ON SCHEMA public TO carsten;
GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO carsten;
GRANT SELECT, INSERT, UPDATE, DELETE ON ALL TABLES IN SCHEMA public TO carsten;
ALTER DEFAULT PRIVILEGES IN SCHEMA public
GRANT USAGE, SELECT, UPDATE ON SEQUENCE seat_reservations_id_seq TO carsten;

CREATE TABLE shows (
    id SERIAL PRIMARY KEY,
    movie_name VARCHAR(100) NOT NULL,
    running_date TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW()
);

CREATE TABLE seat_reservations (
    id SERIAL PRIMARY KEY,
    shows_id SERIAL NOT NULL,
    seat INT NOT NULL,
    created TIMESTAMP WITHOUT TIME ZONE NOT NULL DEFAULT NOW(),
    UNIQUE (shows_id, seat),
    FOREIGN KEY (shows_id) REFERENCES shows(id) ON DELETE CASCADE
);

// Populating shows with 5 shows on two days

INSERT INTO public.shows(
	movie_name, running_date)
	VALUES ('TopGun', '2026-06-10 19:30:00');

INSERT INTO public.shows(
	movie_name, running_date)
	VALUES ('TopGun', '2026-06-10 22:30:00');

INSERT INTO public.shows(
	movie_name, running_date)
	VALUES ('TopGun', '2026-06-11 16:30:00');

INSERT INTO public.shows(
	movie_name, running_date)
	VALUES ('TopGun', '2026-06-11 19:30:00');

INSERT INTO public.shows(
	movie_name, running_date)
	VALUES ('TopGun', '2026-06-11 22:30:00');

## Pre booked reservations

INSERT INTO public.seat_reservations(
	shows_id, seat)
	VALUES (2, 44);
INSERT INTO public.seat_reservations(
	shows_id, seat)
	VALUES (3, 20);
INSERT INTO public.seat_reservations(
	shows_id, seat)
	VALUES (3, 30);
