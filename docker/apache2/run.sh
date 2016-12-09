#/bin/bash
docker run -dit -p 443:443 -p 80:80 -v ~/logs/docker/apache2:/usr/local/apache2/logs --name my-running-apache janweinschenker/apache2http2:1.0
