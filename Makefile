ready:
	curl -X POST -d "password=mynameisnabi" localhost:8080/api/v1/game/ready_check
start:
	curl -X POST -d "password=mynameisnabi" localhost:8080/api/v1/game/start
delete_users: reset_game
	curl -X POST -d "password=mynameisnabi" localhost:8080/api/v1/data/reset/users
reset_game:
	curl -X POST -d "password=mynameisnabi" localhost:8080/api/v1/game/reset
