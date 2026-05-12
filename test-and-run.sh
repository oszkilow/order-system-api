#!/bin/bash
set -e # Detiene el script si un comando falla

# Colores para la visualización
BLUE='\033[0;34m'
GREEN='\033[0;32m'
RED='\033[0;31m'
NC='\033[0m'

echo -e "${BLUE}==> 1. Verificando Contenedores...${NC}"
if [ ! "$(docker ps -q -f name=order_postgres)" ]; then
    echo -e "${RED}Error: Postgres no está corriendo.${NC}"
    exit 1
fi

echo -e "${BLUE}==> 2. Ejecutando Tests Unitarios con Mockito...${NC}"
./mvnw test

echo -e "${BLUE}==> 3. Compilando el proyecto (JAR)...${NC}"
./mvnw package -DskipTests

echo -e "${GREEN}==> 4. ¡Todo listo! Iniciando la API...${NC}"
java -jar target/ordersystem-api-*.jar
