SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS user_info (
  id         int unsigned NOT NULL AUTO_INCREMENT,
  user_type  tinyint unsigned NOT NULL,
  user_name  varchar(24) NOT NULL,
  password   varchar(256) NOT NULL,
  company    varchar(64) NOT NULL,
  phone_number varchar(24),
  department varchar(64),
  PRIMARY KEY (id),
  unique index user_info_name (user_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS company_info (
  id           int unsigned NOT NULL AUTO_INCREMENT,
  company      varchar(64) NOT NULL,
  factory_name varchar(64),
  PRIMARY KEY (id),
  unique index company_info_name (company)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS aiot_sensor_info (
  id           bigint NOT NULL AUTO_INCREMENT COMMENT '传感器id',
  uuid         varchar(64) NOT NULL COMMENT 'UUID',
  mac          varchar(24) NOT NULL COMMENT 'MAC',
  gateway_mac  varchar(24) NULL COMMENT '网关MAC',
  type         tinyint unsigned NOT NULL COMMENT '类型',
  producer     varchar(64) NOT NULL COMMENT '厂商',
  qr_code      varchar(256) NOT NULL COMMENT '传感器二维码',
  version      varchar(64) NULL COMMENT '版本信息',
  status       tinyint unsigned NOT NULL COMMENT '状态',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  index sensor_status (status),
  unique index sensor_uuid (uuid),
  unique index sensor_mac (mac),
  unique index sensor_qrcode (qr_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='传感器信息';


CREATE TABLE IF NOT EXISTS sensor_characteristics (
  version      varchar(64) NOT NULL,
  uuid         varchar(64) NOT NULL,
  encryption   varchar(64) NOT NULL,
  byte_address int NOT NULL,
  message_type tinyint unsigned NOT NULL,
  description  varchar(64) NOT NULL,
  io_type      tinyint unsigned NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS aiot_machine_info (
  id              bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '设备id',
  company_name    varchar(64) NOT NULL COMMENT '公司名称',
  factory_name    varchar(64) NULL COMMENT '工厂名称',
  product_line    varchar(64) NULL COMMENT '设备产线',
  device_name     varchar(64) NOT NULL COMMENT '设备名称',
  device_type     varchar(64) NOT NULL COMMENT '设备类型',
  device_image    mediumblob COMMENT '设备图片',
  last_check_time DATETIME COMMENT '最新检测时间',
  last_maintainer varchar(16) COMMENT '最新检测人员',
    `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  index machine_info_company (company_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备信息';


CREATE TABLE IF NOT EXISTS aiot_machine_location_info (
  id               bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '设备位置id',
  qr_code          varchar(256) NOT NULL COMMENT '设备位置二维码',
  machine_id       int unsigned NOT NULL COMMENT '设备id',
  sensor_id        int unsigned COMMENT '传感器id',
  location_info    varchar(64) NOT NULL COMMENT '设备位置',
  latest_result    tinyint unsigned COMMENT '最新检测结果',
  last_check_time  DATETIME COMMENT '最新检测时间',
  last_maintainer  varchar(16) COMMENT '最新检测人员',
  location_image   mediumblob COMMENT '设备位置图片',
  `creator` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `updater` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
  `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
  `deleted` bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
  `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
  PRIMARY KEY (id),
  unique index machine_location_info_qrcode (qr_code),
  index machine_location_info_machine_id (machine_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='设备位置信息';


CREATE TABLE IF NOT EXISTS bluetooth_gateway_info (
  gateway_mac varchar(24) NOT NULL,
  version     varchar(24) NOT NULL,
  ac_address  varchar(64) NOT NULL,
  PRIMARY KEY (gateway_mac)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS config (
  name         varchar(64) NOT NULL,
  value        varchar(256) NOT NULL,
  description  varchar(256) NOT NULL,
  PRIMARY KEY (name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS machine_measurement_tasks (
  sensor_id           int unsigned NOT NULL,
  machine_location_id int unsigned NOT NULL,
  task_id             varchar(32) NOT NULL,
  maintainer          varchar(16),
  type                tinyint unsigned NOT NULL,
  method              tinyint unsigned NOT NULL,
  start_time          datetime(3) not null,
  end_time            datetime(3),
  status              tinyint unsigned NOT NULL,
  PRIMARY KEY (task_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS aiot_qrcode_info (
                                                id          int unsigned NOT NULL AUTO_INCREMENT COMMENT '二维码id',
                                                info        varchar(256) NOT NULL COMMENT '二维码信息',
    create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '二维码创建时间',
    type        varchar(255) NOT NULL COMMENT '二维码类型',
    status      int unsigned NOT NULL COMMENT '二维码状态',
    `creator`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '创建者',
    `updater`   varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NULL DEFAULT '' COMMENT '更新者',
    `update_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    `deleted`   bit(1) NOT NULL DEFAULT b'0' COMMENT '是否删除',
    `tenant_id` bigint NOT NULL DEFAULT 0 COMMENT '租户编号',
    PRIMARY KEY (id),
    unique index qrcode_infomation (info)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='二维码信息';


CREATE TABLE IF NOT EXISTS db_logger (
  id           int unsigned NOT NULL AUTO_INCREMENT,
  user_id      int unsigned NOT NULL,
  user_type    tinyint unsigned NOT NULL,
  action_type  tinyint unsigned NOT NULL,
  table_name   varchar(256) NOT NULL,
  action_info  varchar(256) NOT NULL,
  result       varchar(256) NOT NULL,
  time         DATETIME NOT NULL,
  PRIMARY KEY (id),
  index db_logger_user_id (user_id),
  index db_logger_user_type (user_type),
  index db_logger_action_type (action_type),
  index db_logger_table_name (table_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS request_logger (
  source       varchar(24) NOT NULL,
  requestName  varchar(24) NOT NULL,
  requestParam varchar(256) NOT NULL,
  reqresponse  varchar(256) NOT NULL,
  result       varchar(256) NOT NULL,
  time         DATETIME NOT NULL,
  index request_logger_requestName (requestName),
  index request_logger_result (result),
  index request_logger_time (time)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


CREATE TABLE IF NOT EXISTS vibration_records (
  sensor_id           int unsigned NOT NULL,
  task_id             varchar(32) NOT NULL,
  timestamp           datetime(3) not null,
  x_axis_rms          double,
  y_axis_rms          double,
  z_axis_rms          double,
  x_axis_ma_rms       double,
  y_axis_ma_rms       double,
  z_axis_ma_rms       double,
  x_axis_peak_to_peak double,
  y_axis_peak_to_peak double,
  z_axis_peak_to_peak double,
  PRIMARY KEY (timestamp,task_id,sensor_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;