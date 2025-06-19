CREATE TABLE shows (
    id SERIAL PRIMARY KEY,
    movie_name VARCHAR(100) NOT NULL,
    running_date timestamp NOT NULL,
    created timestamp NOT NULL DEFAULT NOW()
);

CREATE TABLE seating_reservations (
    id SERIAL PRIMARY KEY,
    shows_id SERIAL NOT NULL,
    seating INT NOT NULL,
    created timestamp NOT NULL DEFAULT NOW()
);

// Populating shows with 5 shows on two days

INSERT INTO public.shows(
	movie_name, running_date, created)
	VALUES ('TopGun', '2026-06-10 19:30:00', now());

INSERT INTO public.shows(
	movie_name, running_date, created)
	VALUES ('TopGun', '2026-06-10 22:30:00', now());

INSERT INTO public.shows(
	movie_name, running_date, created)
	VALUES ('TopGun', '2026-06-11 16:30:00', now());

INSERT INTO public.shows(
	movie_name, running_date, created)
	VALUES ('TopGun', '2026-06-11 19:30:00', now());

INSERT INTO public.shows(
	movie_name, running_date, created)
	VALUES ('TopGun', '2026-06-11 22:30:00', now());

// Pre booked reservations

INSERT INTO public.seating_reservations(
	id, shows_id, seating, created)
	VALUES (1, 2, 44, now());
INSERT INTO public.seating_reservations(
	id, shows_id, seating, created)
	VALUES (1, 3, 30, now());
