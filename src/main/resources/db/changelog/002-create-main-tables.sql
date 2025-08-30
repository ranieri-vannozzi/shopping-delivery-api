-- 002-create-main-tables.sql

DROP TABLE IF EXISTS shopping;

CREATE TABLE warehouse (
                           id SMALLSERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           latitude DOUBLE PRECISION NOT NULL,
                           longitude DOUBLE PRECISION NOT NULL,
                           address VARCHAR(255),
                           phone VARCHAR(50),
                           is_active BOOLEAN DEFAULT TRUE
);

CREATE TABLE shopping (
                          id BIGSERIAL PRIMARY KEY,
                          delivery_latitude DOUBLE PRECISION NOT NULL,
                          delivery_longitude DOUBLE PRECISION NOT NULL,
                          delivery_address VARCHAR(255),
                          status VARCHAR(50) NOT NULL,
                          created_at TIMESTAMP NULL,
                          updated_at TIMESTAMP NULL,
                          requested_delivery_date TIMESTAMP NULL,
                          customer_name VARCHAR(255),
                          customer_phone VARCHAR(50),
                          delivery_notes VARCHAR(500),
                          total_amount DECIMAL(15,2),
                          warehouse_id SMALLINT,
                          CONSTRAINT fk_shopping_warehouse FOREIGN KEY (warehouse_id) REFERENCES warehouse(id),
                          CONSTRAINT chk_shopping_status CHECK (status IN ('CREATED','PROCESSING','DELIVERED','CANCELLED'))
);

CREATE TABLE shopping_item (
                                id BIGSERIAL PRIMARY KEY,
                                product_name VARCHAR(255) NOT NULL,
                                product_code VARCHAR(100),
                                quantity INT NOT NULL,
                                unit_price DECIMAL(15,2) NOT NULL,
                                total_price DECIMAL(15,2),
                                category VARCHAR(100),
                                brand VARCHAR(100),
                                weight DOUBLE PRECISION,
                                unit_of_measure VARCHAR(50),
                                shopping_id BIGINT,
                                CONSTRAINT fk_items_shopping FOREIGN KEY (shopping_id) REFERENCES shopping(id)
);
