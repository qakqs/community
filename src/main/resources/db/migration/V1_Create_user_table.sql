create table USER
(
    ID           INT auto_increment,
    ACCOUNT_ID   INT,
    NAME         VARCHAR(100),
    TOKEN        CHAR(100),
    GMT_CREATE   BIGINT,
    GMT_MODIFIED BIGINT,
    constraint USER_PK
        primary key (ID)
);
