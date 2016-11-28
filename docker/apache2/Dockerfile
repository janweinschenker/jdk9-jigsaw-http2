FROM weinschenker/http24:1.0
COPY ./conf/httpd.conf /usr/local/apache2/conf/httpd.conf
COPY ./conf/vhost-example.conf /usr/local/apache2/conf/vhost-example.conf
COPY ./public-html/ /usr/local/apache2/htdocs
COPY ./certs/server.crt /usr/local/apache2/conf
COPY ./certs/server.key /usr/local/apache2/conf

