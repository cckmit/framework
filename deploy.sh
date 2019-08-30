mvn clean package -Dspring.profiles.active=test -Dmaven.test.skip=true
mvn deploy -Dspring.profiles.active=test -Dmaven.test.skip=true