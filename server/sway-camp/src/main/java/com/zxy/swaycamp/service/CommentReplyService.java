package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.dto.comment.CommentDTO;
import com.zxy.swaycamp.domain.dto.comment.ReplyDTO;
import com.zxy.swaycamp.domain.entity.CommentReply;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 回复表 服务类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
public interface CommentReplyService extends IService<CommentReply> {
    /**
     * 回复评论
     * @param replyDTO 评论信息
     */
    void uploadReply(ReplyDTO replyDTO);
}
