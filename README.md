# verisoul-common

Shared interfaces, DTOs, and smart contract source for the Verisoul platform. This module defines the service contracts, data transfer objects, and the canonical SBT smart contract used by both [verisoul](https://github.com/joeyzyt/verisoul) (implementations) and [verisoul-api](https://github.com/joeyzyt/verisoul-api) (REST controllers).

## Architecture

```
verisoul-common (this module)
    ├── service interfaces
    ├── DTOs
    └── SBT smart contract (SBT.sol + compiled SBT.json)
        ↑ compile        ↑ compile
        │                │
   verisoul          verisoul-api
   (implementations)  (controllers + Hardhat test chain)
```

- **verisoul-common** is a plain Java library with no Spring Boot runtime.
- **verisoul** depends on verisoul-common at compile time and provides `@Service` implementations. It auto-generates the Web3j Java wrapper (`SBT.java`) from `SBT.json` via the `web3j-maven-plugin`.
- **verisoul-api** depends on verisoul-common at compile time (interfaces/DTOs) and on verisoul at runtime (`@Service` beans). It auto-extracts `SBT.sol` for Hardhat contract deployment via `maven-dependency-plugin`.

## Requirements

- Java 17+
- Maven 3.8+

## Build

```bash
mvn clean install
```

This installs the jar to your local Maven repository so downstream projects can resolve it.

## Usage

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>ai.megaannum</groupId>
    <artifactId>verisoul-common</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

## Contents

### Service Interfaces (`ai.megaannum.api.service`)

| Interface | Description |
|-----------|-------------|
| `BackendService` | User registration, login, file upload/management |
| `BurnSoulService` | Burn (destroy) an SBT token |
| `CertImageService` | Generate certificate images |
| `ChainQuery2Service` | Blockchain statistics and tx pool data |
| `ChainQueryService` | Query blockchain transactions |
| `GetProfileService` | Retrieve and download user profiles |
| `GetProfilesService` | List profiles by profiler address |
| `GetSoulService` | Retrieve soul data for a user |
| `HasProfileService` | Check if a user has a profile |
| `HasSoulService` | Check if a user has a soul |
| `ListProfilesService` | List all profiles for a soul |
| `ProfileQueryService` | Query profiles with display metadata and download |
| `ProfilesService` | CRUD operations for profile records |
| `SbtDecryptionService` | Decrypt SBT-encrypted profile assets |
| `UpdateSoulService` | Update SBT metadata |
| `UsersService` | Track user login/logout sessions |
| `WalletService` | Wallet address lookups |

### DTOs (`ai.megaannum.api.dto`)

| Class | Description |
|-------|-------------|
| `DecryptedAssetDTO` | Decrypted file content and filename |
| `ProfileDisplayDTO` | Profile display data (identity, issuer, timestamp, download URL) |
| `ProfilesUtils` | Profile record mapped to `ProfilesDB.json` |
| `SoulDTO` | Soul data (identity, URL, score, timestamp) |
| `SoulProfileDataDTO` | Container for lists of souls and issuer addresses |
| `UpdateSoulRequestDTO` | Request payload for updating soul metadata |
| `UserRecordDTO` | User information (email, DOB, gender, major) |

### Smart Contract (`contracts/`)

| File | Description |
|------|-------------|
| `contracts/SBT.sol` | Canonical Solidity source for the Soulbound Token contract |
| `src/main/resources/SBT.sol` | Copy of the above, packaged into the JAR for downstream extraction |
| `src/main/resources/SBT.json` | Compiled ABI + bytecode (Hardhat artifact format), used by verisoul's web3j-maven-plugin to auto-generate `SBT.java` |

### Updating the Smart Contract

When `contracts/SBT.sol` changes:

```bash
# 1. Compile with Hardhat (in verisoul-api which has the Hardhat toolchain)
cd ~/verisoul-api/hardhat
npx hardhat compile

# 2. Extract the compiled artifact back to verisoul-common
python3 -c "import json; d=json.load(open('artifacts/contracts/SBT.sol/SBT.json')); \
  print(json.dumps({'abi':d['abi'],'bytecode':d['bytecode']},indent=2))" \
  > ~/verisoul-common/src/main/resources/SBT.json

# 3. Copy SBT.sol to resources (for JAR packaging)
cp ~/verisoul-common/contracts/SBT.sol ~/verisoul-common/src/main/resources/SBT.sol

# 4. Install
cd ~/verisoul-common && mvn clean install

# Downstream modules auto-update on their next build:
#   verisoul:     web3j-maven-plugin regenerates SBT.java
#   verisoul-api: maven-dependency-plugin extracts SBT.sol into hardhat/contracts/
```
