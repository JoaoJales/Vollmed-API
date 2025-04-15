create table consultations(
    id bigint not null auto_increment,
    doctor_id bigint not null,
    patient_id bigint not null,
    data datetime not null,

    primary key (id),
    constraint fk_consultations_doctor_id foreign key (doctor_id) references doctors(id),
    constraint fk_consultations_patient_id foreign key (patient_id) references patients(id)
);