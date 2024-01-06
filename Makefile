setup: db-migrate

db-migrate:
	 docker-compose -p karson-ecom-base -f ./infrastructure/docker-composes/docker-compose-base.yml up -d --build

db-remove:
	docker-compose -p db-migrate down --volumes --remove-orphans

obs:
	docker-compose -p karson-ecom-oberservability -f ./infrastructure/docker-composes/docker-compose-obs.yml up -d --build