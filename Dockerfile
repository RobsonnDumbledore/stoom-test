FROM azul/zulu-openjdk-alpine:17

# Define o caminho do arquivo JAR, ajustado para onde o Gradle coloca os arquivos JAR
ARG JAR_FILE=build/libs/*SNAPSHOT.jar

# Copia o arquivo JAR para a imagem como app.jar
COPY ${JAR_FILE} app.jar

# Define o comando para executar a aplicação
ENTRYPOINT ["java","-jar","/app.jar"]
