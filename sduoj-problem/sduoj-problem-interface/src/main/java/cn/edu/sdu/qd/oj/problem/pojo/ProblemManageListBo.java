/**
 * The GNU General Public License
 * Copyright (c) 2020-2020 zhangt2333@gmail.com
 **/

package cn.edu.sdu.qd.oj.problem.pojo;

import cn.edu.sdu.qd.oj.problem.utils.BytesToCheckpointIdsSerializer;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Arrays;

/**
 * @ClassName ProblemManageBo
 * @Description TODO
 * @Author zhangt2333
 * @Date 2020/4/1 19:51
 * @Version V1.0
 **/

@Data
@Table(name = "oj_problems")
public class ProblemManageListBo implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "p_id")
    private Integer problemId;

    @Column(name = "p_is_public")
    private Integer isPublic;

    @Column(name = "u_id")
    private Integer userId;

    @Column(name = "p_title")
    private String problemTitle;

    @Column(name = "p_submit_num")
    private Integer submitNum;

    @Column(name = "p_accept_num")
    private Integer acceptNum;

    @Column(name = "p_time_limit")
    private Integer timeLimit;

    @Column(name = "p_memory_limit")
    private Integer memoryLimit;

    @Column(name = "p_checkpoint_num")
    private Integer checkpointNum;
}