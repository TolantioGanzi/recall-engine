CREATE TABLE public.attempts (
                                 problem_id integer NOT NULL,
                                 priority_score double precision,
                                 pattern_score integer,
                                 implementation_score integer,
                                 complexity_score integer,
                                 debug_score integer,
                                 hints_used integer,
                                 days_since_attempt bigint,
                                 result integer,
                                 difficulty integer,
                                 actual_time integer,
                                 pattern character varying(255),
                                 optimal_solution text,
                                 attempt_date date,
                                 id bigint NOT NULL
);

CREATE SEQUENCE public.attempts_id_seq
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

ALTER SEQUENCE public.attempts_id_seq OWNED BY public.attempts.id;

CREATE TABLE public.problems (
                                 id integer NOT NULL,
                                 title character varying(255),
                                 difficulty integer,
                                 topics text[],
                                 premium_only boolean DEFAULT false NOT NULL,
                                 similar_questions text
);

CREATE TABLE public.user_problems (
                                      problem_number integer NOT NULL,
                                      confidence integer,
                                      step integer,
                                      difficulty integer,
                                      title character varying(255),
                                      pattern character varying(255),
                                      fail_log text,
                                      date_added date,
                                      next_recall date
);

ALTER TABLE ONLY public.attempts ALTER COLUMN id SET DEFAULT nextval('public.attempts_id_seq'::regclass);

ALTER TABLE ONLY public.attempts
    ADD CONSTRAINT attempts_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.problems
    ADD CONSTRAINT problems_pkey PRIMARY KEY (id);

ALTER TABLE ONLY public.user_problems
    ADD CONSTRAINT user_problems_pkey PRIMARY KEY (problem_number);