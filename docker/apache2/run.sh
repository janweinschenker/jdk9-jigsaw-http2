#/bin/bash
docker run -dit -p 443:443 -p 80:80 --name my-running-apache janweinschenker/apache2http2:1.0
