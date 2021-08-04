# Board 기능 명세

## Board (부모 Entity)

- Single table로 구현

### EduBoard

- user 별로 보여지는 page 권한 설정
- Team 단위로 설정 할 수있게 변경

### NotificationBoard

- 긴급 공지 여부 판단 -> 긴급 공지만 간추려서 볼 수 있음
- Batch로 최근 7일 간격으로 긴급 공지 일반 공지로 전환 필요