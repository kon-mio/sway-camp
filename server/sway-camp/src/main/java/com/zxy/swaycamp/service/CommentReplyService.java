package com.zxy.swaycamp.service;

import com.zxy.swaycamp.domain.dto.comment.CommentDTO;
import com.zxy.swaycamp.domain.dto.comment.ReplyDTO;
import com.zxy.swaycamp.domain.entity.CommentReply;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.domain.vo.comment.CommentVO;
import com.zxy.swaycamp.domain.vo.comment.ReplyVO;

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

    /**
     * 分页查询评论回复
     * @param index 页码
     * @param size 分页大小
     * @param commentId 评论ID
     * @return  ReplyVO
     */
    PageVO<ReplyVO> listReplyPage(Integer index, Integer size, Integer commentId);
}
