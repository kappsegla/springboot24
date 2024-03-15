ALTER TABLE cat
    ADD chipped BIT(1) NULL;

UPDATE cat
SET chipped = 0
WHERE chipped IS NULL;
ALTER TABLE cat
    MODIFY chipped BIT (1) NOT NULL;
