create database if not exists mydb;

-- public.note definition

-- Drop table

-- DROP TABLE public.note;

CREATE TABLE public.note (
	id_nota serial4 NOT NULL,
	titolo text DEFAULT 'Nota '::text || to_char(now(), 'YYYY-MM-DD HH24:MI'::text) NULL,
	testo text NOT NULL,
	data_creazione timestamp DEFAULT now() NULL,
	id_utente int4 NULL,
	CONSTRAINT note_pkey PRIMARY KEY (id_nota)
);


-- public.note foreign keys

ALTER TABLE public.note ADD CONSTRAINT fk_utente FOREIGN KEY (id_utente) REFERENCES public.utenti(id_utente);

-- public.utenti definition

-- Drop table

-- DROP TABLE public.utenti;

CREATE TABLE public.utenti (
	id_utente serial4 NOT NULL,
	username varchar(50) NOT NULL,
	"password" varchar(255) NOT NULL,
	email varchar(100) NOT NULL,
	nome varchar(50) NOT NULL,
	cognome varchar(50) NOT NULL,
	data_creazione timestamp DEFAULT now() NULL,
	CONSTRAINT utenti_email_key UNIQUE (email),
	CONSTRAINT utenti_pkey PRIMARY KEY (id_utente),
	CONSTRAINT utenti_username_key UNIQUE (username)
);

INSERT INTO public.utenti
(id_utente, username, "password", email, nome, cognome, data_creazione)
VALUES(nextval('utenti_id_utente_seq'::regclass), 'admin', 'admin', 'admin@me.com', 'admin', 'admin', now());

INSERT INTO public.note
(id_nota, titolo, testo, data_creazione, id_utente)
VALUES(nextval('note_id_nota_seq'::regclass), 'Example title'::text, 'Example text'::text, now(), 1);
