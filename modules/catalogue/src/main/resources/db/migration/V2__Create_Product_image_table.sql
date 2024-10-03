CREATE TABLE product_images (
    id UUID PRIMARY KEY,
    product_id UUID,
    path VARCHAR,
    created TIMESTAMP,
    updated TIMESTAMP,
    FOREIGN KEY (product_id) REFERENCES products(id)
);