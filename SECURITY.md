# Security Policy

Thank you for helping keep WorldFood 3D Adventure and its users safe.

## Supported Versions

WorldFood 3D Adventure is currently preparing for its first public release.

| Version | Supported |
| ------- | --------- |
| 1.0.x   | ✅ Yes |
| < 1.0   | ❌ No |

Only the latest publicly released version of WorldFood 3D Adventure receives
security fixes and maintenance updates.

## Reporting a Vulnerability

If you discover a security vulnerability in WorldFood 3D Adventure, please
report it responsibly.

Please do **not** publish security vulnerabilities in a public GitHub Issue.

When reporting a vulnerability, please include as much information as possible:

- A clear description of the vulnerability
- Steps required to reproduce the issue
- Android version and device information
- The affected WorldFood 3D Adventure version
- Screenshots or logs when appropriate
- The potential security impact
- Any suggested solution, if available

Sensitive information such as credentials, API keys, authentication tokens,
private user information, signing keys, or other secrets must never be posted
in a public issue.

## Security Response

Security reports will be reviewed as soon as reasonably possible.

After a vulnerability has been confirmed, the project will work to:

1. Identify the affected component.
2. Determine the security impact.
3. Develop and test an appropriate fix.
4. Release the fix when it is ready.
5. Publish additional information when appropriate.

Please allow time for a vulnerability to be investigated and corrected before
publicly disclosing technical details.

## Application Security

WorldFood 3D Adventure is an Android application built with Kotlin and
Jetpack Compose.

The project uses security-conscious development practices including:

- Release builds signed with a private signing key
- Release builds configured as non-debuggable
- Code and resource optimization for production builds
- Firebase App Check with Play Integrity for production protection
- Encrypted network communication through supported Firebase services
- No signing credentials committed to this repository
- No private keystore files committed to this repository

## Secrets and Credentials

The following files and information must never be committed to the public
repository:

- Android signing keystores
- Keystore passwords
- `keystore.properties`
- Private API keys
- Authentication tokens
- Service-account private keys
- Personal credentials
- Other production secrets

If a secret is accidentally committed, it should be considered compromised
and must be rotated or revoked as appropriate.

## Firebase Configuration

Firebase configuration and security-sensitive credentials must be handled
according to the security requirements of the corresponding Firebase service.

Firebase backend access must not rely solely on values contained inside the
Android application package.

Server-side security rules, authentication, App Check, and other applicable
controls should be used to protect backend resources.

## Third-Party Dependencies

WorldFood 3D Adventure uses third-party Android libraries and services.

Dependencies should be kept reasonably up to date, and known security
vulnerabilities should be reviewed before production releases.

## Responsible Disclosure

We appreciate responsible security research that:

- Avoids accessing or modifying data belonging to other users
- Avoids disrupting services
- Avoids destructive testing
- Gives the project reasonable time to investigate and resolve a vulnerability
  before public disclosure

Thank you for helping improve the security of WorldFood 3D Adventure.

---

Copyright © 2026 WorldFood 3D Adventure.
All rights reserved.
