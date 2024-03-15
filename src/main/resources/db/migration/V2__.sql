CREATE TABLE vaccination
(
    id     BIGINT AUTO_INCREMENT NOT NULL,
    date   date NULL,
    cat_id BIGINT NULL,
    CONSTRAINT pk_vaccination PRIMARY KEY (id)
);

ALTER TABLE vaccination
    ADD CONSTRAINT FK_VACCINATION_ON_CAT FOREIGN KEY (cat_id) REFERENCES cat (id);
