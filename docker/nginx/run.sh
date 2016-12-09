#/bin/bash
docker run -d -p 443:443 -p 80:80 -v ~/git/jdk9-jigsaw-http2/docker/nginx/sites:/etc/nginx/conf.d -v ~/git/jdk9-jigsaw-http2/docker/nginx/certs:/etc/nginx/ssl -v ~/git/jdk9-jigsaw-http2/docker/nginx/log:/var/log/nginx -v ~/git/jdk9-jigsaw-http2/docker/nginx/cache:/var/cache/nginx -v ~/git/jdk9-jigsaw-http2/docker/nginx/html:/var/www/html --name my-running-nginx weinschenker/nginx-http2
