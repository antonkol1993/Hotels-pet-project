#!/bin/sh

echo "Waiting for Elasticsearch at http://elasticsearch:9200..."

# Ждем пока Elasticsearch запустится (можешь заменить или подправить под свой таймаут)
until curl -s http://elasticsearch:9200 >/dev/null; do
  sleep 1
done

echo "Elasticsearch is up! Starting Filebeat..."

# Меняем права на конфиг (требование Filebeat)
chmod 600 /usr/share/filebeat/filebeat.yml

# Запускаем filebeat в режиме foreground
exec filebeat -e
