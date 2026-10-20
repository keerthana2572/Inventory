-- Schema definition for products table
CREATE TABLE IF NOT EXISTS products (
    id VARCHAR(255) PRIMARY KEY,
    sku VARCHAR(255) UNIQUE,
    name VARCHAR(255),
    category VARCHAR(50),
    current_price DECIMAL(19,4),
    stock_level INTEGER,
    reorder_threshold INTEGER,
    demand_velocity INTEGER,
    status VARCHAR(50)
);

-- Schema definition for pricing_suggestions table
CREATE TABLE IF NOT EXISTS pricing_suggestions (
    id VARCHAR(255) PRIMARY KEY,
    product_id VARCHAR(255),
    current_price DECIMAL(19,4),
    recommended_price DECIMAL(19,4),
    change_direction VARCHAR(50),
    confidence DOUBLE,
    reasoning TEXT,
    status VARCHAR(50),
    trigger_reason VARCHAR(50)
);

-- Schema definition for reorder_suggestions table
CREATE TABLE IF NOT EXISTS reorder_suggestions (
    id VARCHAR(255) PRIMARY KEY,
    product_id VARCHAR(255),
    current_stock INTEGER,
    recommended_quantity INTEGER,
    suggested_lead_time_days INTEGER,
    confidence DOUBLE,
    reasoning TEXT,
    status VARCHAR(50),
    trigger_reason VARCHAR(50)
);