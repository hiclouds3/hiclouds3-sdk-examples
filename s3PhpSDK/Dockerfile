FROM php:7-cli

COPY --from=composer:2 /usr/bin/composer /usr/local/bin/composer
COPY entrypoint.sh /opt/
COPY sample /opt/sample
RUN ["chmod", "+x", "/opt/entrypoint.sh"]
RUN cd /opt/sample/ && composer install

WORKDIR /opt
ENTRYPOINT ["/opt/entrypoint.sh"]
