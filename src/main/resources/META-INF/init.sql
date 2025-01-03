# ALTER TABLE games
#     DROP FOREIGN KEY FK_games_GRID_ID;
#
# ALTER TABLE games
#     ADD CONSTRAINT FK_games_GRID_ID
#         FOREIGN KEY (GRID_ID) REFERENCES grids (ID)
#             ON DELETE CASCADE;
#
# ALTER TABLE games
#     DROP FOREIGN KEY FK_games_USER_ID;
#
# ALTER TABLE games
#     ADD CONSTRAINT FK_games_USER_ID
#         FOREIGN KEY (USER_ID) REFERENCES users (ID)
#             ON DELETE CASCADE;
#
# ALTER TABLE clues
#     DROP FOREIGN KEY FK_clues_GRID_ID;
#
# ALTER TABLE clues
#     ADD CONSTRAINT FK_clues_GRID_ID
#         FOREIGN KEY (GRID_ID) REFERENCES grids (ID)
#             ON DELETE CASCADE;
#
# ALTER TABLE grids
#     DROP FOREIGN KEY FK_grids_USER_ID;
#
# ALTER TABLE grids
#     ADD CONSTRAINT FK_grids_USER_ID
#         FOREIGN KEY (USER_ID) REFERENCES users (ID)
#             ON DELETE CASCADE;

INSERT INTO `users` (`ID`, `CREATED_AT`, `PASSWORD`, `ROLE`, `UPDATED_AT`, `USERNAME`)
VALUES ('9a58b695-c6c8-11ef-bddf-0242ac120002', '2024-12-30 17:10:36',
        '$2a$10$tXL3UT1Dy1I3mxY3p8RGn.OGG4KAvuRqDv0Ptuv9RPnJGJPAp8RxC', 'ADMIN', NULL, 'admin')
