--DROP DATABASE IF EXISTS test2_db;

--CREATE DATABASE test2_db
--    WITH
--    OWNER = postgres
--    ENCODING = 'UTF8'
--    LC_COLLATE = 'Russian_Russia.1251'
--    LC_CTYPE = 'Russian_Russia.1251'
--    TABLESPACE = pg_default
--    CONNECTION LIMIT = -1;

CREATE TABLE IF NOT EXISTS public.books
(
    id bigserial primary key,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    author character varying COLLATE pg_catalog."default" NOT NULL,
    year integer NOT NULL
);
CREATE TABLE IF NOT EXISTS public.readers
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    last_name character varying COLLATE pg_catalog."default" NOT NULL,
    name character varying COLLATE pg_catalog."default" NOT NULL,
    patronymic character varying COLLATE pg_catalog."default",
    gender character varying COLLATE pg_catalog."default",
    age integer NOT NULL,
    CONSTRAINT readers_pkey PRIMARY KEY (id)
);
CREATE TABLE IF NOT EXISTS public.taken_books
(
    id bigint NOT NULL GENERATED ALWAYS AS IDENTITY ( INCREMENT 1 START 1 MINVALUE 1 MAXVALUE 9223372036854775807 CACHE 1 ),
    book_id bigint NOT NULL,
    reader_id bigint NOT NULL,
    issue_date date DEFAULT now(),
    planned_return_date date,
    CONSTRAINT taken_books_pkey PRIMARY KEY (id),
    CONSTRAINT dist_id_zipcode_key UNIQUE (book_id),
    CONSTRAINT book_fkey FOREIGN KEY (book_id)
        REFERENCES public.books (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID,
    CONSTRAINT reader_fkey FOREIGN KEY (reader_id)
        REFERENCES public.readers (id) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
        NOT VALID
)