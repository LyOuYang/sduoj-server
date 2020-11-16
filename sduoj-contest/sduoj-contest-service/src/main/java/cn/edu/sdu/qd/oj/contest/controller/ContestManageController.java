/*
 * Copyright 2020-2020 the original author or authors.
 *
 * Licensed under the General Public License, Version 3.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.gnu.org/licenses/gpl-3.0.en.html
 */

package cn.edu.sdu.qd.oj.contest.controller;

import cn.edu.sdu.qd.oj.auth.enums.PermissionEnum;
import cn.edu.sdu.qd.oj.common.annotation.UserSession;
import cn.edu.sdu.qd.oj.common.entity.ApiResponseBody;
import cn.edu.sdu.qd.oj.common.entity.PageResult;
import cn.edu.sdu.qd.oj.common.entity.UserSessionDTO;
import cn.edu.sdu.qd.oj.common.enums.ApiExceptionEnum;
import cn.edu.sdu.qd.oj.common.exception.ApiException;
import cn.edu.sdu.qd.oj.common.util.AssertUtils;
import cn.edu.sdu.qd.oj.contest.client.ProblemClient;
import cn.edu.sdu.qd.oj.contest.dto.*;
import cn.edu.sdu.qd.oj.contest.service.ContestManageService;
import cn.edu.sdu.qd.oj.contest.service.ContestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequestMapping("/manage/contest")
public class ContestManageController {

    @Autowired
    private ContestService contestService;

    @Autowired
    private ContestManageService contestManageService;

    @Autowired
    private ProblemClient problemClient;

    @PostMapping("/create")
    @ApiResponseBody
    public Long create(@RequestBody @Valid ContestCreateReqDTO reqDTO,
                       @RequestHeader("authorization-userId") Long userId) {
        // 增补
        reqDTO.setUserId(userId);

        // 校验 当前时间<开始时间<结束时间
        AssertUtils.isTrue(reqDTO.getGmtStart().after(new Date()), ApiExceptionEnum.CONTEST_TIME_ERROR);
        AssertUtils.isTrue(reqDTO.getGmtStart().before(reqDTO.getGmtEnd()), ApiExceptionEnum.CONTEST_TIME_ERROR);

        // 校验 problemCode
        try {
            List<String> problemCodeList = reqDTO.getProblems()
                    .stream()
                    .map(ContestProblemListDTO::getProblemCode)
                    .collect(Collectors.toList());
            AssertUtils.isTrue(problemClient.validateProblemCodeList(problemCodeList), ApiExceptionEnum.PROBLEM_NOT_FOUND);
        } catch (Exception e) {
            throw new ApiException(ApiExceptionEnum.UNKNOWN_ERROR);
        }

        // TODO: 校验 problemDescriptionId



        return contestManageService.create(reqDTO);
    }

    @GetMapping("/page")
    @ApiResponseBody
    public PageResult<ContestListDTO> page(ContestListReqDTO reqDTO) {
        return contestService.page(reqDTO);
    }

    @GetMapping("/query")
    @ApiResponseBody
    public ContestManageDTO query(@RequestParam("contestId") long contestId,
                                  @UserSession UserSessionDTO userSessionDTO) {
        ContestManageDTO contestManageDTO = contestManageService.query(contestId);
        // 超级管理员一定可以查到比赛详情
        if (PermissionEnum.SUPERADMIN.in(userSessionDTO)) {
            return contestManageDTO;
        }
        // 非比赛出题者看不到
        if (userSessionDTO.userIdNotEquals(contestManageDTO.getUserId())) {
            throw new ApiException(ApiExceptionEnum.USER_NOT_MATCHING);
        }
        return contestManageDTO;
    }

    @PostMapping("/update")
    @ApiResponseBody
    public Void update(@RequestBody @Valid ContestDTO reqDTO,
                       @UserSession UserSessionDTO userSessionDTO) {
        // 校验 当前时间<开始时间<结束时间
        AssertUtils.isTrue(reqDTO.getGmtStart().before(reqDTO.getGmtEnd()), ApiExceptionEnum.CONTEST_TIME_ERROR);

        contestManageService.update(reqDTO, userSessionDTO);
        return null;
    }


}