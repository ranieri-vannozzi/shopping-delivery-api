# Requirements

A grocery retailer takes orders online and delivers them from its warehouses. This project covers the management of
those orders and the planning of the delivery route, with a backend
([shopping-delivery-api](https://github.com/ranieri-vannozzi/shopping-delivery-api)) and a web frontend
([shopping-delivery-ng](https://github.com/ranieri-vannozzi/shopping-delivery-ng)).

## Use cases

The backend exposes REST services for four use cases.

1. **Create an order.** The service receives a new order and stores it.
2. **Update an order.** Only some fields can change. A typical case is a wrong delivery address, which can still be
   fixed as long as the order has not left the warehouse.
3. **List orders.** The service returns the orders with their status. Filtering and sorting are welcome.
4. **Plan the delivery route.** Starting from the orders that are ready for delivery, the service computes a good
   route: it leaves the warehouse, reaches every delivery address and comes back to the warehouse. The choice of
   algorithm is free: even a simple one, such as visiting the points in a straight sequence, is acceptable.

The frontend lets a user work with these services.

## Data

- **Order:** the delivery position (latitude and longitude), the list of items, and any other field that makes sense.
- **Warehouse:** a readable name and its position (latitude and longitude).
- Other entities can be added when the design needs them.

## Technical constraints

- A modern, cloud-native framework. The backend uses Spring Boot.
- The backend is built with Maven and tested with JUnit.
- The application runs in a Docker container.
- Assumptions and design choices are documented in the README.
