-- formatted sql

-- changeset ruslee:1
CREATE TABLE NotificationTask
(
    id        bigserial,
    chat_id   bigserial,
    notice    text,
    dateTime timestamp

);
-- changeset ruslee:2
DROP TABLE notificationtask;

-- changeset ruslee:3
CREATE TABLE NotificationTask
(
    id        bigserial,
    chat_id   bigserial,
    notice    text,
    dateTime timestamp

);
-- changeset ruslee:4
DROP TABLE notificationtask;