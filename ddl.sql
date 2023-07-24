CREATE TABLE scott.admin (
    id         VARCHAR2(30 BYTE) NOT NULL,
    admin_pw   VARCHAR2(200 BYTE) NOT NULL,
    admin_name VARCHAR2(20 BYTE) NOT NULL
);


CREATE UNIQUE INDEX scott.pk_admin ON
    scott.admin (
        id
    ASC );

ALTER TABLE scott.admin
    ADD CONSTRAINT pk_admin PRIMARY KEY ( id )
        USING INDEX scott.pk_admin;

CREATE TABLE scott.answer (
    id           NUMBER NOT NULL,
    inquiry_id   NUMBER NOT NULL,
    admin_id     VARCHAR2(30) NOT NULL,
    answer       VARCHAR2(4000 BYTE) NOT NULL,
    created_time DATE NOT NULL
);

CREATE UNIQUE INDEX scott.pk_answer ON
    scott.answer (
        id
    ASC );

ALTER TABLE scott.answer
    ADD CONSTRAINT pk_answer PRIMARY KEY ( id )
        USING INDEX scott.pk_answer;

CREATE TABLE scott.inquiry (
    id           NUMBER NOT NULL,
    user_id      VARCHAR2(30 BYTE) NOT NULL,
    title        VARCHAR2(500 BYTE) NOT NULL,
    content      VARCHAR2(4000 BYTE) NOT NULL,
    created_time DATE NOT NULL
);

CREATE UNIQUE INDEX scott.pk_inquiry ON
    scott.inquiry (
        id
    ASC );

ALTER TABLE scott.inquiry
    ADD CONSTRAINT pk_inquiry PRIMARY KEY ( id )
        USING INDEX scott.pk_inquiry;

CREATE TABLE scott.actor (
    id            VARCHAR2(100 BYTE) NOT NULL,
    name          VARCHAR2(100 BYTE),
    profile_image VARCHAR2(1000 BYTE)
);

CREATE UNIQUE INDEX scott.actor_pk ON
    scott.actor (
        id
    ASC );

ALTER TABLE scott.actor
    ADD CONSTRAINT actor_pk PRIMARY KEY ( id )
        USING INDEX scott.actor_pk;

CREATE TABLE scott.casting (
    id         NUMBER NOT NULL,
    actor_id   VARCHAR2(100 BYTE) NOT NULL,
    role       VARCHAR2(100 BYTE),
    musical_id VARCHAR2(100 BYTE) NOT NULL
);

CREATE UNIQUE INDEX scott.casting_pk ON
    scott.casting (
        id
    ASC );

ALTER TABLE scott.casting
    ADD CONSTRAINT casting_pk PRIMARY KEY ( id )
        USING INDEX scott.casting_pk;

CREATE TABLE scott.image (
    id      NUMBER NOT NULL,
    post_id NUMBER NOT NULL,
    path    VARCHAR2(1000 BYTE) NOT NULL
);

CREATE UNIQUE INDEX scott.pk_image ON
    scott.image (
        id
    ASC );

ALTER TABLE scott.image
    ADD CONSTRAINT pk_image PRIMARY KEY ( id )
        USING INDEX scott.pk_image;

CREATE TABLE scott.musical (
    id             VARCHAR2(100 BYTE) NOT NULL,
    stdate         DATE,
    eddate         DATE,
    title          VARCHAR2(100 BYTE),
    theater        VARCHAR2(100 BYTE),
    view_age       VARCHAR2(100 BYTE),
    running_time   VARCHAR2(100 BYTE),
    main_character VARCHAR2(100 BYTE),
    poster_image   VARCHAR2(1000 BYTE)
);

CREATE UNIQUE INDEX scott.musical_pk ON
    scott.musical (
        id
    ASC );

ALTER TABLE scott.musical
    ADD CONSTRAINT musical_pk PRIMARY KEY ( id )
        USING INDEX scott.musical_pk;

