# Usa a imagem base oficial do OpenJDK 17
FROM eclipse-temurin:17-jdk-jammy

# Copia o jar gerado pelo Maven para dentro do container
COPY target/creditcard-payment-api-0.0.1-SNAPSHOT.jar app.jar

# Expõe a porta 8080 para acesso externo
EXPOSE 8080

# Comando para rodar a aplicação
ENTRYPOINT ["java", "-jar", "/app.jar"]
