# spring-microservices-app
This is a order management app build using spring cloud and microservices architecture.

## Modules
1. **order-service**: This is the main service which is responsible for managing orders.
2. **product-service**: This service is responsible for managing products.
3. **inventory-service**: This service is responsible for managing inventory.
4. **notification-service**: This service is responsible for sending notifications to the users.
5. **api-gateway**: This service is responsible for routing the requests to the respective services.


## How to run the application
1. Clone the repository
2. Run the following command to start the services
```shell
docker-compose up
```