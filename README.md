# verisoul-common

Shared interfaces and DTOs for the Verisoul platform. This module defines the service contracts and data transfer objects used by both [verisoul](https://github.com/joeyzyt/verisoul) (implementations) and [verisoul-api](https://github.com/joeyzyt/verisoul-api) (REST controllers).

## Architecture

```
verisoul-common (this module)
    ├── service interfaces
    └── DTOs
        ↑ compile        ↑ compile
        │                │
   verisoul          verisoul-api
   (implementations)  (controllers)
```

- **verisoul-common** is a plain Java library with no Spring Boot runtime.
- **verisoul** depends on verisoul-common at compile time and provides `@Service` implementations.
- **verisoul-api** depends on verisoul-common at compile time (interfaces/DTOs) and on verisoul at runtime (`@Service` beans).

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
