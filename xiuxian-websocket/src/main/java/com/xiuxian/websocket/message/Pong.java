package com.xiuxian.websocket.message;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 心跳发送
 *
 * @author Chenxiao 591343671@qq.com
 */
@Data
@ApiModel(value = "心跳响应")
public class Pong {
    //
    String toId;
}
