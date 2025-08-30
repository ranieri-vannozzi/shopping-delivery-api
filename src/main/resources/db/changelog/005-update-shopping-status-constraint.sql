-- 005-update-shopping-status-constraint.sql

ALTER TABLE shopping
DROP CONSTRAINT IF EXISTS chk_shopping_status;

ALTER TABLE shopping
    ADD CONSTRAINT chk_shopping_status
        CHECK (status IN (
                          'CREATED',
                          'CONFIRMED',
                          'PREPARING',
                          'READY_FOR_DELIVERY',
                          'IN_TRANSIT',
                          'DELIVERED',
                          'CANCELLED'
            ));
