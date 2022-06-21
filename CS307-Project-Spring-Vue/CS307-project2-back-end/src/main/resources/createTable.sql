--
-- PostgreSQL database dump
--

-- Dumped from database version 12.1
-- Dumped by pg_dump version 12.1

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

SET default_tablespace = '';

SET default_table_access_method = heap;

--
-- Name: contract; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.contract (
    contract_number character varying(10) NOT NULL,
    contract_manager_name character varying(50) NOT NULL,
    enterprise character varying(200) NOT NULL,
    supply_center character varying(50) NOT NULL,
    ordernum bigint NOT NULL
);


ALTER TABLE public.contract OWNER TO postgres;

--
-- Name: enterprise; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.enterprise (
    name character varying(200) NOT NULL,
    country character varying(50) NOT NULL,
    city character varying(50),
    supply_center character varying(100) NOT NULL,
    industry character varying(50) NOT NULL
);


ALTER TABLE public.enterprise OWNER TO postgres;

--
-- Name: inventory; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.inventory (
    supply_center character varying(50) NOT NULL,
    product_model character varying(100) NOT NULL,
    importnum bigint NOT NULL,
    exportnum bigint NOT NULL,
    totalcost bigint NOT NULL,
    totalinterest bigint NOT NULL,
    stockinnum bigint NOT NULL,
    placeordernum bigint NOT NULL
);


ALTER TABLE public.inventory OWNER TO postgres;

--
-- Name: model; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.model (
    number character varying(10) NOT NULL,
    model character varying(100) NOT NULL,
    product character varying(100) NOT NULL,
    unit_price bigint NOT NULL
);


ALTER TABLE public.model OWNER TO postgres;

--
-- Name: order_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.order_record (
    contract_num character varying(10) NOT NULL,
    enterprise character varying(200) NOT NULL,
    product_model character varying(100) NOT NULL,
    quantity bigint NOT NULL,
    contract_manager character varying(8) NOT NULL,
    contract_date date NOT NULL,
    estimated_delivery_date date NOT NULL,
    lodgement_date date NOT NULL,
    salesman_num character varying(8) NOT NULL,
    contract_type character varying(50) NOT NULL
);


ALTER TABLE public.order_record OWNER TO postgres;

--
-- Name: staff; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.staff (
    name character varying(50) NOT NULL,
    age bigint NOT NULL,
    gender character varying(50) NOT NULL,
    number character varying(8) NOT NULL,
    supply_center character varying(50) NOT NULL,
    mobile_number character varying(11) NOT NULL,
    type character varying(50) NOT NULL
);


ALTER TABLE public.staff OWNER TO postgres;

--
-- Name: stock_in_record; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.stock_in_record (
    supply_center character varying(100) NOT NULL,
    product_model character varying(100) NOT NULL,
    supply_staff character varying(8) NOT NULL,
    date date NOT NULL,
    purchase_price bigint NOT NULL,
    quantity bigint NOT NULL
);


ALTER TABLE public.stock_in_record OWNER TO postgres;

--
-- Name: supply_center; Type: TABLE; Schema: public; Owner: postgres
--

CREATE TABLE public.supply_center (
    name character varying(100) NOT NULL
);


ALTER TABLE public.supply_center OWNER TO postgres;

--
-- Data for Name: contract; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.contract (contract_number, contract_manager_name, enterprise, supply_center, ordernum) FROM stdin;
\.


--
-- Data for Name: enterprise; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.enterprise (name, country, city, supply_center, industry) FROM stdin;
\.


--
-- Data for Name: inventory; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.inventory (supply_center, product_model, importnum, exportnum, totalcost, totalinterest, stockinnum, placeordernum) FROM stdin;
\.


--
-- Data for Name: model; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.model (number, model, product, unit_price) FROM stdin;
\.


--
-- Data for Name: order_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.order_record (contract_num, enterprise, product_model, quantity, contract_manager, contract_date, estimated_delivery_date, lodgement_date, salesman_num, contract_type) FROM stdin;
\.


--
-- Data for Name: staff; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.staff (name, age, gender, number, supply_center, mobile_number, type) FROM stdin;
\.


--
-- Data for Name: stock_in_record; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.stock_in_record (supply_center, product_model, supply_staff, date, purchase_price, quantity) FROM stdin;
\.


--
-- Data for Name: supply_center; Type: TABLE DATA; Schema: public; Owner: postgres
--

COPY public.supply_center (name) FROM stdin;
\.


--
-- Name: contract contract_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.contract
    ADD CONSTRAINT contract_pkey PRIMARY KEY (contract_number);


--
-- Name: enterprise enterprise_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.enterprise
    ADD CONSTRAINT enterprise_pkey PRIMARY KEY (name);


--
-- Name: inventory inventory_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.inventory
    ADD CONSTRAINT inventory_unique UNIQUE (supply_center, product_model);


--
-- Name: model model_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.model
    ADD CONSTRAINT model_pkey PRIMARY KEY (model);


--
-- Name: order_record order_record_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.order_record
    ADD CONSTRAINT order_record_unique UNIQUE (contract_num, enterprise, product_model, salesman_num);


--
-- Name: staff staff_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.staff
    ADD CONSTRAINT staff_pkey PRIMARY KEY (number);


--
-- Name: stock_in_record stock_in_record_unique; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.stock_in_record
    ADD CONSTRAINT stock_in_record_unique UNIQUE (supply_center, product_model, supply_staff);


--
-- Name: supply_center supply_center_pkey; Type: CONSTRAINT; Schema: public; Owner: postgres
--

ALTER TABLE ONLY public.supply_center
    ADD CONSTRAINT supply_center_pkey PRIMARY KEY (name);


--
-- PostgreSQL database dump complete
--

