-- 003-insert-default-warehouse-data.sql

INSERT INTO warehouse (name, latitude, longitude, address, phone, is_active)
VALUES
    ('PISTOIA', 43.92913240147757, 10.900101040215748, 'Viale Adua 1, Pistoia', '0573-000001', TRUE),
    ('PRATO', 43.87654721438641, 11.093375513226892, 'Via Roma 10, Prato', '0574-000010', TRUE),
    ('FIRENZE', 43.80067381681958, 11.245130611376862, 'Viale Morgagni 40, Firenze', '055-000040', TRUE);
