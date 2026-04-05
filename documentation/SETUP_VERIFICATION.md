# ✅ Setup Verification Checklist

Use este checklist para verificar se tudo está configurado corretamente.

---

## 🔍 Pré-requisitos

### Java Installation
```bash
java -version
```

- [ ] Java 14 ou superior instalado
- [ ] Output similar a: `java version "14.0.x"`

### Maven Installation
```bash
mvn --version
```

- [ ] Maven 3.6 ou superior instalado
- [ ] Output similar a: `Apache Maven 3.6.x`

---

## 📁 Project Structure

### Root Files

```bash
# Verificar se arquivos existem
ls -la pom.xml
ls -la START_HERE.md
ls -la README.md
```

- [ ] `pom.xml` exists (Maven configuration)
- [ ] `START_HERE.md` exists (documentation)
- [ ] `.gitignore` exists

### Source Structure

```bash
# Verificar estrutura de código
ls -la src/
ls -la bin/
```

- [ ] `src/br/com/game/animator/` exists
- [ ] `bin/res/images/` exists
- [ ] `src/res/config/config.ini` exists

### Resources

```bash
# Verificar resources
ls -la src/res/config/config.ini
```

- [ ] `config.ini` exists
- [ ] Contains properties: `dev_logo_enabled`, `intro_screen_enabled`, `high_score_screen_enabled`

---

## 🏗️ Build Verification

### Step 1: Clean Build Artifacts

```bash
mvn clean
```

- [ ] `target/` folder removed
- [ ] No errors

### Step 2: Compile Source Code

```bash
mvn compile
```

Expected output:
```
[INFO] BUILD SUCCESS
```

- [ ] **✅ BUILD SUCCESS** appears in console
- [ ] `target/classes/` folder created
- [ ] Java classes compiled to `.class` files

### Step 3: Package Into JAR

```bash
mvn package
```

Expected output:
```
[INFO] BUILD SUCCESS
[INFO] Building jar: .../target/animator-engine-1.0.0.jar
```

- [ ] **✅ BUILD SUCCESS** appears
- [ ] `target/animator-engine-1.0.0.jar` file created (size ~500KB+)
- [ ] `target/animator-engine-1.0.0-sources.jar` created (optional)
- [ ] `target/animator-engine-1.0.0-javadoc.jar` created (optional)

### Step 4: Install Locally

```bash
mvn install
```

Expected output:
```
[INFO] Installing .../animator-engine-1.0.0.jar to ~/.m2/repository/...
[INFO] BUILD SUCCESS
```

- [ ] **✅ BUILD SUCCESS** appears
- [ ] JAR installed to `~/.m2/repository/br/com/game/animator-engine/1.0.0/`

---

## 📦 Dependency Verification

### Check Local Repository

```bash
ls ~/.m2/repository/br/com/game/animator-engine/1.0.0/
```

- [ ] `animator-engine-1.0.0.jar` exists
- [ ] `animator-engine-1.0.0.pom` exists

### Test in Another Project

Create test `pom.xml`:

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0">
    <modelVersion>4.0.0</modelVersion>
    <groupId>com.test</groupId>
    <artifactId>test-project</artifactId>
    <version>1.0</version>

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
mvn dependency:tree
```

Expected output should include:
```
br.com.game:animator-engine:jar:1.0.0
```

- [ ] `animator-engine` appears in dependency tree
- [ ] No error downloading dependency

---

## 📄 Documentation Files

Verify all documentation files exist:

```bash
ls -la *.md
```

- [ ] `START_HERE.md` ← Main guide
- [ ] `MAVEN_QUICKSTART.md` ← Quick setup
- [ ] `README.md` ← Full documentation
- [ ] `MAVEN_SETUP.md` ← Maven details
- [ ] `TEMPLATE_NEW_GAME.md` ← Game template
- [ ] `NEXT_STEPS.md` ← Next actions
- [ ] `PROJECT_OVERVIEW.md` ← Overview
- [ ] `DOCUMENTATION_INDEX.md` ← Index
- [ ] `SETUP_VERIFICATION.md` ← This file

---

## 🎯 Code Quality Checks

### Check Class Compilation

```bash
# Verify Java classes compile
mvn compile

# Check compiled classes
find target/classes -name "*.class" | wc -l
```

Expected: 50+ `.class` files

- [ ] All Java files compile without errors
- [ ] No warnings about deprecated methods

### Check Resource Inclusion

```bash
# Verify config.ini is in JAR
jar tf target/animator-engine-1.0.0.jar | grep config.ini
```

Expected output:
```
config/config.ini
```

- [ ] `config.ini` included in JAR

---

## 🚀 Quick Start Test

Run the complete workflow:

```bash
# Clean and build
mvn clean package install

# Unpack and inspect JAR
jar tf target/animator-engine-1.0.0.jar | head -20
```

- [ ] All steps execute successfully
- [ ] JAR contains expected structure:
  - `br/com/game/animator/` classes
  - `config/config.ini`

---

## 📊 Final Status Report

### ✅ Infrastructure

- [ ] Java 14+ installed
- [ ] Maven 3.6+ installed
- [ ] `pom.xml` configured
- [ ] `.gitignore` present

### ✅ Source Code

- [ ] All Java files present (50+ files)
- [ ] `GameEngine.java` compiles
- [ ] `Game.java` compiles
- [ ] Core interfaces present

### ✅ Build Output

- [ ] `animator-engine-1.0.0.jar` created
- [ ] `-sources.jar` created
- [ ] `-javadoc.jar` created
- [ ] JAR installed locally (~/.m2/)

### ✅ Documentation

- [ ] 9 markdown files present
- [ ] START_HERE.md readable
- [ ] TEMPLATE_NEW_GAME.md has examples
- [ ] README.md comprehensive

### ✅ Ready for Use

- [ ] Can use JAR as dependency
- [ ] Can create new game project
- [ ] Can extend AbstractGame
- [ ] Can build new game with Maven

---

## ⚠️ Troubleshooting

If any step fails, check here:

| Issue | Solution |
|-------|----------|
| `mvn: command not found` | Install Maven, add to PATH |
| `java: command not found` | Install Java 14+, add to PATH |
| `BUILD ERROR: Compilation` | Check Java version matches |
| JAR not created | Run `mvn clean package` |
| Dependency not found | Run `mvn install` in animator-engine folder |
| Old JAR version | Delete `~/.m2/SNAPSHOT` folder |

---

## ✨ Success Criteria

When ALL boxes below are ✅, you're ready to create games:

```
✅ Java 14+ installed
✅ Maven 3.6+ installed
✅ pom.xml configured
✅ Code compiles (mvn compile)
✅ JAR created (mvn package)
✅ JAR installed locally (mvn install)
✅ All 9 documentation files present
✅ Can use as dependency
✅ Ready to create first game!
```

---

## 🎮 Next: Create Your First Game

When all checks pass ✅, follow:

**→ [TEMPLATE_NEW_GAME.md](TEMPLATE_NEW_GAME.md)**

---

## 📝 Verification Date & Notes

```
Date: _______________
Verified by: _____________
Notes: _______________
       _______________
```

---

**Status: Ready to build games!** 🚀✨
