# Multi-Service E-Commerce Backend

## Project Overview
This project implements a backend system for an e-commerce application following a microservices architecture. It consists of the following core components:

1. **Product Service**: Manages product details.
2. **Order Service**: Handles customer orders.
3. **API Gateway**: Provides a single entry point for accessing all services.
4. **Service Registry**: Uses Eureka for service discovery and registration.
5. **Load Balancer**: Distributes requests among multiple service instances.
6. **Messaging System**: Uses Apache Pulsar for synchronous communication between services.

---

## Features

### 1. Product Service
- Provides REST API endpoints to manage products.
  - `GET /products`: Fetch all products.
  - `GET /products/{id}`: Fetch a product by ID.
  - `POST /products`: Add a new product.
  - `PUT /products/{id}`: Update a product.
  - `DELETE /products/{id}`: Delete a product.
- Implements basic in-memory storage.
- Registers with Eureka for service discovery.

### 2. Order Service
- Provides REST API endpoints to manage orders.
  - `GET /orders`: Fetch all orders.
  - `POST /orders`: Create a new order (verifies product existence via the Product Service).
- Publishes events to the messaging system for order-related notifications.
- Registers with Eureka for service discovery.

### 3. API Gateway
- Implements Spring Cloud Gateway.
- Routes requests to the appropriate services:
  - `/api/products/**` to Product Service.
  - `/api/orders/**` to Order Service.
- Configured with load balancing for scalable performance.

### 4. Service Registry (Eureka Server)
- Manages service discovery and registration for all microservices.
- Ensures dynamic allocation of instances for scalability.

### 5. Load Balancer
- Deploys multiple instances of the Product Service.
- Configures the API Gateway to use Spring Cloud LoadBalancer to distribute requests across instances.

### 6. Messaging System (Apache Pulsar)
- Enables synchronous communication between services.

---

## Getting Started

### Prerequisites
- **Java 21**
- **Maven**
- **Postman** (for testing API endpoints)
- **Apache Pulsar**

### Running Locally

#### 1. Clone the repository
```bash
git clone <repository-url>
cd ecommerce-backend
```

#### 2. Start the Eureka Server
```bash
cd eureka-server
mvn spring-boot:run
```

#### 3. Start the Product Service
```bash
cd product-service
mvn spring-boot:run
```

#### 4. Start the Order Service
```bash
cd order-service
mvn spring-boot:run
```

#### 5. Start the API Gateway
```bash
cd api-gateway
mvn spring-boot:run
```

#### 6. Start Apache Pulsar
Download and start the Apache Pulsar standalone server:
```bash
wget https://downloads.apache.org/pulsar/pulsar-2.11.0/apache-pulsar-2.11.0-bin.tar.gz
tar xvfz apache-pulsar-2.11.0-bin.tar.gz
cd apache-pulsar-2.11.0
bin/pulsar standalone
```

#### 7. Test the Application
Use Postman or a similar tool to test the endpoints. Examples:
- Fetch all products: `GET http://localhost:8080/api/products`
- Create an order: `POST http://localhost:8080/api/orders`

---

## Deployment

### Docker Setup (Optional)
To run the services using Docker:

1. Build Docker images for each service:
```bash
docker build -t product-service ./product-service
docker build -t order-service ./order-service
docker build -t api-gateway ./api-gateway
docker build -t eureka-server ./eureka-server
```

2. Start services using Docker Compose:
```bash
docker-compose up
```

3. Ensure Apache Pulsar is running in standalone mode or set up as a container.

---

## Logging
- Uses **SLF4J** for consistent and structured logging.
- Logs include service instance details to track which instance handles requests.

---

## Testing
- Basic unit tests are implemented for the Product and Order services.
- Run tests using Maven:
```bash
mvn test
```

---

## Error Handling
- Proper error responses for invalid requests and missing resources.
- Returns appropriate HTTP status codes (e.g., 404 for not found, 400 for bad requests).

---

## Author
**Muhammad Baba Muhammad**  

