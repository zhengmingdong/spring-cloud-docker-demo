CREATE TABLE `bs_event_story_audio`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `audio_pic` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '音乐展示图片',
  `audio_name` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '音乐展示名称',
  `audio_author` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '音乐作者',
  `audio_url` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NULL DEFAULT NULL COMMENT '音乐地址',
  `sort` int(255) NULL DEFAULT NULL COMMENT '展示排序',
  `is_default` tinyint(1) NOT NULL DEFAULT 0 COMMENT '是否为默认选中音乐,1是;0否',
  `is_valid` tinyint(1) NOT NULL DEFAULT 1 COMMENT '是否有效,0无效/1有效',
  `create_time` timestamp(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人',
  `update_time` timestamp(0) NOT NULL DEFAULT CURRENT_TIMESTAMP(0) ON UPDATE CURRENT_TIMESTAMP(0) COMMENT '更新时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '更新人',
  `is_shelf` tinyint(4) NULL DEFAULT 0 COMMENT '是否上架音乐:1上架,0未上架',
  `is_publish` tinyint(4) NULL DEFAULT 0 COMMENT '是否发布音乐:1发布,0未发布',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '动态plog的音频资源' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Records of bs_event_story_audio
-- ----------------------------
INSERT INTO `bs_event_story_audio` VALUES (1, 'https://app.public.zhuiyinanian.com/8e5732ed-5284-6235-a66b-7892da65539c-20191017173854.png', '啦啦', '林俊杰1', 'https://app.public.zhuiyinanian.com/%E6%88%91%E6%9B%BE.mp3', 11, 0, 1, '2019-10-16 13:58:14.108', NULL, '2019-10-18 13:37:48', NULL, 1, 1);
INSERT INTO `bs_event_story_audio` VALUES (2, 'https://app.public.zhuiyinanian.com/9c7a5870-cff2-623e-8e86-c43bb2cc6d0d-20191024165839.jpg', '起风了', '吴青峰', 'https://app.public.zhuiyinanian.com/60a9dd8d-63a9-4ce1-f03a-0439a8818a18-20191018145241.mp3', 4, 1, 1, '2019-10-16 15:11:02.119', NULL, '2019-10-24 16:58:49', NULL, 1, 1);
INSERT INTO `bs_event_story_audio` VALUES (3, 'https://app.public.zhuiyinanian.com/fc871c9e-a510-1c05-a70c-f6d5ecc82460-20191017174200.png', '如果当时', '许嵩', 'https://app.public.zhuiyinanian.com/11fb30c8-cd99-3b9f-064b-522c0d3d4e5d-20191018145219.mp3', 2, 0, 1, '2019-10-16 15:10:06.993', NULL, '2019-10-18 14:52:33', NULL, 1, 1);
INSERT INTO `bs_event_story_audio` VALUES (4, 'https://app.public.zhuiyinanian.com/40217494-ff34-af03-c6a4-dfae3ba154a2-20191017174144.png', '消愁', '毛不易', 'https://app.public.zhuiyinanian.com/61c61917-f4bb-d498-7ccc-269d289fb38e-20191018145259.mp3', 3, 0, 1, '2019-10-16 15:10:32.528', NULL, '2019-10-18 14:53:07', NULL, 1, 1);
