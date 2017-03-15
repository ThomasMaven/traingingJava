CREATE TABLE osoba
(
    id serial NOT NULL,
    imie character varying(100),
    nazwisko character varying(100),
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE osoba
    OWNER to postgres;
	
	
	
	
	
	
	
	
CREATE TABLE kontakt_list
(
    id_os integer NOT NULL,
    id_kontaktu integer NOT NULL,
    id serial NOT NULL,
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE kontakt_list
    OWNER to postgres;
	
	
	
	
	
	
	
CREATE TABLE kontakt
(
    id serial NOT NULL,
    typ_id integer NOT NULL,
    wartosc character varying(100),
    PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
);

ALTER TABLE kontakt
    OWNER to postgres;
	
	
	
	
	
	
CREATE TABLE typ
(
	id serial NOT NULL,
    wartosc character varying(100),
    CONSTRAINT typ_pkey PRIMARY KEY (id)
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE typ
    OWNER to postgres;