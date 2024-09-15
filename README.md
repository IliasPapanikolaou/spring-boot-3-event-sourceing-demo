### Postman request:

Create new order:
```shell
curl --location 'localhost:8080/api/orders/place' \
--header 'Content-Type: application/json' \
--data '{
    "userId":"jr-123",
    "name": "John Rambo",
    "qty": 3,
    "price": 43.80
}'
```

Confirm order:
```shell
curl --location --request PUT 'localhost:8080/api/orders/confirm/9ecd77ac' \
--data ''
```