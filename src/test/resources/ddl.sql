CREATE TABLE TB_COMPANY (
	COMPANY_ID NUMERIC(10,0) NOT NULL,
	COMPANY_NAME VARCHAR(100) NOT NULL,
	COMPANY_EMAIL VARCHAR(100),
	COMPANY_PHONE VARCHAR(20),
	ADDRESS_STREET VARCHAR(100) NOT NULL,
	ADDRESS_CITY VARCHAR(100) NOT NULL,
	ADDRESS_COUNTRY VARCHAR(100) NOT NULL
);

CREATE TABLE TB_COMPANY_OWNER (
	COMPANY_ID NUMERIC(10,0) NOT NULL,
	OWNER_NAME VARCHAR(100) NOT NULL,
	OWNER_ORDER NUMERIC(1,0) NOT NULL
);

CREATE SEQUENCE SQ_COMPANY START WITH 5 INCREMENT BY 1;

ALTER TABLE TB_COMPANY ADD CONSTRAINT PK_COMPANY PRIMARY KEY (COMPANY_ID);

ALTER TABLE TB_COMPANY_OWNER ADD CONSTRAINT FK_COMPANY_OWNER FOREIGN KEY (COMPANY_ID) REFERENCES TB_COMPANY(COMPANY_ID);
