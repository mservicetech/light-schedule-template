
--DROP table IF EXISTS  Goals;
--DROP table IF EXISTS  Client;
--DROP table IF EXISTS  ClientGoalOwnership;
--DROP table IF EXISTS  GoalTypes;

CREATE TABLE message (
  ID VARCHAR(120) PRIMARY KEY,
  DESTINATION VARCHAR(1000) NULL,
  HEADERS VARCHAR(1000) NULL,
  PAYLOAD VARCHAR(1000) NOT NULL,
  PUBLISHED INT DEFAULT 0
);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F2', null,
     '{"connectionRole":"PRIMARY","ID":"F58606AE-58BB-4B28-AF72-B89F380267F2"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F3', null,
     '{"connectionRole":"PRIMARY","ID":"F58606AE-58BB-4B28-AF72-B89F380267F3"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F4', null,
     '{"connectionRole":"PRIMARY","ID":"F58606AE-58BB-4B28-AF72-B89F380267F4"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F5', null,
     '{"connectionRole":"PRIMARY","ID":"F58606AE-58BB-4B28-AF72-B89F380267F5"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F6', null,
     '{"connectionRole":"PRIMARY","ID":"F58606AE-58BB-4B28-AF72-B89F380267F6"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F7', null,
     '{"connectionRole":"PRIMARY","ID":"F58606AE-58BB-4B28-AF72-B89F380267F7"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F8', null,
     '{"connectionRole":"PRIMARY","ID":"F58656AE-58BB-4B28-AF72-B89F380267F8"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F9', null,
     '{"connectionRole":"PRIMARY","ID":"F58656AE-58BB-4B28-AF72-B89F380267F9"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F58606AE-58BB-4B28-AF72-B89F380267F0', null,
     '{"connectionRole":"PRIMARY","ID":"F58646AE-58BB-4B28-AF72-B89F380267F0"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F68606AE-58BB-4B28-AF72-B89F380267F0', null,
     '{"connectionRole":"PRIMARY","ID":"F58636AE-58BB-4B28-AF72-B89F380267F2"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F98606AE-58BB-4B28-AF72-B89F380267F2', null,
     '{"connectionRole":"PRIMARY","ID":"F98616AE-58BB-4B28-AF72-B89F380267F2"}', '{PAYLOAD: NULL}', 0);

Insert into message (ID, DESTINATION, HEADERS,PAYLOAD,  PUBLISHED) values ('F78606AE-58BB-4B28-AF72-B89F380267F2', null,
     '{"connectionRole":"PRIMARY","ID":"F68626AE-58BB-4B28-AF72-B89F380267F2"}', '{PAYLOAD: NULL}', 0);

