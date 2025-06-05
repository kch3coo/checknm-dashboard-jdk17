-- ----------------------------
-- Aiot 模块
-- ----------------------------
INSERT INTO system_menu
(name, permission, type, sort, parent_id, path, icon, component, component_name, status, visible, keep_alive, always_show)
VALUES('AIOT', '', 1, 1, 0, '/aiot', 'fa-solid:dice-d20', '', '', 0, 1, 1, 1);

SELECT @SystemMenuId := id FROM system_menu WHERE name = 'AIOT';

-- ----------------------------
-- SensorInfo 菜单
-- ----------------------------
-- 菜单 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status, component_name
)
VALUES (
    '传感器信息管理', '', 2, 0, @SystemMenuId,
    'sensor-info', 'fa-solid:stethoscope', 'aiot/sensorInfo/index', 0, 'SensorInfo'
);

-- 按钮父菜单ID
-- 暂时只支持 MySQL。如果你是 Oracle、PostgreSQL、SQLServer 的话，需要手动修改 @parentId 的部分的代码
SELECT @parentId := LAST_INSERT_ID();

-- 按钮 SQL
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '传感器信息查询', 'aiot:sensor-info:query', 3, 1, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '传感器信息创建', 'aiot:sensor-info:create', 3, 2, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '传感器信息更新', 'aiot:sensor-info:update', 3, 3, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '传感器信息删除', 'aiot:sensor-info:delete', 3, 4, @parentId,
    '', '', '', 0
);
INSERT INTO system_menu(
    name, permission, type, sort, parent_id,
    path, icon, component, status
)
VALUES (
    '传感器信息导出', 'aiot:sensor-info:export', 3, 5, @parentId,
    '', '', '', 0
);

INSERT INTO `system_dict_type` (
  `name`,
  `type`,
  `status`,
  `remark`,
  `create_time`,
  `update_time`,
  `deleted`
)
VALUES (
  'AIot 传感器状态',        -- name
  'aiot_sensor_status',        -- type
  0,                    -- status
  '状态：0：WIRED；1：WIRELESS；2：DISABLED',        -- remark
  '2025-05-29 10:00:00', -- create_time
  '2025-05-29 10:00:00', -- update_time
  b'0'                  -- deleted
);

INSERT INTO `system_dict_data` (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES(1, 'CONNECT', '1', 'aiot_sensor_status', 0, '', '', '', '1', '2025-05-29 16:10:01', '1', '2025-05-29 16:10:01', 0);
INSERT INTO `system_dict_data` (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES(2, 'DISCONNECT', '2', 'aiot_sensor_status', 0, '', '', '', '1', '2025-05-29 16:10:13', '1', '2025-05-29 16:10:13', 0);
INSERT INTO `system_dict_data` (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES(3, 'DISABLED', '3', 'aiot_sensor_status', 0, '', '', '', '1', '2025-05-29 16:10:21', '1', '2025-05-29 16:10:21', 0);

INSERT INTO `system_dict_type` (
  `name`,
  `type`,
  `status`,
  `remark`,
  `create_time`,
  `update_time`,
  `deleted`
)
VALUES (
  'AIot 传感器类型',        -- name
  'aiot_sensor_type',        -- type
  0,                    -- status
  '类型：0：WIRED；1：WIRELESS;',        -- remark
  '2025-05-29 10:00:00', -- create_time
  '2025-05-29 10:00:00', -- update_time
  b'0'                  -- deleted
);

-- AIot 传感器状态 WIRED
INSERT INTO `system_dict_data` (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES(1, 'WIRED', '1', 'aiot_sensor_type', 0, '', '', '', '1', '2025-05-29 16:10:01', '1', '2025-05-29 16:10:01', 0);

-- AIot 传感器状态 WIRELESS
INSERT INTO `system_dict_data` (sort, label, value, dict_type, status, color_type, css_class, remark, creator, create_time, updater, update_time, deleted) VALUES(2, 'WIRELESS', '2', 'aiot_sensor_type', 0, '', '', '', '1', '2025-05-29 16:10:13', '1', '2025-05-29 16:10:13', 0);