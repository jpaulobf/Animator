# Template: Criando um Novo Jogo com Animator Engine

Este documento é um **passo-a-passo** para criar um novo jogo que use `animator-engine` como dependência.

## 1. Preparação

### Pré-requisitos

```bash
# Verificar Maven
mvn --version
# > Apache Maven 3.6+ ✓

# Verificar Java
java -version
# > Java 14+ ✓

# Instalar Animator Engine localmente (uma única vez)
cd ~/path/to/animator-engine
mvn clean install
```

---

## 2. Criar Novo Projeto com Maven Archetype

```bash
# Criar pasta do novo jogo
mkdir my-platformer-game
cd my-platformer-game

# Gerar projeto Maven padrão
mvn archetype:generate \
  -DgroupId=com.mygame \
  -DartifactId=platformer-game \
  -DarchetypeArtifactId=maven-archetype-quickstart \
  -DinteractiveMode=false

cd platformer-game
```

**Estrutura criada:**
```
platformer-game/
├── pom.xml
├── src/
│   ├── main/java/com/mygame/
│   │   └── App.java
│   └── test/java/com/mygame/
│       └── AppTest.java
└── README.md
```

---

## 3. Configurar pom.xml

**Abra** `platformer-game/pom.xml` e **substitua** o conteúdo:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
                             http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>com.mygame</groupId>
    <artifactId>platformer-game</artifactId>
    <version>1.0.0</version>
    <packaging>jar</packaging>

    <name>My Platformer Game</name>
    <description>A platformer game built on Animator Engine</description>

    <properties>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
        <maven.compiler.source>14</maven.compiler.source>
        <maven.compiler.target>14</maven.compiler.target>
    </properties>

    <dependencies>
        <!-- Animator Engine Framework -->
        <dependency>
            <groupId>br.com.game</groupId>
            <artifactId>animator-engine</artifactId>
            <version>1.0.0</version>
        </dependency>

        <!-- JUnit Testing -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.13.2</version>
            <scope>test</scope>
        </dependency>
    </dependencies>

    <build>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.8.1</version>
                <configuration>
                    <source>14</source>
                    <target>14</target>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-jar-plugin</artifactId>
                <version>3.2.0</version>
                <configuration>
                    <archive>
                        <manifest>
                            <mainClass>com.mygame.PlatformerLauncher</mainClass>
                            <addClasspath>true</addClasspath>
                        </manifest>
                    </archive>
                </configuration>
            </plugin>

            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-shade-plugin</artifactId>
                <version>3.2.4</version>
                <executions>
                    <execution>
                        <phase>package</phase>
                        <goals>
                            <goal>shade</goal>
                        </goals>
                        <configuration>
                            <transformers>
                                <transformer implementation="org.apache.maven.plugins.shade.resource.ManifestResourceTransformer">
                                    <mainClass>com.mygame.PlatformerLauncher</mainClass>
                                </transformer>
                            </transformers>
                            <finalName>platformer-game-${project.version}-executable</finalName>
                        </configuration>
                    </execution>
                </executions>
            </plugin>
        </plugins>

        <resources>
            <resource>
                <directory>src/main/resources</directory>
                <includes>
                    <include>**/*.ini</include>
                    <include>**/*.properties</include>
                    <include>images/**/*</include>
                </includes>
            </resource>
        </resources>
    </build>

</project>
```

---

## 4. Criar Estrutura de Recursos

```bash
mkdir -p src/main/resources/config
mkdir -p src/main/resources/images
```

**Criar `src/main/resources/config/config.ini`:**

```ini
# Game flow configuration
dev_logo_enabled=false
intro_screen_enabled=false
high_score_screen_enabled=false
```

---

## 5. Implementar o Jogo

**Criar `src/main/java/com/mygame/PlatformerGame.java`:**

```java
package com.mygame;

import java.awt.Graphics2D;
import br.com.game.animator.game.Game;
import br.com.game.animator.game.core.AbstractGame;

public class PlatformerGame extends AbstractGame {

    private float playerX = 100;
    private float playerY = 100;
    private float playerSpeed = 5;

    @Override
    public void handleInput(Game game, int keyCode, boolean isAltDown) {
        switch (keyCode) {
            case 37: // LEFT
                playerX -= playerSpeed;
                break;
            case 39: // RIGHT
                playerX += playerSpeed;
                break;
            case 38: // UP
                playerY -= playerSpeed;
                break;
            case 40: // DOWN
                playerY += playerSpeed;
                break;
        }
    }

