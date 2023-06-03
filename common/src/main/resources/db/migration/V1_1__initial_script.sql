CREATE TABLE if not exists public.users (
	user_id bigserial PRIMARY KEY,
	name VARCHAR ( 50 ) NOT NULL,
	email VARCHAR ( 255 ) UNIQUE NOT NULL,
	password VARCHAR ( 50 ) NOT NULL,
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null,
	constraint mail_check check (email like '%_@__%.__%')
);

create table if not exists public.c_roles (
    role_id bigserial primary key,
    role_name varchar( 50 ) unique not null
);

create table if not exists public.users_roles (
    users_roles_id bigserial primary key,
    user_id bigint not null references users(user_id),
    role_id bigint not null references c_roles(role_id)
);

create table if not exists public.survey (
	survey_id bigserial primary key,
	survey_name varchar (50) not null,
	owner_id bigint not null,
	access_codeword varchar (15), -- nullable
	responders_limit integer, -- nullable
	validity_date TIMESTAMP, --nullable
	time_limit bigint, --nullable
	--only_users_access
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null,
	FOREIGN KEY(owner_id) REFERENCES users(user_id)
);

create table if not exists public.response (
	response_id bigserial primary key,
	user_id bigint not null, -- nullable if ip add??
	survey_id bigint not null,
	response_status varchar(25) not null,
	completion_date TIMESTAMP,
	--address (ip)
	--answers jsonb not null,
	spent_time bigint,
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null,
	FOREIGN KEY(survey_id) REFERENCES "survey"(survey_id),
	FOREIGN KEY(user_id) REFERENCES users(user_id)
	/*FOREIGN KEY(status_id) REFERENCES "c_response_status"(status_id)*/
);

--create table if not exists public.c_question_type (
--	question_type_id bigserial primary key,
--	question_type varchar (30) unique not null,
--	created_on TIMESTAMP not null default now(),
--	changed_on TIMESTAMP not null default now(),
--	is_deleted BOOLEAN DEFAULT false not null
--);

create table if not exists public.question (
	question_id bigserial primary key,
	question_text varchar (200) not null,
	resource_path varchar(100),
	mandatory boolean default true not null,
	question_type varchar (50) not null,
	survey_id bigint not null,
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null,
	--FOREIGN KEY(question_type_id) REFERENCES c_question_type(question_type_id),
	FOREIGN KEY(survey_id) REFERENCES survey(survey_id)
);

create table if not exists public.question_field (
	question_field_id bigserial primary key,
	question_id bigint not null references question(question_id),
	field_text varchar (50), --nullable if text question type
	resource_path varchar(100),
	is_right boolean, --nullable
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null
);

create table if not exists public.question_answer (
	question_answer_id bigserial primary key,
	response_id bigint not null,
	question_id bigint not null,
	answer jsonb,
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null,
	FOREIGN KEY(response_id) REFERENCES "response"(response_id),
	FOREIGN KEY(question_id) REFERENCES "question"(question_id)
);

create table if not exists public.mailing (
	mailing_id bigserial primary key,
	mail VARCHAR ( 255 ) NOT NULL,
	survey_id bigint not null,
	created_on TIMESTAMP not null default now(),
	changed_on TIMESTAMP not null default now(),
	is_deleted BOOLEAN DEFAULT false not null,
	FOREIGN KEY(survey_id) REFERENCES "survey"(survey_id),
	constraint mail_check check (mail like '%_@__%.__%')
);