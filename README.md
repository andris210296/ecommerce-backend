# E-commerce Application

This is a Spring Boot-based e-commerce application that provides functionalities for managing products, orders, and order items. The application uses PostgreSQL as the database.

## Setup

1. **Clone the repository**:
    ```sh
    git clone https://github.com/your-repo/ecommerce.git
    cd ecommerce
    ```

2. **Configure the database**:
   Update the `src/main/resources/application.properties` file with your PostgreSQL database configuration:
    ```ini
    spring.datasource.url=jdbc:postgresql://localhost:5432/ecommerce
    spring.datasource.username=your-username
    spring.datasource.password=your-password
    ```
      
3. **Build the project**:
    ```sh
    mvn clean install
    ```

4. **Run the application**:
    ```sh
    mvn spring-boot:run
    ```

5. **Package the application**:
  Run the following command to create the JAR file in the `target` folder. This is necessary for the Dockerfile to work correctly:
   ```sh
   mvn clean package
   ```

6. **Run with Docker**:
   Ensure you have Docker installed. Use the provided `docker-compose.yml` to build and run the application and the database. Make sure that the `target` folder contains the JAR file of the project before running this command:
    ```sh
    docker-compose up --build
    ```

## API Documentation
The application uses OpenAPI 3.0 for API documentation. You can access the API documentation at:
```ini
http://localhost:8080/swagger-ui.html
```
You can check the endpoints by accessing the Swagger page:
```ini
http://localhost:8080/swagger-ui/index.html#/
```

## Endpoints

The application provides the following endpoints:

### Product Endpoints:
```ini
GET /api/products: Fetch all products.
GET /api/products/{id}: Fetch a product by ID.
POST /api/products: Create a new product.
PUT /api/products/{id}: Update an existing product.
DELETE /api/products/{id}: Delete a product.
```
### Order Endpoints
```ini
GET /api/orders: Fetch all orders.
GET /api/orders/{id}: Fetch an order by ID.
POST /api/orders: Create a new order.
PUT /api/orders/{id}: Update an existing order.
DELETE /api/orders/{id}: Delete an order.
```

### Order Item Endpoints
```ini
GET /api/order-items: Fetch all order items.
GET /api/order-items/{id}: Fetch an order item by ID.
POST /api/order-items: Create a new order item.
PUT /api/order-items/{id}: Update an existing order item.
DELETE /api/order-items/{id}: Delete an order item.
```

Before creating an order or an order item, ensure that a product is created first. Follow these steps:

1. **Create a Product**: Use the product endpoints to create a product.
2. **Create an Order**: Once a product is created, use the order endpoints to create an order.
3. **Create an Order Item**: After creating an order, use the order item endpoints to create an order item.

You can verify the products associated with orders by accessing the order endpoints.