    @Override
    public void updateGame() {
        // Update game logic here
    }

    @Override
    public void drawGame(Graphics2D g2d) {
        // Clear background
        g2d.setColor(new java.awt.Color(255, 255, 255));
        g2d.fillRect(0, 0, 1280, 720);

        // Draw player
        g2d.setColor(new java.awt.Color(0, 0, 255));
        g2d.fillRect((int)playerX, (int)playerY, 50, 50);

        // Draw text
        g2d.setColor(new java.awt.Color(0, 0, 0));
        g2d.drawString("Platformer Game - Use arrow keys to move", 50, 50);
    }
}
```

**Criar `src/main/java/com/mygame/PlatformerLauncher.java`:**

```java
package com.mygame;

import br.com.game.animator.Launcher;
import br.com.game.animator.game.Game;
import br.com.game.animator.game.core.IGame;
import br.com.game.animator.game.factory.CoreGameFactory;

public class PlatformerLauncher extends Launcher {
    
    public static void main(String[] args) {
        new PlatformerLauncher();
    }

    @Override
    protected IGame createGame() {
        return new PlatformerGame();
    }

    @Override
    protected CoreGameFactory createFactory() {
        // Use default factory or override with custom logic
        return CoreGameFactory.getInstance();
    }
}
```

---

## 6. Compilar e Executar

```bash
# Compilar
mvn clean compile

# Empacotar (gera JAR executável)
mvn clean package

# Executar JAR
java -jar target/platformer-game-1.0.0-executable.jar

# Ou executar direto do Maven
mvn exec:java -Dexec.mainClass="com.mygame.PlatformerLauncher"
```

---

## 7. Estrutura Final do Projeto

```
platformer-game/
├── pom.xml
├── src/
│   ├── main/
│   │   ├── java/com/mygame/
│   │   │   ├── PlatformerGame.java      ← Sua lógica do jogo
│   │   │   └── PlatformerLauncher.java  ← Entry point
│   │   └── resources/
│   │       ├── config/config.ini        ← Configuração
│   │       └── images/                  ← Seus assets
│   └── test/java/...
└── target/
    └── platformer-game-1.0.0-executable.jar  ← JAR final
```

---

## 8. Checklist para Novo Jogo

- [x] Clonar repositório ou criar novo projeto Maven
- [x] Configurar `pom.xml` com dependência `animator-engine`
- [x] Criar `config.ini` na pasta `resources`
- [x] Estender `AbstractGame` com sua lógica
- [x] Criar Launcher que estende `Launcher`
- [x] Adicionar assets (imagens, sons) em `resources`
- [x] Compilar com `mvn clean package`
- [x] Executar JAR gerado

---

## 9. Personalizações Comuns

### A) Customizar Menu Principal

```java
public class MyMainMenu implements CoreGameLogic {
    @Override
    public void draw(Graphics2D g2d) {
        // Seu menu customizado
    }

    @Override
    public void handleInput(Game game, int keyCode, boolean isAltDown) {
        // Sua lógica de menu
    }

    @Override
    public boolean finished() {
        return false;
    }
}
```

Depois registrar em `CoreGameFactory.createCoreGameLogic()`:
```java
case MAIN_MENU_SCREEN:
    return new MyMainMenu();
```

### B) Adicionar Nova Tela de Options

```java
public class MyGameOptions implements CoreGameLogic {
    // ... implementar
}
```

Registrar em `CoreGameFactory` e adicionar novo estado em `GameStates.java`.

### C) Modificar Game Flow

Editar `GameStateMachine.calculateNextState()` para adicionar seus estados customizados.

---

## 10. Troubleshooting

| Problema | Solução |
|----------|---------|
| `Cannot find animator-engine` | Execute `mvn install` no projeto principal |
| Compilação falha | Verifique Java 14+ com `java -version` |
| JAR não executa | Verifique `<mainClass>` no pom.xml |
| Dependências não baixam | Verifique conexão internet + `~/.m2/settings.xml` |

---

**Pronto!** Seu novo jogo está rodando em cima de `animator-engine`. 🎮✨

Para modificar, apenas altere:
- `PlatformerGame.java` (sua lógica)
- Telas de menu/options (implementando `CoreGameLogic`)
- `config.ini` (ativar/desativar telas)
