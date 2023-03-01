/**请求参数 */

// 评论参数
export interface CommentDTO {
  articleId: number
  content: string
}
/**响应/接收参数 */

// 评论信息
export interface Comment {
  id: number
  userId: number
  username: string
  avatar: string
  content: string
  likeCount: number
  date: string
  replies: Reply[]
  replyCount: number
}

// 回复信息
export interface Reply {
  id: number
  commentId: number
  content: string
  date: string
  likeCount: number
  userId: number
  username: string
  avatar: string
  replyUserId: number
  replyUsername: string
  replyAvatar: string
}
