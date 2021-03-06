#Primary keys
ALTER TABLE Master ADD CONSTRAINT pk_master PRIMARY KEY (playerID);
ALTER TABLE Parks ADD CONSTRAINT pk_parks_ PRIMARY KEY (`park.key`);
ALTER TABLE Schools ADD CONSTRAINT pk_schools PRIMARY KEY (schoolID);
ALTER TABLE TeamsFranchises ADD CONSTRAINT pk_teamsfranchises PRIMARY KEY (franchID);
ALTER TABLE Batting ADD CONSTRAINT pk_batting PRIMARY KEY (playerID,yearID,stint);
ALTER TABLE Pitching ADD CONSTRAINT pk_pitching PRIMARY KEY (playerID,yearID,stint);
ALTER TABLE AllstarFull ADD CONSTRAINT pk_allstarfull PRIMARY KEY (playerID,gameID);
ALTER TABLE Appearances ADD CONSTRAINT pk_appearances PRIMARY KEY (playerID,teamID,yearID);
ALTER TABLE Managers ADD CONSTRAINT pk_managers PRIMARY KEY (playerID,yearID,teamID,inseason);
ALTER TABLE Teams ADD CONSTRAINT pk_teams PRIMARY KEY (TeamID,yearID);
ALTER TABLE BattingPost ADD CONSTRAINT pk_battingpost PRIMARY KEY (playerID ,yearID, round);
ALTER TABLE PitchingPost ADD CONSTRAINT pk_pitcingpost PRIMARY KEY (playerID ,yearID, round);
ALTER TABLE FieldingPost ADD CONSTRAINT pk_fieldingpost PRIMARY KEY (playerID ,yearID,round,POS);
ALTER TABLE FieldingOF ADD CONSTRAINT pk_fieldingof PRIMARY KEY (playerID ,yearID,stint);
ALTER TABLE FieldingOFsplit ADD CONSTRAINT pk_fofsplit PRIMARY KEY (playerID ,yearID,stint,POS);
ALTER TABLE Salaries ADD CONSTRAINT pk_salaries PRIMARY KEY (playerID,yearID,teamID);
ALTER TABLE SeriesPost ADD CONSTRAINT pk_seriespost PRIMARY KEY (yearID,round);
ALTER TABLE AwardsManagers ADD CONSTRAINT pk_awardsm PRIMARY KEY (yearID,playerID,awardID);
ALTER TABLE AwardsPlayers ADD CONSTRAINT pk_awardsp PRIMARY KEY (yearID,playerID,awardID,lgID);
ALTER TABLE AwardsShareManagers ADD CONSTRAINT pk_awardssm  PRIMARY KEY (yearID,playerID,awardID);
ALTER TABLE AwardsSharePlayers ADD CONSTRAINT pk_awards_sp PRIMARY KEY (yearID,playerID,awardID);
ALTER TABLE CollegePlaying ADD CONSTRAINT pk_collegeplaying PRIMARY KEY (yearID,playerID,schoolID);
ALTER TABLE HomeGames ADD CONSTRAINT pk_homegames PRIMARY KEY (`year.key`,`park.key`,`team.key`);


#Foreign Keys
ALTER TABLE Batting ADD CONSTRAINT fk_batting_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE Batting ADD CONSTRAINT fk_batting_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE Pitching ADD CONSTRAINT fk_pitching_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE Pitching ADD CONSTRAINT fk_pitching_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE Fielding ADD CONSTRAINT fk_fielding_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
SET FOREIGN_KEY_CHECKS=1;
ALTER TABLE Fielding ADD CONSTRAINT fk_fielding_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE AllstarFull ADD CONSTRAINT fk_allstarfull_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE AllstarFull ADD CONSTRAINT fk_allstarfull_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE HallOfFame ADD CONSTRAINT fk_halloffame_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
SET FOREIGN_KEY_CHECKS=1;
ALTER TABLE Managers ADD CONSTRAINT fk_managers_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE Managers ADD CONSTRAINT fk_managers_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE Teams ADD CONSTRAINT fk_teams_fid FOREIGN KEY (franchID) REFERENCES TeamsFranchises(franchID);
ALTER TABLE BattingPost ADD CONSTRAINT fk_battingpost_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE BattingPost ADD CONSTRAINT fk_battingpost_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE BattingPost ADD CONSTRAINT fk_battingpost_yrid FOREIGN KEY (yearID,round) REFERENCES SeriesPost(yearID,round);
ALTER TABLE PitchingPost ADD CONSTRAINT fk_pitchingpost_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE PitchingPost ADD CONSTRAINT fk_pitchingpost_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE PitchingPost ADD CONSTRAINT fk_pitchingpost_yrid FOREIGN KEY (yearID,round) REFERENCES SeriesPost(yearID,round);
ALTER TABLE FieldingOF ADD CONSTRAINT fk_fieldingof_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE ManagersHalf ADD CONSTRAINT fk_mhalf_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE ManagersHalf ADD CONSTRAINT fk_mhalf_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE TeamsHalf ADD CONSTRAINT fk_thalf_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE Salaries ADD CONSTRAINT fk_salaries_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE Salaries ADD CONSTRAINT fk_salaries_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
SET FOREIGN_KEY_CHECKS=1;
ALTER TABLE SeriesPost ADD CONSTRAINT fk_sp_tyid FOREIGN KEY (teamIDwinner,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE SeriesPost ADD CONSTRAINT fk_sp_tylid FOREIGN KEY (teamIDloser,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE AwardsManagers ADD CONSTRAINT fk_awardsm_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE AwardsPlayers ADD CONSTRAINT fk_awardsp_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE AwardsShareManagers ADD CONSTRAINT fk_awardssm_pyaid FOREIGN KEY (yearID,playerID,awardID) REFERENCES AwardsManagers(yearID,playerID,awardID);
ALTER TABLE AwardsSharePlayers ADD CONSTRAINT fk_awardssp_pyaid FOREIGN KEY (yearID,playerID,awardID) REFERENCES AwardsPlayers(yearID,playerID,awardID);
SET FOREIGN_KEY_CHECKS=1;
ALTER TABLE FieldingPost ADD CONSTRAINT fk_fpost_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE FieldingPost ADD CONSTRAINT fk_fpost_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE FieldingPost ADD CONSTRAINT fk_fpost_yrid FOREIGN KEY (yearID,round) REFERENCES SeriesPost(yearID,round);
ALTER TABLE Appearances ADD CONSTRAINT fk_appearances_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
ALTER TABLE Appearances ADD CONSTRAINT fk_appearances_tyid FOREIGN KEY (teamID,yearID) REFERENCES Teams(teamID,yearID);
ALTER TABLE CollegePlaying ADD CONSTRAINT fk_cplaying_pid FOREIGN KEY (playerID) REFERENCES Master(playerID);
SET FOREIGN_KEY_CHECKS=0;
ALTER TABLE CollegePlaying ADD CONSTRAINT fk_cplaying_sid FOREIGN KEY (playerID) REFERENCES Schools(schoolID);
ALTER TABLE FieldingOFsplit ADD CONSTRAINT fk_fofsplit_pysid FOREIGN KEY (playerID,yearID,stint) REFERENCES FieldingOF(playerID,yearID,stint);
SET FOREIGN_KEY_CHECKS=1;
ALTER TABLE HomeGames ADD CONSTRAINT fk_homegames_pid FOREIGN KEY (`park.key`) REFERENCES Parks(`park.key`);
ALTER TABLE HomeGames ADD CONSTRAINT fk_homegames_tyid FOREIGN KEY (`team.key`,`year.key`) REFERENCES Teams(teamID,yearID);