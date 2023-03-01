package com.zxy.swaycamp.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.toolkit.BeanUtils;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zxy.swaycamp.common.constant.HttpStatus;
import com.zxy.swaycamp.common.exception.ServiceException;
import com.zxy.swaycamp.domain.dto.comment.CommentDTO;
import com.zxy.swaycamp.domain.entity.*;
import com.zxy.swaycamp.domain.vo.ArticleVO;
import com.zxy.swaycamp.domain.vo.PageVO;
import com.zxy.swaycamp.domain.vo.comment.CommentVO;
import com.zxy.swaycamp.domain.vo.comment.ReplyVO;
import com.zxy.swaycamp.mapper.CommentMapper;
import com.zxy.swaycamp.mapper.CommentReplyMapper;
import com.zxy.swaycamp.service.CommentService;
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
 * 文章评论表 服务实现类
 * </p>
 *
 * @author Xinyuan Zhao
 * @since 2023-02-28
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Resource
    private CommonQuery commonQuery;
    @Resource
    private CommentReplyMapper commentReplyMapper;

    /**
     * 上传评论
     * @param commentDTO 评论信息
     */
    @Override
    public CommentVO uploadComment(CommentDTO commentDTO){
        Integer userId = SwayUtil.getCurrentUserId();
        try{
            Comment comment = new Comment();
            comment.setUserId(userId);
            comment.setArticleId(commentDTO.getArticleId());
            comment.setContent(commentDTO.getContent());
            comment.setCreateTime(LocalDateTime.now());
            comment.setLikeCount(0);
            comment.setReplyCount(0);
            comment.setDeleted(false);
            save(comment);
            return buildCommentVO(comment);
        }catch (Exception e){
            throw new ServiceException();
        }
    }


    /**
     * 分页查询评论
     * @param index 页码
     * @param size 分页大小
     * @param articleId 文章ID
     * @return 评论列表
     */
    @Override
    public PageVO<CommentVO> listComment(Integer index, Integer size, Integer articleId){
        Page<Comment> comments = lambdaQuery().eq(Comment::getArticleId, articleId)
                .page(new Page<>(index, size));
        if(comments == null || CollectionUtils.isEmpty(comments.getRecords())){
            throw new ServiceException(HttpStatus.BAD_REQUEST, "查询页数超出总页数");
        }
        List<CommentVO> commentVOS =  comments.getRecords().stream().map(item -> buildCommentVO(item)).collect(Collectors.toList());
        PageVO<CommentVO> pageVO = new PageVO<>();
        pageVO.setList(commentVOS);
        pageVO.setTotal((int) comments.getTotal());
        return pageVO;
    }

    private CommentVO buildCommentVO(Comment comment){
        CommentVO commentVO = new CommentVO();
        BeanUtil.copyProperties(comment, commentVO);
        List<CommentReply> replies = new LambdaQueryChainWrapper<>(commentReplyMapper)
                .eq(CommentReply::getCommentId, comment.getId())
                .eq(CommentReply::getDeleted, false)
                .orderByDesc(CommentReply::getLikeCount)
                .last("limit 0, 2").list();
        User user =  commonQuery.getUser(commentVO.getUserId());
        commentVO.setUsername(user.getUsername());
        commentVO.setAvatar(user.getAvatar());
        commentVO.setDate(comment.getCreateTime().toString());
        // 回复内容
        List<ReplyVO> replyVOS =  replies.stream().map(item -> {
            ReplyVO replyVO = new ReplyVO();
            BeanUtil.copyProperties(item, replyVO);
            User ryuser =  commonQuery.getUser(item.getUserId());
            replyVO.setUsername(ryuser.getUsername());
            replyVO.setAvatar(ryuser.getAvatar());
            if(item.getReplyId() != null){
                User replyUser =  commonQuery.getUser(item.getUserId());
                replyVO.setReplyUsername(replyUser.getUsername());
                replyVO.setReplyAvatar(replyUser.getAvatar());
            }
            replyVO.setDate(item.getCreateTime().toString());
            return replyVO;
        }).collect(Collectors.toList());
        commentVO.setReplies(replyVOS);
        Integer replyCount = new LambdaQueryChainWrapper<>(commentReplyMapper)
                .eq(CommentReply::getCommentId, comment.getId())
                .eq(CommentReply::getDeleted, false).count();
        commentVO.setReplyCount(replyCount);
        return commentVO;
    }
}
