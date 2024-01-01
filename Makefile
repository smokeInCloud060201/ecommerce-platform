setup: db-migrate

db-migrate:
	 docker-compose -p db-migrate -f ./infrastructure/docker-composes/docker-compose-base.yml up -d --build

db-remove:
	docker-compose -p db-migrate down --volumes --remove-orphans

ops:
	docker-compose -p oberservability -f ./infrastructure/docker-composes/docker-compose-obs.yml up -d --build