CREATE TABLE scott.post (
    id           NUMBER NOT NULL,
    user_id      VARCHAR2(30 BYTE) NOT NULL,
    type         VARCHAR2(20 BYTE) NOT NULL,
    title        VARCHAR2(500 BYTE) NOT NULL,
    content      VARCHAR2(4000 BYTE) NOT NULL,
    created_time DATE NOT NULL,
    viewed       NUMBER(20) DEFAULT 0 NOT NULL
);

CREATE UNIQUE INDEX scott.pk_post ON
    scott.post (
        id
    ASC );

ALTER TABLE scott.post
    ADD CONSTRAINT pk_post PRIMARY KEY ( id )
        USING INDEX scott.pk_post;

CREATE TABLE scott.post_like (
    id      NUMBER NOT NULL,
    user_id VARCHAR2(30 BYTE) NOT NULL,
    post_id NUMBER NOT NULL
);

CREATE UNIQUE INDEX scott.post_like_pk ON
    scott.post_like (
        id
    ASC );

ALTER TABLE scott.post_like
    ADD CONSTRAINT post_like_pk PRIMARY KEY ( id )
        USING INDEX scott.post_like_pk;

CREATE TABLE scott.re_reply (
    id           NUMBER NOT NULL,
    post_id      NUMBER NOT NULL,
    user_id      VARCHAR2(30 BYTE) NOT NULL,
    reply_id     NUMBER NOT NULL,
    content      VARCHAR2(2000 BYTE) NOT NULL,
    created_time DATE NOT NULL
);

CREATE UNIQUE INDEX scott.pk_re_reply ON
    scott.re_reply (
        id
    ASC );

ALTER TABLE scott.re_reply
    ADD CONSTRAINT pk_re_reply PRIMARY KEY ( id )
        USING INDEX scott.pk_re_reply;

CREATE TABLE scott.re_reply_like (
    id          NUMBER NOT NULL,
    user_id     VARCHAR2(30 BYTE) NOT NULL,
    re_reply_id NUMBER NOT NULL
);

CREATE UNIQUE INDEX scott.re_reply_like_pk ON
    scott.re_reply_like (
        id
    ASC );

ALTER TABLE scott.re_reply_like
    ADD CONSTRAINT re_reply_like_pk PRIMARY KEY ( id )
        USING INDEX scott.re_reply_like_pk;

CREATE TABLE scott.reply (
    id           NUMBER NOT NULL,
    post_id      NUMBER NOT NULL,
    user_id      VARCHAR2(30 BYTE) NOT NULL,
    content      VARCHAR2(2000 BYTE) NOT NULL,
    created_time DATE NOT NULL
);

CREATE UNIQUE INDEX scott.pk_reply ON
    scott.reply (
        id
    ASC );

ALTER TABLE scott.reply
    ADD CONSTRAINT pk_reply PRIMARY KEY ( id )
        USING INDEX scott.pk_reply;

CREATE TABLE scott.reply_like (
    id       NUMBER NOT NULL,
    user_id  VARCHAR2(30 BYTE) NOT NULL,
    reply_id NUMBER NOT NULL
);

CREATE UNIQUE INDEX scott.reply_like_pk ON
    scott.reply_like (
        id
    ASC );

ALTER TABLE scott.reply_like
    ADD CONSTRAINT reply_like_pk PRIMARY KEY ( id )
        USING INDEX scott.reply_like_pk;

CREATE TABLE scott.review (
    id         NUMBER NOT NULL,
    post_id    NUMBER NOT NULL,
    musical_id VARCHAR2(100 BYTE)
);

CREATE UNIQUE INDEX scott.pk_review ON
    scott.review (
        id
    ASC );

ALTER TABLE scott.review
    ADD CONSTRAINT pk_review PRIMARY KEY ( id )
        USING INDEX scott.pk_review;

CREATE TABLE scott.subscribe (
    id         NUMBER NOT NULL,
    musical_id VARCHAR2(100 BYTE) NOT NULL,
    user_id    VARCHAR2(30 BYTE) NOT NULL,
    type       VARCHAR2(20 BYTE) NOT NULL
);

CREATE UNIQUE INDEX scott.pk_subscribe ON
    scott.subscribe (
        id
    ASC );

ALTER TABLE scott.subscribe
    ADD CONSTRAINT pk_subscribe PRIMARY KEY ( id )
        USING INDEX scott.pk_subscribe;

