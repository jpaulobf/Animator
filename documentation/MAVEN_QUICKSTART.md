# Maven Quick Start - Animator Engine

**Versão rápida**: Próximos 5 passos para ter o Maven funcionando.

## ✅ Passo 1: Verificar Pré-requisitos

```bash
# Verificar Java
java -version
# Precisa Java 14+

# Verificar Maven
mvn --version
# Maven 3.6+ recomendado
```

Se não tiver Maven:
- **Windows**: https://maven.apache.org/download.cgi → adicionar `bin/` ao PATH
- **Mac**: `brew install maven`
- **Linux**: `sudo apt install maven` ou download manual

---

## ✅ Passo 2: Testar Build Básico

Na pasta raiz do projeto (`animator-engine/`):

```bash
# Limpar builds anteriores
mvn clean

# Compilar código
mvn compile

# Se tudo deu certo, você verá:
# [INFO] BUILD SUCCESS
```

---

## ✅ Passo 3: Executar Build Completo

```bash
# Compilar + testar + empacotar
mvn clean package

# Gera arquivo: target/animator-engine-1.0.0.jar

# Se tudo deu certo:
# [INFO] BUILD SUCCESS
```

---

## ✅ Passo 4: Instalar Localmente

```bash
# Instalar JAR no repositório local (~/.m2/)
mvn clean install
```

Isso permite que outros projetos usem sua engine como dependência!

---

## ✅ Passo 5: Testar em Outro Projeto

Criar novo projeto que usa seu engine:

```bash
mkdir my-test-game
cd my-test-game
```

**Criar `pom.xml`:**

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.mytest</groupId>
    <artifactId>my-test-game</artifactId>
    <version>1.0.0</version>

    <dependencies>
        <dependency>
            <groupId>br.com.game</groupId>
            <artifactId>animator-engine</artifactId>
            <version>1.0.0</version>
        </dependency>
    </dependencies>
</project>
```

```bash
# Testar se consegue baixar dependência
mvn dependency:tree

# Deve listar animator-engine-1.0.0.jar
```

---

## 🎯 Comandos Mais Usados

```bash
mvn clean              # Limpar build anterior
mvn compile            # Compilar código
mvn test               # Executar testes
mvn package            # Gerar JAR
mvn install            # Instalar no repositório local
mvn clean install      # Tudo acima de uma vez
mvn dependency:tree    # Ver dependências
mvn javadoc:javadoc    # Gerar documentação
```

---

## 🔧 Troubleshooting Rápido

| Erro | Solução |
|------|---------|
| `'mvn' is not recognized` | Adicionar Maven ao PATH |
| Compilação falha | Verificar Java versão (`java -version`) |
| `BUILD FAILURE` | Rodar `mvn clean` e tentar novamente |
| Dependências não baixam | Checar conexão internet |

---

## ✨ Pronto!

Você agora tem:
- ✅ Maven configurado
- ✅ `animator-engine-1.0.0.jar` pronto
- ✅ Engine disponível para outros projetos

**Próximo passo**: Lê o arquivo `TEMPLATE_NEW_GAME.md` para criar seu primeiro jogo! 🎮

---

## 📚 Recursos

- Maven Official: https://maven.apache.org/
- POM Reference: https://maven.apache.org/pom.html
- Arquivos neste projeto:
  - `README.md` — Documentação completa
  - `NEXT_STEPS.md` — Fases recomendadas
  - `TEMPLATE_NEW_GAME.md` — Criar novo jogo
