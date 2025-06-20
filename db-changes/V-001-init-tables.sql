CREATE TABLE shows (
    id SERIAL PRIMARY KEY,
    movie_name VARCHAR(100) NOT NULL,
    running_date timestamp NOT NULL,
    created timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE seat_reservations (
    id SERIAL PRIMARY KEY,
    shows_id SERIAL NOT NULL,
    seat INT NOT NULL,
    created timestamp NOT NULL DEFAULT NOW(),
    UNIQUE (shows_id, seat)
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

// Pre booked reservations

INSERT INTO public.seat_reservations(
	shows_id, seat)
	VALUES (2, 44);
INSERT INTO public.seat_reservations(
	shows_id, seat)
	VALUES (3, 20);
INSERT INTO public.seat_reservations(
	shows_id, seat)
	VALUES (3, 30);
