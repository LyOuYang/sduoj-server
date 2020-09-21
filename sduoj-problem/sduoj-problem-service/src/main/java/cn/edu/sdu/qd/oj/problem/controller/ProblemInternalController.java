/**
 * The GNU General Public License
 * Copyright (c) 2020-2020 zhangt2333@gmail.com
 **/

package cn.edu.sdu.qd.oj.problem.controller;

import cn.edu.sdu.qd.oj.common.exception.InternalApiException;
import cn.edu.sdu.qd.oj.problem.api.ProblemApi;
import cn.edu.sdu.qd.oj.problem.service.ProblemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @ClassName ProblemInternalController
 * @Description TODO
 * @Author zhangt2333
 * @Date 2020/4/9 15:46
 * @Version V1.0
 **/

@RestController
public class ProblemInternalController implements ProblemApi {

    @Autowired
    private ProblemService problemService;

    @Override
    public Map<Integer, String> queryIdToTitleMap() throws InternalApiException {
        return problemService.queryIdToTitleMap();
    }
}