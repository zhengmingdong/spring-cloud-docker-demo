package com.zynn.service.module.user.resource;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.QueryChainWrapper;
import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import com.zynn.common.core.dto.UserActiveDTO;
import com.zynn.common.core.dto.UserRecallDTO;
import com.zynn.service.bridge.service.user.UserVisitorInfoRemoteService;
import com.zynn.service.module.user.entity.UserVisitorInfo;
import com.zynn.service.module.user.service.UserVisitorInfoService;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author 袁毅雄
 * @description 访客
 * @date 2019/7/7
 */
@RestController
public class UserVisitorInfoRemoteResource implements UserVisitorInfoRemoteService {

    @Autowired
    private UserVisitorInfoService userVisitorInfoService;

    @Override
    public List<UserRecallDTO> twoRecentVisitors(List<UserActiveDTO> recallUsers) {

        if (CollectionUtils.isEmpty(recallUsers)) {
            return Lists.newArrayList();
        }

        Map<String, List<Long>> recallUserMap = recallUsers.stream().collect(Collectors.groupingBy(UserActiveDTO::getLatestTime))
                .entrySet().stream().collect(
                        Collectors.toMap(
                                e -> e.getKey(),
                                e -> e.getValue().stream().map(UserActiveDTO::getUserId).collect(Collectors.toList()))
                );

        List<CompletableFuture<List<UserRecallDTO>>> future = Lists.newArrayList();
        recallUserMap.keySet().forEach(day -> {

            CompletableFuture<List<UserRecallDTO>> query = CompletableFuture.supplyAsync(() -> {

                List<Long> userIds = recallUserMap.get(day);
                if (CollectionUtils.isEmpty(userIds)) {
                    return Lists.newArrayList();
                }

                List<Map<String, Object>> rows = userVisitorInfoService.listMaps(
                        new QueryWrapper<UserVisitorInfo>()
                                .select("be_visited_user as beVisitedUserId,count(*) as visitNumber,substring_index(group_concat(visit_user order by create_time desc),',',2) as latestVisitedUserIds")
                                .in("be_visited_user", userIds)
                                .ge("create_time", day)
                                .groupBy("be_visited_user")
                                .having("count(*)>2")
                );
                if (CollectionUtils.isEmpty(rows)) {
                    return Lists.newArrayList();
                }

                List<UserRecallDTO> result = rows.stream().map(row -> {
                    Long beVisitedUserId = NumberUtils.LONG_ZERO;
                    Integer visitNumber = NumberUtils.INTEGER_ZERO;
                    List<Long> latestVisitedUserIds = Lists.newArrayList();

                    Object objBeVisitedUserId = row.get("beVisitedUserId");
                    if (Objects.nonNull(objBeVisitedUserId)) {
                        beVisitedUserId = Longs.tryParse(Objects.toString(objBeVisitedUserId));
                    }
                    Object objVisitNumber = row.get("visitNumber");
                    if (Objects.nonNull(objVisitNumber)) {
                        visitNumber = Integer.valueOf(Objects.toString(objVisitNumber));
                    }
                    Object objLatestVisitedUserIds = row.get("latestVisitedUserIds");
                    if (Objects.nonNull(objLatestVisitedUserIds) && StringUtils.isNotBlank((Objects.toString(objLatestVisitedUserIds)))) {
                        latestVisitedUserIds = Arrays.stream(Objects.toString(objLatestVisitedUserIds).split(",")).map(Longs::tryParse).collect(Collectors.toList());
                    }

                    UserRecallDTO dto = new UserRecallDTO();
                    dto.setBeRecallUserId(beVisitedUserId);
                    dto.setFriendNumber(visitNumber);
                    dto.setFriendUserIds(latestVisitedUserIds);
                    return dto;
                }).collect(Collectors.toList());

                return result;
            });

            future.add(query);
        });

        List<UserRecallDTO> result = Lists.newArrayList();
        try {
            result = future.stream().reduce((o1, o2) -> {
                return o1.thenCombine(o2, (list, list2) -> {
                    list.addAll(list2);
                    return list;
                });
            }).get().get();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return result;
    }

}
