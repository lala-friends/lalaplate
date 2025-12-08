## boilerplate
kotlin, spring boot with mysql, mongodb, reids.  
Make fast **rest api** and **business logic**. 

## Requirements
jdk21, docker

## How to Run
```
docker-compose up -d
./gradlew bootRun
```

## Swagger
http://localhost:8080/swagger-ui/index.html


## claude
### Commands
| 명령어                | 설명                              |
|--------------------|---------------------------------|
| /go                | plan.md에서 다음 테스트를 찾아 TDD 사이클 실행 |
| /red <기능>          | 실패하는 테스트 작성 (RED 단계)            |
| /green             | 최소 코드로 테스트 통과 (GREEN 단계)        |
| /tidy              | 구조적 리팩토링 (Tidy First)           |
| /refactor <패턴>     | 특정 리팩토링 패턴 적용                   |
| /test              | 전체 테스트 실행 및 결과 보고               |
| /commit-structural | 구조적 변경 커밋                       |
| /commit-behavioral | 기능적 변경 커밋                       |
| /fix <버그>          | TDD 방식으로 결함 수정                  |
| /status            | 현재 TDD 진행 상태 확인                 |
| /cycle <기능>        | 전체 Red-Green-Refactor 사이클 실행    |