CREATE TABLE scott.user_tb (
    id               VARCHAR2(30 BYTE) NOT NULL,
    user_pw          VARCHAR2(200 BYTE) NOT NULL,
    user_nickname    VARCHAR2(100 BYTE) NOT NULL,
    user_profile_img VARCHAR2(1000 BYTE),
    essential_agree  CHAR(1 BYTE) DEFAULT 'N' NOT NULL,
    personal_agree   CHAR(1 BYTE) DEFAULT 'N' NOT NULL,
    age_agree        CHAR(1 BYTE) DEFAULT 'N' NOT NULL
);

CREATE UNIQUE INDEX scott.pk_user ON
    scott.user_tb (
        id
    ASC );

ALTER TABLE scott.user_tb
    ADD CONSTRAINT pk_user PRIMARY KEY ( id )
        USING INDEX scott.pk_user;

ALTER TABLE scott.casting
    ADD CONSTRAINT casting_actor_fk FOREIGN KEY ( actor_id )
        REFERENCES scott.actor ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.casting
    ADD CONSTRAINT casting_musical_fk FOREIGN KEY ( musical_id )
        REFERENCES scott.musical ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.image
    ADD CONSTRAINT image_post_fk FOREIGN KEY ( post_id )
        REFERENCES scott.post ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.post_like
    ADD CONSTRAINT post_like_post_fk FOREIGN KEY ( post_id )
        REFERENCES scott.post ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.post_like
    ADD CONSTRAINT post_like_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.post
    ADD CONSTRAINT post_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.re_reply_like
    ADD CONSTRAINT re_reply_like_re_reply_fk FOREIGN KEY ( re_reply_id )
        REFERENCES scott.re_reply ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.re_reply_like
    ADD CONSTRAINT re_reply_like_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.re_reply
    ADD CONSTRAINT re_reply_post_fk FOREIGN KEY ( post_id )
        REFERENCES scott.post ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.re_reply
    ADD CONSTRAINT re_reply_reply_fk FOREIGN KEY ( reply_id )
        REFERENCES scott.reply ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.re_reply
    ADD CONSTRAINT re_reply_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.reply_like
    ADD CONSTRAINT reply_like_reply_fk FOREIGN KEY ( reply_id )
        REFERENCES scott.reply ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.reply_like
    ADD CONSTRAINT reply_like_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.reply
    ADD CONSTRAINT reply_post_fk FOREIGN KEY ( post_id )
        REFERENCES scott.post ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.reply
    ADD CONSTRAINT reply_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.review
    ADD CONSTRAINT review_musical_fk FOREIGN KEY ( musical_id )
        REFERENCES scott.musical ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.review
    ADD CONSTRAINT review_post_fk FOREIGN KEY ( post_id )
        REFERENCES scott.post ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.subscribe
    ADD CONSTRAINT subscribe_musical_fk FOREIGN KEY ( musical_id )
        REFERENCES scott.musical ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.subscribe
    ADD CONSTRAINT subscribe_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;
ALTER TABLE scott.answer
    ADD CONSTRAINT answer_admin_fk FOREIGN KEY ( admin_id )
        REFERENCES scott.admin ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.answer
    ADD CONSTRAINT answer_inquiry_fk FOREIGN KEY ( inquiry_id )
        REFERENCES scott.inquiry ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

ALTER TABLE scott.inquiry
    ADD CONSTRAINT inquiry_user_fk FOREIGN KEY ( user_id )
        REFERENCES scott.user_tb ( id )
            ON DELETE CASCADE
    NOT DEFERRABLE;

create SEQUENCE answer_sequence;
create SEQUENCE casting_sequence;
create SEQUENCE image_sequence;
create SEQUENCE inquiry_sequence;
create SEQUENCE post_sequence;
create SEQUENCE post_like_sequence;
create SEQUENCE reply_sequence;
create SEQUENCE reply_like_sequence;
create SEQUENCE re_reply_sequence;
create SEQUENCE re_reply_like_sequence;
create SEQUENCE SUBSCRIBE_sequence;
create SEQUENCE user_tb_sequence;
create SEQUENCE review_sequence;
