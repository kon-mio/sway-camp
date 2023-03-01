/**请求参数 */

// 评论参数
export interface CommentDTO {
  articleId: number
  content: string
}

export interface ReplyDTO {
  commentId: number | null
  content: string
  replyId: number | null
  replyUserId: number | null
}
/**响应/接收参数 */

// 共同信息
export interface CommonInfo {
  id: number
  content: string
  date: string
  likeCount: number
  userId: number
  username: string
  avatar: string
  commentId?: number
}
// 评论信息
export interface Comment extends CommonInfo {
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
export interface Reply extends CommonInfo {
  id: number
  commentId: number
  replyId: number
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

// 评论列表
export interface CommentPage {
  list: Comment[]
  total: number
}

// 评论列表
export interface ReplyPage {
  list: Reply[]
  total: number
}
