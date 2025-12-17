# Spring User Manager

- [About](#about)
- [Features](#features)
- [Domain Model](#domain-model)

## About

Spring User Manager is a secure, scalable, enterprise-ready microservice for managing users, roles, and security permissions. 

## Features

- Manage the user lifecycle: create users, delete users, update user status. 
- Role-based access control: manage security permissions based on role and assign roles to users. 
- Password hashing for secure credential storage.
- Secure third party authentication via OpenID Connect.
- Auditing and compliance data logging. 
- REST API gateway access.

## Domain Model

There are three primary objects in the domain model:

- User: Can sign in with their credentials and may be assigned a role.
- Role: Defines a set of permissions to be granted to users.
- Permission: Allows access to information or the ability to perform an action.

Users have no intrisic security permissions. Grant users security access by assigning permissions to roles, then assigning roles to users. 