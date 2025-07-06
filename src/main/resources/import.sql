insert into rol(nombre_rol) values ('ROL_ADMIN');
insert into rol(nombre_rol) values ('ROL_DOCTOR');
insert into rol(nombre_rol) values ('ROL_RECEPCIONISTA');
insert into rol(nombre_rol) values ('ROL_PACIENTE');

insert into estado(nombre_estado) values ('Activo');
insert into estado(nombre_estado) values ('Inactivo');
-- Especialidades
insert into especialidades (nombre, descripcion) values ('Cardiología', 'Diagnóstico y tratamiento de enfermedades del corazón y el sistema circulatorio.');
insert into especialidades (nombre, descripcion) values ('Dermatología', 'Atención de enfermedades de la piel, cabello y uñas.');
insert into especialidades (nombre, descripcion) values ('Pediatría', 'Cuidado médico de bebés, niños y adolescentes.');
insert into especialidades (nombre, descripcion) values ('Neurología', 'Diagnóstico y tratamiento de trastornos del sistema nervioso.');
insert into especialidades (nombre, descripcion) values ('Ginecología', 'Salud del sistema reproductor femenino y control prenatal.');
insert into especialidades (nombre, descripcion) values ('Oftalmología', 'Diagnóstico y tratamiento de enfermedades de los ojos.');
insert into especialidades (nombre, descripcion) values ('Ortopedia', 'Tratamiento de lesiones y enfermedades del sistema musculoesquelético.');
insert into especialidades (nombre, descripcion) values ('Psiquiatría', 'Diagnóstico y tratamiento de trastornos mentales y emocionales.');
insert into especialidades (nombre, descripcion) values ('Endocrinología', 'Estudio de las glándulas endocrinas y trastornos hormonales.');
insert into especialidades (nombre, descripcion) values ('Urología', 'Tratamiento de enfermedades del sistema urinario y reproductor masculino.');
-- Admin de prueba
insert into usuarios(estado_id, rol_id, sexo, numero, apellido, contrasena, nombre, nombre_usua) values (1, 1, 'M', '924102235', 'Anonymus','$2a$10$bR4qy1aE8DQN5pMI8sCBuu9mTkGqQrAPttlN6iNygDXb1R/jAiF2G','Admin', 'ADMIN');
-- Medicamentos 
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Paracetamol', 'Analgésico y antipirético', '2023-10-01', '2025-10-01', 100, 0.50);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Ibuprofeno', 'Antiinflamatorio no esteroideo', '2023-10-01', '2025-10-01', 200, 0.75);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Amoxicilina', 'Antibiótico de amplio espectro', '2023-10-01', '2025-10-01', 150, 1.20);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Aspirina', 'Antiinflamatorio y analgésico', '2023-10-01', '2025-10-01', 120, 0.40);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Omeprazol', 'Inhibidor de la bomba de protones', '2023-10-01', '2025-10-01', 80, 1.50);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Loratadina', 'Antihistamínico para alergias', '2023-10-01', '2025-10-01', 90, 0.60);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Metformina', 'Medicamento para la diabetes tipo 2', '2023-10-01', '2025-10-01', 110, 1.00);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Simvastatina', 'Medicamento para el colesterol alto', '2023-10-01', '2025-10-01', 130, 1.80);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Levotiroxina', 'Medicamento para el hipotiroidismo', '2023-10-01', '2025-10-01', 70, 2.00);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Atorvastatina', 'Medicamento para el colesterol alto', '2023-10-01', '2025-10-01', 140, 1.90);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Clopidogrel', 'Antiplaquetario para prevenir coágulos', '2023-10-01', '2025-10-01', 160, 2.50);
insert into medicamentos(nombre, descripcion, fecha_ingreso, fecha_vencimiento, cantidad, precio_unitario) values ('Furosemida', 'Diurético para la hipertensión', '2023-10-01', '2025-10-01', 170, 0.70);