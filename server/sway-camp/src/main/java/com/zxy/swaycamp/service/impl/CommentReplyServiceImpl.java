package com.zxy.swaycamp.service.impl;

import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.comment.ReplyDTO;
import com.zxy.swaycamp.domain.entity.CommentReply;
import com.zxy.swaycamp.mapper.CommentReplyMapper;
import com.zxy.swaycamp.service.CommentReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.utils.SwayUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

/**
 * <p>
 * 回复表 服务实现类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
@Service
public class CommentReplyServiceImpl extends ServiceImpl<CommentReplyMapper, CommentReply> implements CommentReplyService {


    /**
     * 回复评论
     * @param replyDTO 评论信息
     */
    @Override
    public void uploadReply(ReplyDTO replyDTO){
        Integer userId = SwayUtil.getCurrentUserId();
        CommentReply commentReply = new CommentReply();
        if(replyDTO.getReplyId() != null){
            commentReply = lambdaQuery().eq(CommentReply::getId, replyDTO.getReplyId())
                    .eq(CommentReply::getDeleted, false).one();
            if(commentReply == null){
                throw new ServiceException(HttpStatus.NOT_FOUND, "回复评论不存在");
            }
        }
        try{
            commentReply.setUserId(userId);
            commentReply.setReplyId(replyDTO.getReplyId());
            commentReply.setReplyUserId(replyDTO.getReplyUserId());
            if(replyDTO.getReplyId() == null) {
                commentReply.setReplyUserId(null);
            }
            commentReply.setContent(replyDTO.getContent());
            commentReply.setCommentId(replyDTO.getCommentId());
            commentReply.setLikeCount(0);
            commentReply.setCreateTime(LocalDateTime.now());
            commentReply.setDeleted(false);
            save(commentReply);
        }catch (Exception e){
            throw new ServiceException();
        }
    }
}
