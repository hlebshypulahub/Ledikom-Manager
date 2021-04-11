create type employee_position as enum (
    'Директор',
    'Менеджер',
    'Гл. Бухгалтер',
    'Бухгалтер',
    'Провизор',
    'Фармацевт',
    'Укладчик-упаковщик',
    'Водитель'
    );

create type employee_category as enum (
    '1',
    '2',
    'Высшая'
    );
	
create type facility_status as enum (
    'Аптека',
    'Офис',
    'Склад'
    );
	
create type contract_type as enum (
    'По основному месту работы',
    'По внутреннему совместительству',
    'По внешнему совместительству'
    );
	
create type facility_category as enum (
    '2',
    '5'
    );
	
create type checkup_type_name as enum (
    'Проверка помещений',
    'Реализация',
    'Персонал',
    'Приемка и хранение'
    );
	
create type checkup_answer as enum (
    'Да',
    'Нет'
    );

	
