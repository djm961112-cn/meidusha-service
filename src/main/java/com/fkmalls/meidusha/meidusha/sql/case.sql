CREATE TABLE `case` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `p_id` int NOT NULL DEFAULT '0' COMMENT '父ID',
                        `case_code` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '测试用例编码',
                        `case_title` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8_general_ci NOT NULL COMMENT '测试用例标题',
                        `case_condition` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '前置条件',
                        `case_expected_results` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '预期结果',
                        `case_actually_results` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '实际结果',
                        `case_operation_steps` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '操作步骤',
                        `case_tag` int unsigned NOT NULL DEFAULT '0' COMMENT '用例标签 0=正式,1=冒烟,2=非叶子节点',
                        `case_del_tag` int NOT NULL DEFAULT '0' COMMENT '删除标记 0=存在,1=删除',
                        `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
                        `create_time` datetime DEFAULT NULL COMMENT '创建时间',
                        `update_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
                        `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='测试用例集合';

CREATE TABLE `execute_job` (
                               `id` bigint NOT NULL COMMENT 'id',
                               `execute_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务名称',
                               `execute_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT '任务执行人',
                               `execute_start_time` datetime DEFAULT NULL COMMENT '任务开始执行时间',
                               `create_user` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '创建人',
                               `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                               `update_user` varchar(0) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT '更新人',
                               `update_time` datetime DEFAULT NULL COMMENT '更新时间',
                               `del_tag` int NOT NULL DEFAULT '0' COMMENT '删除标志 0=存在，1=删除',
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='用例任务表';

CREATE TABLE `execute_job_item` (
                                    `id` bigint NOT NULL,
                                    `case_id` bigint DEFAULT NULL COMMENT '测试用例id',
                                    `execute_id` bigint DEFAULT NULL COMMENT '执行id',
                                    `del_tag` int DEFAULT NULL COMMENT '删除标志 0=存在，1=删除',
                                    `create_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                                    PRIMARY KEY (`id`),
                                    KEY `case_id` (`case_id`),
                                    KEY `execute_id` (`execute_id`),
                                    CONSTRAINT `case_id` FOREIGN KEY (`case_id`) REFERENCES `case` (`id`) ON UPDATE CASCADE,
                                    CONSTRAINT `execute_id` FOREIGN KEY (`execute_id`) REFERENCES `execute_job` (`id`) ON DELETE RESTRICT ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci ROW_FORMAT=DYNAMIC COMMENT='测试用例与执行表关联';

CREATE TABLE `demand` (
                          `id` bigint NOT NULL,
                          `demand_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '需求名称',
                          `demand_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '需求地址',
                          `product_id` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '关联产品',
                          `qa_id` int DEFAULT NULL COMMENT '关联测试',
                          `dev_id` int DEFAULT NULL COMMENT '关联后端',
                          `front_id` int DEFAULT NULL COMMENT '关联前端',
                          `demand_status` int NOT NULL DEFAULT '10' COMMENT '需求进度 10=研发中，20=已提测，30=提测驳回，40=测试完成，50=已发布',
                          `demand_del_tag` int NOT NULL DEFAULT '0' COMMENT '删除标记 0=存在，1=删除',
                          `create_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                          `update_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                          `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='需求\n';

CREATE TABLE `test_execute_message` (
                                        `id` bigint NOT NULL,
                                        `test_report_id` bigint NOT NULL COMMENT '测试报告id',
                                        `test_type` int NOT NULL COMMENT '测试类型 10=功能测试，20=接口测试，30=性能测试',
                                        `start_time` datetime DEFAULT NULL COMMENT '开始时间',
                                        `end_time` datetime DEFAULT NULL COMMENT '结束时间',
                                        `plan_execute_case_all_num` int DEFAULT NULL COMMENT '计划执行用例总数',
                                        `rel_execute_case_all_num` int DEFAULT NULL COMMENT '实际执行用例总数',
                                        `rel_pass_case_all_num` int DEFAULT NULL COMMENT '实际通过用例总数',
                                        `find_bug_num` int DEFAULT NULL COMMENT '发现BUG数',
                                        `fix_bug_num` int DEFAULT NULL COMMENT '修复BUG数',
                                        `remark` varchar(255) DEFAULT NULL COMMENT '备注',
                                        `create_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL,
                                        `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                                        `update_user` varchar(255) DEFAULT NULL,
                                        `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='测试执行信息\n';

CREATE TABLE `test_project` (
                                `id` bigint NOT NULL,
                                `test_report_id` bigint NOT NULL COMMENT '测试报告id',
                                `project_nmae` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '工程名',
                                `branch_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '分支名',
                                `gitlab_address` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT 'gitlab地址',
                                `app_defect_message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '应用缺陷值',
                                `coverage_test_message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '覆盖率测试',
                                `unit_test_message` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '单元测试',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='测试项目';

CREATE TABLE `test_report` (
                               `id` bigint NOT NULL,
                               `test_user` varchar(255) NOT NULL COMMENT '测试执行人',
                               `test_conclusion` varchar(255) NOT NULL COMMENT '测试结论',
                               `test_plan` varchar(255) DEFAULT NULL COMMENT '关联提测单',
                               `demand_name` varchar(255) DEFAULT NULL COMMENT '关联需求',
                               `test_start_time` datetime DEFAULT NULL COMMENT '测试开始时间',
                               `test_end_time` datetime DEFAULT NULL COMMENT '测试结束时间',
                               `test_explain` varchar(255) DEFAULT NULL COMMENT '测试说明',
                               `risk_explain` varchar(255) DEFAULT NULL COMMENT '风险说明',
                               `test_range` varchar(255) DEFAULT NULL COMMENT '测试范围',
                               `performance_result` int DEFAULT NULL COMMENT '性能测试结果 10=无需压测，20=压测通过，30=压测不通过',
                               `perfor_report_address` varchar(255) DEFAULT NULL COMMENT '性能测试报告地址',
                               `benchmark_result` int DEFAULT NULL COMMENT '基准测试结果 10=无需压测，20=压测通过，30=压测不通过',
                               `benchmark_report_address` varchar(255) DEFAULT NULL COMMENT '基准测试报告地址',
                               `smoke_pass_num` int DEFAULT NULL COMMENT '冒烟通过率',
                               `smoke_test_address` varchar(255) DEFAULT NULL COMMENT '冒烟测试用例地址',
                               `create_user` varchar(255) DEFAULT NULL,
                               `create_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
                               `update_user` varchar(255) DEFAULT NULL,
                               `update_time` datetime DEFAULT CURRENT_TIMESTAMP,
                               PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COMMENT='TestReport\n测试报告\n';

CREATE TABLE `user` (
                        `id` bigint NOT NULL COMMENT 'id',
                        `nick_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci DEFAULT NULL COMMENT '真实姓名',
                        `user_name` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户名',
                        `password` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '用户密码',
                        `role_id` bigint NOT NULL COMMENT '关联角色ID',
                        `ding_mobile` varchar(255) DEFAULT NULL COMMENT '钉钉手机号码',
                        `image_path` varchar(255) DEFAULT NULL COMMENT '头像地址',
                        `user_token` varchar(255) DEFAULT NULL COMMENT '用户token',
                        `del_tag` int NOT NULL COMMENT '删除标志，0=存在，1=删除',
                        `state` int DEFAULT NULL COMMENT '在线状态 0=在线，1=下线',
                        `create_user` varchar(255) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL COMMENT '创建人',
                        `create_time` datetime NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '创建时间',
                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;

CREATE TABLE `user_role` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `role` varchar(255) DEFAULT NULL COMMENT '角色',
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb3;