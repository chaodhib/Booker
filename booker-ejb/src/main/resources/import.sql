--
-- JBoss, Home of Professional Open Source
-- Copyright 2013, Red Hat, Inc. and/or its affiliates, and individual
-- contributors by the @authors tag. See the copyright.txt in the
-- distribution for a full listing of individual contributors.
--
-- Licensed under the Apache License, Version 2.0 (the "License");
-- you may not use this file except in compliance with the License.
-- You may obtain a copy of the License at
-- http://www.apache.org/licenses/LICENSE-2.0
-- Unless required by applicable law or agreed to in writing, software
-- distributed under the License is distributed on an "AS IS" BASIS,
-- WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
-- See the License for the specific language governing permissions and
-- limitations under the License.
--

-- You can use this file to load seed data into the database using SQL statements
insert into department(name, external) values ('Pediatrie', false), ('Gynecologie', false), ('Chirurgie', false), ('Cardiologie', true);

insert into doctor(name, department_id) value ('P Dugrenier', 1), ('J Devalo', 2), ('C Marco', 3), ('O Deckart', 1), ('E Erfg', 2), ('F Froart', 1), ('M Tralou', 1);

insert into patient(name) value ('Charles'), ('Yves'), ('Marc'), ('Marie'), ('Ivette');

insert into appointment(timeStart, timeEnd, doctor_id, patient_id) value ('2015-01-02T10:00:00.0', '2015-01-02 11:00:00.0', 4, 1), ('2015-01-02 08:00:00.0','2015-01-02 09:00:00.0',4,1);