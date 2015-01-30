
    create table Appointment (
        id integer not null auto_increment,
        timeStart datetime,
        timeEnd datetime,
        doctor_id integer,
        patient_id integer,
        primary key (id)
    );

    create table Department (
        id integer not null auto_increment,
        external bit,
        name varchar(255) not null,
        primary key (id)
    );

    create table Doctor (
        id integer not null auto_increment,
        name varchar(255) not null,
        department_id integer,
        primary key (id)
    );

    create table Patient (
        id integer not null auto_increment,
        name varchar(255) not null,
        primary key (id)
    );

    alter table Department 
        add constraint UK_biw7tevwc07g3iutlbmkes0cm  unique (name);

    alter table Doctor 
        add constraint UK_d8rr250pbtwgdivpibd424hv0  unique (name);

    alter table Patient 
        add constraint UK_envvd9t39mue7rd8p8el7svct  unique (name);

    alter table Appointment 
        add constraint FK_1hvuax8arxsbm16x54oksxko7 
        foreign key (doctor_id) 
        references Doctor (id);

    alter table Appointment 
        add constraint FK_oy39osrhv8yum5bv1i82y98b2 
        foreign key (patient_id) 
        references Patient (id);

    alter table Doctor 
        add constraint FK_cdedep2wcu2aa9mf2fh5kfatl 
        foreign key (department_id) 
        references Department (id);
