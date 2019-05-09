To run application execute following steps:

- mvn clean install
- java -jar target/revolut-backend-task-1.0-jar-with-dependencies.jar

Examples:
To create account execute HTTP POST method e.g.
curl -X POST -H "Content-Type: application/json" -d '{"id": 1, "money": "1.0"}' http://localhost:4567/account

To get account balance execute HTTP GET method e.g.
curl http://localhost:4567/account/balance?id=1

To transfer money from one account to another execute HTTP POST method e.g.
curl -X POST -H "Content-Type: application/json" -d '{"sender": 1, "recipient": 2, "amount": "1.0"}' http://localhost:4567/transaction