package com.xiuxian.chat.vo.friendlist;


import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 群组列表信息Vo
 *
 * @author Chenxiao 591343671@qq.co
 */
@Data
@ApiModel(value = "群组列表Vo")
public class GroupListVo {
    private List<GroupListItemVo> groupList;
}
