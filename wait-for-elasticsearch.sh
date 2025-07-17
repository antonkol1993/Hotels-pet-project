#!/bin/bash
set -e

echo "⏳ Ожидаем запуск Elasticsearch на http://elasticsearch:9200..."
until curl -s http://elasticsearch:9200 >/dev/null; do
  sleep 3
done

echo "✅ Elasticsearch готов. Запускаем Filebeat."
/usr/share/filebeat/filebeat -e
