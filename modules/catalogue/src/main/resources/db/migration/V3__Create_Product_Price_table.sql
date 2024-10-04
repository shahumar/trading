CREATE TABLE product_price (
    id UUID PRIMARY KEY,
    product_id UUID,
    price decimal(8, 2),
    created TIMESTAMP,
    updated TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);