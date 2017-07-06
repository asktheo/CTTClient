DROP table IF EXISTS gps_observation;

CREATE TABLE partners.gps_observation
(
    id serial,
    unitid integer,
    euringno character varying(5),
    dt character varying(20),
    gps_date character varying(10),
    gps_time character varying(8),
    geom geometry,
    lat numeric(8, 6),
    lon numeric(8, 6),
    hdop numeric(4, 2),
    fix integer,
    cog integer,
    speed numeric(4, 2),
    alt numeric(6, 2),
    data_voltage numeric(4, 2),
    solar_charge numeric(4, 2),
    solar_current numeric(4, 2),
    nsats integer,
    vdop numeric(4, 2),
    activity integer,
    inactivity integer,
    temperature numeric(6,2),
    time_to_fix integer
)
WITH (
    OIDS = FALSE
)
TABLESPACE pg_default;

ALTER TABLE partners.gps_observation
    OWNER to partners;

CREATE index gps_observation_sidx on gps_observation USING gist(geom);