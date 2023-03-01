package com.zxy.swaycamp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.comment.ReplyDTO;
import com.zxy.swaycamp.domain.entity.Comment;
import com.zxy.swaycamp.domain.entity.CommentReply;
import com.zxy.swaycamp.domain.entity.User;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.domain.vo.comment.CommentVO;
import com.zxy.swaycamp.domain.vo.comment.ReplyVO;
import com.zxy.swaycamp.mapper.CommentReplyMapper;
import com.zxy.swaycamp.service.CommentReplyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zxy.swaycamp.utils.SwayUtil;
import com.zxy.swaycamp.utils.query.CommonQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    @Resource
    private CommonQuery commonQuery;

    /**
     * 回复评论
     * @param replyDTO 评论信息
     */
    @Override
    public ReplyVO uploadReply(ReplyDTO replyDTO){
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
            return buildReplyVO(commentReply);
        }catch (Exception e){
            throw new ServiceException();
        }
    }


    /**
     * 分页查询评论回复
     * @param index 页码
     * @param size 分页大小
     * @param commentId 评论ID
     * @return  ReplyVO
     */
    @Override
    public PageVO<ReplyVO> listReplyPage(Integer index, Integer size, Integer commentId){
        Page<CommentReply> replyPage = lambdaQuery().eq(CommentReply::getCommentId, commentId)
                .page(new Page<>(index, size));
        if(replyPage == null || CollectionUtils.isEmpty(replyPage.getRecords())){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "查询页数超出总页数");
        }
        List<ReplyVO> replyVOS =  replyPage.getRecords().stream().map(item -> buildReplyVO(item)).collect(Collectors.toList());
        PageVO<ReplyVO> pageVO = new PageVO<>();
        pageVO.setList(replyVOS);
        pageVO.setTotal((int) replyPage.getTotal());
        return pageVO;
    }


    /**
     * 创建回复信息
     */
    private ReplyVO buildReplyVO(CommentReply commentReply){
        ReplyVO replyVO = new ReplyVO();
        BeanUtil.copyProperties(commentReply, replyVO);
        replyVO.setDate(commentReply.getCreateTime().toString());
        User user = commonQuery.getUser(commentReply.getUserId());
        replyVO.setUsername(user.getUsername());
        replyVO.setAvatar(user.getAvatar());
        if (commentReply.getReplyUserId() != null) {
            user = commonQuery.getUser(commentReply.getReplyUserId());
            replyVO.setReplyUsername(user.getUsername());
            replyVO.setReplyAvatar(user.getAvatar());
        }
        return replyVO;
    }
}
