--
-- PostgreSQL database dump
--

-- Dumped from database version 10.9
-- Dumped by pg_dump version 11.3

-- Started on 2020-02-26 22:33:23

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

DROP DATABASE tdlpp;
--
-- TOC entry 2844 (class 1262 OID 16393)
-- Name: tdlpp; Type: DATABASE; Schema: -; Owner: postgres
--

CREATE DATABASE tdlpp WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'Spanish_Paraguay.1252' LC_CTYPE = 'Spanish_Paraguay.1252';


ALTER DATABASE tdlpp OWNER TO postgres;

\connect tdlpp

SET statement_timeout = 0;
SET lock_timeout = 0;
SET idle_in_transaction_session_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SELECT pg_catalog.set_config('search_path', '', false);
SET check_function_bodies = false;
SET xmloption = content;
SET client_min_messages = warning;
SET row_security = off;

--
-- TOC entry 217 (class 1255 OID 16544)
-- Name: actualizar_estado_actividad(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE or REPLACE FUNCTION public.actualizar_estado_actividad()
    RETURNS trigger
    LANGUAGE 'plpgsql'
    COST 100
    VOLATILE NOT LEAKPROOF 
AS $BODY$
  DECLARE
  v_actividad integer;
  v_progreso integer;
  BEGIN
	
	IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE' ) THEN
		select actividad_id into v_actividad FROM public.tarea
		where tarea_id=NEW.tarea_id;
	ELSEIF (TG_OP = 'DELETE') THEN
		select actividad_id into v_actividad FROM public.tarea
		where tarea_id=OLD.tarea_id;
	  END IF;
	  
	SELECT round(sum(CASE WHEN estado='A' THEN 0 WHEN 
estado='F' THEN 100 ELSE 0 END)/count(estado),0) into v_progreso
	FROM public.tarea where actividad_id=v_actividad;
	
	update public.actividad set progreso=v_progreso where
	actividad_id=v_actividad;
	
	 IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE' ) THEN
		RETURN NEW;
		ELSEIF (TG_OP = 'DELETE') THEN
       RETURN NULL;
    
    END IF;
		
  END;
$BODY$;

ALTER FUNCTION public.actualizar_estado_actividad()
    OWNER TO postgres;


--
-- TOC entry 204 (class 1255 OID 16502)
-- Name: actualizar_estado_proyecto(); Type: FUNCTION; Schema: public; Owner: postgres
--

CREATE FUNCTION public.actualizar_estado_proyecto() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
  DECLARE
  v_proyecto integer;
  v_progreso integer;
  BEGIN

	select proyecto_id into v_proyecto FROM public.actividad
	where actividad_id=NEW.actividad_id;
	
	SELECT round(sum(progreso)/count(progreso),0) into v_progreso
	FROM public.actividad where proyecto_id=v_proyecto;
	
	update public.proyecto set progreso=v_progreso where
	proyecto_id=v_proyecto;
	
	 IF (TG_OP = 'INSERT' OR TG_OP = 'UPDATE' ) THEN
		RETURN NEW;
		ELSEIF (TG_OP = 'DELETE') THEN
       RETURN NULL;
    
    END IF;
		
  END;
$$;


ALTER FUNCTION public.actualizar_estado_proyecto() OWNER TO postgres;

--
-- TOC entry 197 (class 1259 OID 16472)
-- Name: actividad_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.actividad_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.actividad_id_seq OWNER TO postgres;

SET default_tablespace = '';

SET default_with_oids = false;

--
-- TOC entry 199 (class 1259 OID 16480)
-- Name: actividad; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.actividad (
    actividad_id integer DEFAULT nextval('public.actividad_id_seq'::regclass) NOT NULL,
    proyecto_id integer,
    nombre character varying(40),
    fecha_programada date,
    fecha_inicio date,
    fecha_fin date,
    fecha_limite date,
    progreso smallint DEFAULT 0 NOT NULL,
    comentarios character varying(200),
    responsable_id integer
);


ALTER TABLE public.actividad OWNER TO postgres;

--
-- TOC entry 201 (class 1259 OID 16507)
-- Name: persona; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.persona (
    persona_id integer NOT NULL,
    nombre character varying(80),
    email character varying(80),
    rol character(1) DEFAULT 'U'::bpchar
);


ALTER TABLE public.persona OWNER TO postgres;

--
-- TOC entry 200 (class 1259 OID 16505)
-- Name: persona_persona_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.persona_persona_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.persona_persona_id_seq OWNER TO postgres;

--
-- TOC entry 2846 (class 0 OID 0)
-- Dependencies: 200
-- Name: persona_persona_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.persona_persona_id_seq OWNED BY public.persona.persona_id;


--
-- TOC entry 196 (class 1259 OID 16470)
-- Name: proyecto_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.proyecto_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.proyecto_id_seq OWNER TO postgres;

--
-- TOC entry 198 (class 1259 OID 16474)
-- Name: proyecto; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.proyecto (
    proyecto_id integer DEFAULT nextval('public.proyecto_id_seq'::regclass) NOT NULL,
    nombre character varying(40),
    fecha_inicio date,
    fecha_limite date,
    fecha_fin date,
    progreso smallint DEFAULT 0 NOT NULL,
    estado character(1) DEFAULT 'A'::bpchar NOT NULL,
    comentarios character varying(200),
    dueno_id integer,
    CONSTRAINT proyecto_ck1 CHECK ((estado = ANY (ARRAY['A'::bpchar, 'P'::bpchar, 'F'::bpchar, 'C'::bpchar])))
);


ALTER TABLE public.proyecto OWNER TO postgres;

--
-- TOC entry 203 (class 1259 OID 16520)
-- Name: tarea; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.tarea (
    tarea_id integer NOT NULL,
    nombre character varying(40),
    fecha_programada date,
    fecha_inicio date,
    fecha_fin date,
    fecha_limite date,
    comentarios character varying(200),
    estado character(1) DEFAULT 'A'::bpchar NOT NULL,
    responsable_id integer,
    actividad_id integer
);


ALTER TABLE public.tarea OWNER TO postgres;

--
-- TOC entry 202 (class 1259 OID 16518)
-- Name: tarea_tarea_id_seq; Type: SEQUENCE; Schema: public; Owner: postgres
--

CREATE SEQUENCE public.tarea_tarea_id_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


ALTER TABLE public.tarea_tarea_id_seq OWNER TO postgres;

--
-- TOC entry 2847 (class 0 OID 0)
-- Dependencies: 202
-- Name: tarea_tarea_id_seq; Type: SEQUENCE OWNED BY; Schema: public; Owner: postgres
--

ALTER SEQUENCE public.tarea_tarea_id_seq OWNED BY public.tarea.tarea_id;


--
-- TOC entry 2697 (class 2604 OID 16510)
-- Name: persona persona_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona ALTER COLUMN persona_id SET DEFAULT nextval('public.persona_persona_id_seq'::regclass);


--
-- TOC entry 2700 (class 2604 OID 16523)
-- Name: tarea tarea_id; Type: DEFAULT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea ALTER COLUMN tarea_id SET DEFAULT nextval('public.tarea_tarea_id_seq'::regclass);


--
-- TOC entry 2696 (class 2606 OID 16503)
-- Name: actividad actividad_ck; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.actividad
    ADD CONSTRAINT actividad_ck CHECK (((progreso >= 0) AND (progreso <= 100))) NOT VALID;


--
-- TOC entry 2706 (class 2606 OID 16485)
-- Name: actividad actividad_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT actividad_pkey PRIMARY KEY (actividad_id);


--
-- TOC entry 2699 (class 2606 OID 16548)
-- Name: persona persona_ck1; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.persona
    ADD CONSTRAINT persona_ck1 CHECK ((rol = ANY (ARRAY['U'::bpchar, 'A'::bpchar]))) NOT VALID;


--
-- TOC entry 2708 (class 2606 OID 16512)
-- Name: persona persona_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.persona
    ADD CONSTRAINT persona_pkey PRIMARY KEY (persona_id);


--
-- TOC entry 2704 (class 2606 OID 16479)
-- Name: proyecto proyecto_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proyecto
    ADD CONSTRAINT proyecto_pkey PRIMARY KEY (proyecto_id);


--
-- TOC entry 2702 (class 2606 OID 16532)
-- Name: tarea tarea_ck1; Type: CHECK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE public.tarea
    ADD CONSTRAINT tarea_ck1 CHECK ((estado = ANY (ARRAY['A'::bpchar, 'F'::bpchar]))) NOT VALID;


--
-- TOC entry 2710 (class 2606 OID 16525)
-- Name: tarea tarea_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_pkey PRIMARY KEY (tarea_id);


--
-- TOC entry 2717 (class 2620 OID 16545)
-- Name: tarea actualizar_estado_actividad; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER actualizar_estado_actividad AFTER INSERT OR DELETE OR UPDATE ON public.tarea FOR EACH ROW EXECUTE PROCEDURE public.actualizar_estado_actividad();


--
-- TOC entry 2716 (class 2620 OID 16504)
-- Name: actividad actualizar_estado_proyecto; Type: TRIGGER; Schema: public; Owner: postgres
--

CREATE TRIGGER actualizar_estado_proyecto AFTER INSERT OR DELETE OR UPDATE ON public.actividad FOR EACH ROW EXECUTE PROCEDURE public.actualizar_estado_proyecto();


--
-- TOC entry 2712 (class 2606 OID 16492)
-- Name: actividad actividad_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT actividad_fk1 FOREIGN KEY (proyecto_id) REFERENCES public.proyecto(proyecto_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2713 (class 2606 OID 16513)
-- Name: actividad actividad_fk2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.actividad
    ADD CONSTRAINT actividad_fk2 FOREIGN KEY (responsable_id) REFERENCES public.persona(persona_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2711 (class 2606 OID 16534)
-- Name: proyecto proyecto_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.proyecto
    ADD CONSTRAINT proyecto_fk1 FOREIGN KEY (dueno_id) REFERENCES public.persona(persona_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2714 (class 2606 OID 16526)
-- Name: tarea tarea_fk1; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_fk1 FOREIGN KEY (responsable_id) REFERENCES public.persona(persona_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2715 (class 2606 OID 16539)
-- Name: tarea tarea_fk2; Type: FK CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.tarea
    ADD CONSTRAINT tarea_fk2 FOREIGN KEY (actividad_id) REFERENCES public.actividad(actividad_id) ON UPDATE RESTRICT ON DELETE RESTRICT;


--
-- TOC entry 2845 (class 0 OID 0)
-- Dependencies: 2844
-- Name: DATABASE tdlpp; Type: ACL; Schema: -; Owner: postgres
--

GRANT ALL ON DATABASE tdlpp TO tdlpp_user;


-- Completed on 2020-02-26 22:33:23

--
-- PostgreSQL database dump complete
--

