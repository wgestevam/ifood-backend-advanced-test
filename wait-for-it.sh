

set -e


shift
echo "Waiting 10 seconds for Postgres"
sleep 10
echo "Starting"
exec java $JAVA_OPTS -Djava.security.egd=file:/dev/./urandom -Dspring.profiles.active=docker -jar /app.jar
