# Spring Boot 2.0 OAuth2 JWT example (incl. Auth server & Resource server) in Kotlin

This example makes use of in-memory client credentials and a symmetric signing key shared between both the Auth & 
Resource servers.

## Getting Started
Start up both the applications:
1. ./gradlew :auth-server:bootRun
1. ./gradlew :resource-server:bootRun

## Getting a  token
Once they have both started, first make a request to the Auth server to get a JWT.

`curl http://UserOne:Password@localhost:8080/oauth/token -d grant_type=client_credentials -d scope=write`

`curl http://UserTwo:Password@localhost:8080/oauth/token -d grant_type=client_credentials -d scope=read`

## Using the token to access a Resource
Using the `access_token` you received back from the Auth server, now make the call to the Resource server
replacing `[TOKEN]`.

`curl http://localhost:8081/resource -H"Authorization: Bearer [TOKEN]"`

`curl -X POST http://localhost:8081/resource -H"Authorization: Bearer [TOKEN]"`
