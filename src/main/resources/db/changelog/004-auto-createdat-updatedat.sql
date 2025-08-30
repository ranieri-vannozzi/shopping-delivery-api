-- 004-auto-createdat-updatedat.sql

CREATE OR REPLACE FUNCTION set_created_at_column()
RETURNS TRIGGER AS $$
BEGIN
   IF NEW.created_at IS NULL THEN
       NEW.created_at = now();
END IF;
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_set_shopping_created_at
    BEFORE INSERT ON shopping
    FOR EACH ROW
    EXECUTE FUNCTION set_created_at_column();

---

CREATE OR REPLACE FUNCTION update_updated_at_column()
RETURNS TRIGGER AS $$
BEGIN
   NEW.updated_at = now();
RETURN NEW;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER trigger_update_shopping_updated_at
    BEFORE UPDATE ON shopping
    FOR EACH ROW
    EXECUTE FUNCTION update_updated_at_column();
