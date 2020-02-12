default: run
build:
	mvn package -f pom.xml
	docker build -t whoami:latest .
run: build
	docker run -p 8080:8080 whoami:latest
