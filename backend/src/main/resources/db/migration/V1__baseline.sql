CREATE TABLE game_sessions
(
    id                 uuid                  NOT NULL,
    book_id            uuid,
    current_section_id integer               NOT NULL,
    username           character varying(40) NOT NULL,
    health_points      integer               NOT NULL
);

ALTER TABLE game_sessions
    ADD CONSTRAINT game_sessions_pkey PRIMARY KEY (id);
